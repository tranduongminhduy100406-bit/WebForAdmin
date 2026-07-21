package dao;

import dbutils.DBUtils;
import dto.CustomerTier;
import dto.LoyaltyTransaction;
import dto.NextRewardInfo;
import dto.Reward;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Loyalty Engine.
 *
 * This is the original DAO (author: ADMIN), kept as-is in style and method
 * names, with a few additions needed so the rest of the app (Admin +
 * Customer controllers) can work:
 *   - getAllTiers()          -> used by AdminLoyaltyController to list tiers
 *   - getNextRewardInfo()    -> used by NextRewardController (Customer Profile)
 *   - getAllRewardsForAdmin(), createReward(), toggleRewardStatus()
 *                             -> Admin "create discount reward" feature
 * Also fixed: PreparedStatement/ResultSet were never closed (resource leak)
 * in several methods; runMonthlyTierReview()/runExpirePoints() now return a
 * log so the admin screen can show what happened (they used to run silently).
 *
 * @author ADMIN
 */
public class LoyaltyDAO {

    // ---------- Result codes for redeemReward ----------
    public static final int REDEEM_SUCCESS = 1;
    public static final int REDEEM_NOT_ENOUGH_POINTS = 0;
    public static final int REDEEM_REWARD_NOT_FOUND = -1;
    public static final int REDEEM_ERROR = -2;

    // =========================================================
    // EARN POINTS
    // =========================================================
    public boolean earnPoints(int customerID, int bookingID, double amount) {

        int bonusPercent = getBonusPercent(customerID);

        int basePoints = (int) (amount / 1000);
        int bonusPoints = (int) (basePoints * bonusPercent / 100.0);
        int totalPoints = basePoints + bonusPoints;

        if (totalPoints <= 0) {
            return false;
        }

        try ( Connection cn = DBUtils.getConnection()) {

            String sqlInsert = "INSERT INTO dbo.Loyalty_Transactions"
                    + "(CustomerID, BookingID, TransactionType, Points, Description, TransactionDate, ExpiryDate, IsExpired)"
                    + " VALUES(?,?,?,?,?,?,?,?)";
            try ( PreparedStatement st = cn.prepareStatement(sqlInsert)) {
                st.setInt(1, customerID);
                st.setInt(2, bookingID);
                st.setString(3, "EARN");
                st.setInt(4, totalPoints);
                st.setString(5, "Rua xe - " + basePoints + " diem + thuong " + bonusPoints);
                st.setDate(6, new Date(System.currentTimeMillis()));
                st.setDate(7, getExpiryDate());
                st.setBoolean(8, false);
                st.executeUpdate();
            }

            String sqlUpdate = "UPDATE dbo.Customers "
                    + "SET PointsBalance = PointsBalance + ?, "
                    + "    TotalBookings = TotalBookings + 1, "
                    + "    TotalSpent    = TotalSpent    + ? "
                    + "WHERE CustomerID = ?";
            try ( PreparedStatement st2 = cn.prepareStatement(sqlUpdate)) {
                st2.setInt(1, totalPoints);
                st2.setDouble(2, amount);
                st2.setInt(3, customerID);
                st2.executeUpdate();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // REDEMPTION
    // =========================================================
    public int redeemReward(int customerID, int rewardID) {

        Reward reward = getRewardById(rewardID);
        if (reward == null) {
            return REDEEM_REWARD_NOT_FOUND;
        }

        int pointsRequired = reward.getPointsRequired();
        int currentPoints = getPointsBalance(customerID);

        if (currentPoints < pointsRequired) {
            return REDEEM_NOT_ENOUGH_POINTS;
        }

        try ( Connection cn = DBUtils.getConnection()) {

            String sqlInsert = "INSERT INTO dbo.Loyalty_Transactions"
                    + "(CustomerID, BookingID, TransactionType, Points, Description, TransactionDate, IsExpired)"
                    + " VALUES(?,?,?,?,?,?,?)";
            try ( PreparedStatement st = cn.prepareStatement(sqlInsert)) {
                st.setInt(1, customerID);
                st.setNull(2, java.sql.Types.INTEGER);   // khong co bookingID khi redeem
                st.setString(3, "REDEEM");
                st.setInt(4, pointsRequired);
                st.setString(5, "Doi thuong: " + reward.getRewardName());
                st.setDate(6, new Date(System.currentTimeMillis()));
                st.setBoolean(7, false);
                st.executeUpdate();
            }

            String sqlUpdate = "UPDATE dbo.Customers "
                    + "SET PointsBalance = PointsBalance - ? "
                    + "WHERE CustomerID = ?";
            try ( PreparedStatement st2 = cn.prepareStatement(sqlUpdate)) {
                st2.setInt(1, pointsRequired);
                st2.setInt(2, customerID);
                st2.executeUpdate();
            }

            return REDEEM_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return REDEEM_ERROR;
        }
    }

    // =========================================================
    // TIER REVIEW (monthly, run manually by Admin)
    // =========================================================
    /**
     * NOTE: tier thresholds are hard-coded in calcTier() (kept as in the
     * original code). If Admin needs to configure tier rules from the DB
     * later, calcTier() must be changed to read CustomerTiers.MinBookings /
     * MinSpend instead of the literal numbers below.
     */
    public List<String> runMonthlyTierReview() {

        List<String> log = new ArrayList<>();

        try ( Connection cn = DBUtils.getConnection()) {

            String sqlSelect = "SELECT CustomerID, FullName, TierID, TotalBookings, TotalSpent FROM dbo.Customers WHERE Status = 1";

            try ( PreparedStatement st = cn.prepareStatement(sqlSelect);  ResultSet table = st.executeQuery()) {

                List<int[]> changes = new ArrayList<>(); // [custId, oldTierId, newTierId]
                List<String> names = new ArrayList<>();

                while (table.next()) {
                    int cusId = table.getInt("CustomerID");
                    String name = table.getString("FullName");
                    int oldTierID = table.getInt("TierID");
                    int totalBookings = table.getInt("TotalBookings");
                    double totalSpent = table.getDouble("TotalSpent");

                    int newTierID = calcTier(totalBookings, totalSpent);

                    if (newTierID != oldTierID) {
                        changes.add(new int[]{cusId, oldTierID, newTierID});
                        names.add(name);
                    }
                }

                String sqlUpdate = "UPDATE dbo.Customers SET TierID = ? WHERE CustomerID = ?";
                for (int i = 0; i < changes.size(); i++) {
                    int[] c = changes.get(i);
                    try ( PreparedStatement st2 = cn.prepareStatement(sqlUpdate)) {
                        st2.setInt(1, c[2]);
                        st2.setInt(2, c[0]);
                        st2.executeUpdate();
                    }

                    String direction = c[2] > c[1] ? "UPGRADED" : "DOWNGRADED";
                    log.add(names.get(i) + " (ID " + c[0] + "): " + tierName(c[1]) + " -> "
                            + tierName(c[2]) + " [" + direction + "]");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.add("ERROR while running tier review: " + e.getMessage());
        }

        if (log.isEmpty()) {
            log.add("No tier changes. Everyone is already in the correct tier.");
        }

        return log;
    }

    // =========================================================
    // EXPIRY: points expire after 12 months (run manually by Admin)
    // =========================================================
    public List<String> runExpirePoints() {

        List<String> log = new ArrayList<>();

        try ( Connection cn = DBUtils.getConnection()) {

            // buoc 1: lay tong diem het han cua tung khach
            String sqlSelect = "SELECT L.CustomerID, C.FullName, SUM(L.Points) AS ExpiredPoints "
                    + "FROM dbo.Loyalty_Transactions L "
                    + "JOIN dbo.Customers C ON C.CustomerID = L.CustomerID "
                    + "WHERE L.TransactionType = 'EARN' "
                    + "  AND L.IsExpired = 0 "
                    + "  AND L.ExpiryDate < CAST(GETDATE() AS DATE) "
                    + "GROUP BY L.CustomerID, C.FullName";

            try ( PreparedStatement st = cn.prepareStatement(sqlSelect);  ResultSet table = st.executeQuery()) {

                while (table.next()) {
                    int cusId = table.getInt("CustomerID");
                    String name = table.getString("FullName");
                    int expiredPoints = table.getInt("ExpiredPoints");

                    // buoc 2: tru diem het han khoi PointsBalance
                    String sqlUpdate = "UPDATE dbo.Customers "
                            + "SET PointsBalance = PointsBalance - ? "
                            + "WHERE CustomerID = ?";
                    try ( PreparedStatement st2 = cn.prepareStatement(sqlUpdate)) {
                        st2.setInt(1, expiredPoints);
                        st2.setInt(2, cusId);
                        st2.executeUpdate();
                    }

                    log.add(name + " (ID " + cusId + "): " + expiredPoints + " points expired");
                }
            }

            // buoc 3: danh dau IsExpired = 1 cho tat ca dong da het han
            String sqlMark = "UPDATE dbo.Loyalty_Transactions "
                    + "SET IsExpired = 1 "
                    + "WHERE TransactionType = 'EARN' "
                    + "  AND IsExpired = 0 "
                    + "  AND ExpiryDate < CAST(GETDATE() AS DATE)";
            try ( PreparedStatement mark = cn.prepareStatement(sqlMark)) {
                mark.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.add("ERROR while running points expiry: " + e.getMessage());
        }

        if (log.isEmpty()) {
            log.add("No points expired today.");
        }

        return log;
    }

    // =========================================================
    // REWARDS
    // =========================================================
    public List<Reward> getAllRewards() {
        List<Reward> result = new ArrayList<>();

        String sql = "SELECT RewardID, RewardName, PointsRequired, Description, IsActive, "
                + "DiscountType, DiscountValue "
                + "FROM dbo.Rewards WHERE IsActive = 1 ORDER BY PointsRequired";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet table = st.executeQuery()) {

            while (table.next()) {
                result.add(mapReward(table));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /** Admin management view: returns EVERY reward, active and inactive. */
    public List<Reward> getAllRewardsForAdmin() {
        List<Reward> result = new ArrayList<>();

        String sql = "SELECT RewardID, RewardName, PointsRequired, Description, IsActive, "
                + "DiscountType, DiscountValue "
                + "FROM dbo.Rewards ORDER BY IsActive DESC, PointsRequired";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet table = st.executeQuery()) {

            while (table.next()) {
                result.add(mapReward(table));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Admin creates a new discount-type reward, e.g.
     * createReward("10% Off Next Wash", 300, "...", "PERCENT", 10)
     * createReward("Free Wax", 500, "...", "FREE", 0)
     */
    public boolean createReward(String rewardName, int pointsRequired, String description,
            String discountType, double discountValue) {

        String sql = "INSERT INTO dbo.Rewards (RewardName, PointsRequired, Description, IsActive, "
                + "DiscountType, DiscountValue) VALUES (?, ?, ?, 1, ?, ?)";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setString(1, rewardName);
            st.setInt(2, pointsRequired);
            st.setString(3, description);
            st.setString(4, discountType);
            st.setDouble(5, discountValue);

            return st.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Toggles a reward between Active / Inactive (soft enable-disable). */
    public boolean toggleRewardStatus(int rewardId, boolean active) {

        String sql = "UPDATE dbo.Rewards SET IsActive = ? WHERE RewardID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setBoolean(1, active);
            st.setInt(2, rewardId);

            return st.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Reward getRewardById(int rewardID) {

        String sql = "SELECT RewardID, RewardName, PointsRequired, Description, IsActive, "
                + "DiscountType, DiscountValue "
                + "FROM dbo.Rewards WHERE RewardID = ? AND IsActive = 1";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, rewardID);
            try ( ResultSet table = st.executeQuery()) {
                if (table.next()) {
                    return mapReward(table);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Reward mapReward(ResultSet rs) throws Exception {
        Reward r = new Reward();
        r.setRewardID(rs.getInt("RewardID"));
        r.setRewardName(rs.getString("RewardName"));
        r.setPointsRequired(rs.getInt("PointsRequired"));
        r.setDescription(rs.getString("Description"));
        r.setActive(rs.getBoolean("IsActive"));
        r.setDiscountType(rs.getString("DiscountType"));
        r.setDiscountValue(rs.getDouble("DiscountValue"));
        return r;
    }

    // =========================================================
    // HISTORY
    // =========================================================
    public List<LoyaltyTransaction> getHistory(int customerID) {
        List<LoyaltyTransaction> result = new ArrayList<>();

        String sql = "SELECT TransactionID, CustomerID, BookingID, TransactionType, "
                + "       Points, Description, TransactionDate, ExpiryDate, IsExpired "
                + "FROM dbo.Loyalty_Transactions "
                + "WHERE CustomerID = ? "
                + "ORDER BY TransactionDate DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, customerID);
            try ( ResultSet table = st.executeQuery()) {
                while (table.next()) {
                    LoyaltyTransaction t = new LoyaltyTransaction();
                    t.setTransactionID(table.getInt("TransactionID"));
                    t.setCustomerID(table.getInt("CustomerID"));
                    t.setBookingID(table.getInt("BookingID"));
                    t.setTransactionType(table.getString("TransactionType"));
                    t.setPoints(table.getInt("Points"));
                    t.setDescription(table.getString("Description"));
                    t.setTransactionDate(table.getDate("TransactionDate"));
                    t.setExpiryDate(table.getDate("ExpiryDate"));
                    t.setIsExpired(table.getBoolean("IsExpired"));
                    result.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // =========================================================
    // TIERS  (added: needed by AdminLoyaltyController + NextRewardController)
    // =========================================================
    /**
     * Ordered by TierID ascending, which matches the tier numbers returned
     * by calcTier() (1=Member, 2=Silver, 3=Gold, 4=Platinum).
     */
    public List<CustomerTier> getAllTiers() {
        List<CustomerTier> list = new ArrayList<>();

        String sql = "SELECT TierID, TierName, MinBookings, MinSpend, PointBonusPercent, "
                + "PriorityLevel, Perks FROM dbo.CustomerTiers ORDER BY TierID ASC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                CustomerTier t = new CustomerTier();
                t.setTierID(rs.getInt("TierID"));
                t.setTierName(rs.getString("TierName"));
                t.setMinBookings(rs.getInt("MinBookings"));
                t.setMinSpend(rs.getDouble("MinSpend"));
                t.setPointBonusPercent(rs.getInt("PointBonusPercent"));
                t.setPriorityLevel(rs.getInt("PriorityLevel"));
                t.setPerks(rs.getString("Perks"));
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private String tierName(int tierId) {
        for (CustomerTier t : getAllTiers()) {
            if (t.getTierID() == tierId) {
                return t.getTierName();
            }
        }
        return "Unknown";
    }

    /**
     * Customer Profile requirement: "View: tier, points balance, next reward".
     * Finds the next tier above the customer's current one (by TierID) and
     * how many bookings remain to reach it.
     */
    public NextRewardInfo getNextRewardInfo(int customerId) {

        String sql = "SELECT c.TotalBookings, c.TotalSpent, t.TierID, t.TierName "
                + "FROM dbo.Customers c JOIN dbo.CustomerTiers t ON c.TierID = t.TierID "
                + "WHERE c.CustomerID = ?";

        NextRewardInfo info = new NextRewardInfo();

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, customerId);

            int totalBookings;
            int currentTierId;

            try ( ResultSet rs = st.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                totalBookings = rs.getInt("TotalBookings");
                currentTierId = rs.getInt("TierID");
                info.setCurrentTier(rs.getString("TierName"));
            }

            List<CustomerTier> tiers = getAllTiers(); // ascending TierID

            CustomerTier next = null;
            for (CustomerTier t : tiers) {
                if (t.getTierID() > currentTierId) {
                    next = t;
                    break;
                }
            }

            if (next == null) {
                info.setMaxTierReached(true);
                info.setNextTier("-");
                info.setRemainBookings(0);
                info.setProgressPercent(100);
                info.setNextPointBonusPercent(0);
                info.setNextPriorityLevel(currentTierId);
            } else {
                info.setMaxTierReached(false);
                info.setNextTier(next.getTierName());
                int remain = next.getMinBookings() - totalBookings;
                info.setRemainBookings(Math.max(remain, 0));

                int percent = next.getMinBookings() == 0 ? 100
                        : (int) Math.min(100, (totalBookings * 100.0) / next.getMinBookings());
                info.setProgressPercent(percent);
                info.setNextPointBonusPercent(next.getPointBonusPercent());
                info.setNextPriorityLevel(next.getPriorityLevel());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return info;
    }

    // =========================================================
    // PRIVATE HELPERS (original)
    // =========================================================
    private int calcTier(int totalBookings, double totalSpent) {
        if (totalBookings >= 30 || totalSpent >= 15000000) return 4;
        if (totalBookings >= 15 || totalSpent >= 6000000)  return 3;
        if (totalBookings >= 5  || totalSpent >= 2000000)  return 2;
        return 1;
    }

    private int getBonusPercent(int customerID) {
        String sql = "SELECT T.PointBonusPercent "
                + "FROM dbo.Customers C "
                + "JOIN dbo.CustomerTiers T ON C.TierID = T.TierID "
                + "WHERE C.CustomerID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, customerID);
            try ( ResultSet table = st.executeQuery()) {
                if (table.next()) {
                    return table.getInt("PointBonusPercent");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getPointsBalance(int customerID) {
        String sql = "SELECT PointsBalance FROM dbo.Customers WHERE CustomerID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, customerID);
            try ( ResultSet table = st.executeQuery()) {
                if (table.next()) {
                    return table.getInt("PointsBalance");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Date getExpiryDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.MONTH, 12);
        return new Date(cal.getTimeInMillis());
    }
}