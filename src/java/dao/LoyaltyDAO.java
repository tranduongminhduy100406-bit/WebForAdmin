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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Loyalty Engine.
 *
 * Covers: - Track points, tier, spend, visits (earnPointsForBooking) -
 * Auto-upgrade/downgrade (runTierReview) - Redemption: points -> reward
 * (redeemPoints) - Expiry: points expire after 12 months
 * (runPointsExpiry)
 */
public class LoyaltyDAO {

    // 1 point earned per 1,000 VND spent (per CustomerTiers "Member" perk)
    private static final int VND_PER_POINT = 1000;
    private static final int POINT_EXPIRY_MONTHS = 12;

    // ---------- Result codes for redeemPoints ----------
    public static final int REDEEM_SUCCESS = 1;
    public static final int REDEEM_NOT_ENOUGH_POINTS = 0;
    public static final int REDEEM_REWARD_NOT_FOUND = -1;
    public static final int REDEEM_ERROR = -2;

    // =========================================================
    // TIERS
    // =========================================================
    public List<CustomerTier> getAllTiers() {
        List<CustomerTier> list = new ArrayList<>();
        String sql = "SELECT TierID, TierName, MinBookings, MinSpend, PointBonusPercent, "
                + "PriorityLevel, Perks FROM CustomerTiers ORDER BY PriorityLevel ASC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(mapTier(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public CustomerTier getTierById(int tierId) {
        String sql = "SELECT TierID, TierName, MinBookings, MinSpend, PointBonusPercent, "
                + "PriorityLevel, Perks FROM CustomerTiers WHERE TierID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, tierId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapTier(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private CustomerTier mapTier(ResultSet rs) throws Exception {
        CustomerTier t = new CustomerTier();
        t.setTierID(rs.getInt("TierID"));
        t.setTierName(rs.getString("TierName"));
        t.setMinBookings(rs.getInt("MinBookings"));
        t.setMinSpend(rs.getDouble("MinSpend"));
        t.setPointBonusPercent(rs.getInt("PointBonusPercent"));
        t.setPriorityLevel(rs.getInt("PriorityLevel"));
        t.setPerks(rs.getString("Perks"));
        return t;
    }

    // =========================================================
    // REWARDS
    // =========================================================
    public List<Reward> getActiveRewards() {
        List<Reward> list = new ArrayList<>();
        String sql = "SELECT RewardID, RewardName, PointsRequired, Description, IsActive "
                + "FROM Rewards WHERE IsActive = 1 ORDER BY PointsRequired ASC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(mapReward(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Reward getRewardById(int rewardId) {
        String sql = "SELECT RewardID, RewardName, PointsRequired, Description, IsActive "
                + "FROM Rewards WHERE RewardID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, rewardId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapReward(rs);
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
        return r;
    }

    // =========================================================
    // TRANSACTION HISTORY
    // =========================================================
    public List<LoyaltyTransaction> getTransactionHistory(int customerId) {
        List<LoyaltyTransaction> list = new ArrayList<>();
        String sql = "SELECT TransactionID, CustomerID, BookingID, TransactionType, Points, "
                + "Description, TransactionDate, ExpiryDate, IsExpired "
                + "FROM Loyalty_Transactions WHERE CustomerID = ? ORDER BY TransactionDate DESC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, customerId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LoyaltyTransaction t = new LoyaltyTransaction();
                t.setTransactionID(rs.getInt("TransactionID"));
                t.setCustomerID(rs.getInt("CustomerID"));
                t.setBookingID(rs.getInt("BookingID"));
                t.setTransactionType(rs.getString("TransactionType"));
                t.setPoints(rs.getInt("Points"));
                t.setDescription(rs.getString("Description"));
                t.setTransactionDate(rs.getDate("TransactionDate"));
                t.setExpiryDate(rs.getDate("ExpiryDate"));
                t.setIsExpired(rs.getBoolean("IsExpired"));
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================================================
    // NEXT REWARD INFO (for Customer Profile page)
    // =========================================================
    public NextRewardInfo getNextRewardInfo(int customerId) {

        String sql = "SELECT c.TotalBookings, c.TotalSpent, t.TierID, t.TierName, t.PriorityLevel "
                + "FROM Customers c JOIN CustomerTiers t ON c.TierID = t.TierID "
                + "WHERE c.CustomerID = ?";

        NextRewardInfo info = new NextRewardInfo();

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, customerId);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }

            int totalBookings = rs.getInt("TotalBookings");
            int currentPriority = rs.getInt("PriorityLevel");
            String currentTierName = rs.getString("TierName");

            info.setCurrentTier(currentTierName);

            List<CustomerTier> tiers = getAllTiers(); // ordered by PriorityLevel ASC

            CustomerTier next = null;
            for (CustomerTier t : tiers) {
                if (t.getPriorityLevel() > currentPriority) {
                    next = t;
                    break;
                }
            }

            if (next == null) {
                // already at the highest tier
                info.setMaxTierReached(true);
                info.setNextTier("-");
                info.setRemainBookings(0);
                info.setProgressPercent(100);
                info.setNextPointBonusPercent(0);
                info.setNextPriorityLevel(currentPriority);
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
    // EARN POINTS (called when a booking is marked Completed)
    // =========================================================
    /**
     * Awards loyalty points for a completed booking, updates the
     * customer's PointsBalance / TotalSpent / TotalBookings, and logs an
     * EARN transaction that expires in 12 months.
     *
     * @param bookingId the completed booking
     * @return true if points were awarded
     */
    public boolean earnPointsForBooking(int bookingId) {

        String getBookingSql = "SELECT CustomerID, TotalAmount FROM Bookings WHERE BookingID = ?";
        String getBonusSql = "SELECT t.PointBonusPercent FROM Customers c "
                + "JOIN CustomerTiers t ON c.TierID = t.TierID WHERE c.CustomerID = ?";
        String insertTxnSql = "INSERT INTO Loyalty_Transactions "
                + "(CustomerID, BookingID, TransactionType, Points, Description, TransactionDate, ExpiryDate, IsExpired) "
                + "VALUES (?, ?, 'EARN', ?, ?, CAST(GETDATE() AS DATE), DATEADD(MONTH, ?, CAST(GETDATE() AS DATE)), 0)";
        String updateCustomerSql = "UPDATE Customers SET PointsBalance = PointsBalance + ?, "
                + "TotalSpent = TotalSpent + ?, TotalBookings = TotalBookings + 1 WHERE CustomerID = ?";

        Connection cn = null;

        try {
            cn = DBUtils.getConnection();
            cn.setAutoCommit(false);

            int customerId;
            double totalAmount;

            try ( PreparedStatement st = cn.prepareStatement(getBookingSql)) {
                st.setInt(1, bookingId);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    cn.rollback();
                    return false;
                }
                customerId = rs.getInt("CustomerID");
                totalAmount = rs.getDouble("TotalAmount");
            }

            int bonusPercent = 0;
            try ( PreparedStatement st = cn.prepareStatement(getBonusSql)) {
                st.setInt(1, customerId);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    bonusPercent = rs.getInt("PointBonusPercent");
                }
            }

            int basePoints = (int) (totalAmount / VND_PER_POINT);
            int bonusPoints = (int) (basePoints * bonusPercent / 100.0);
            int totalPoints = basePoints + bonusPoints;

            try ( PreparedStatement st = cn.prepareStatement(insertTxnSql)) {
                st.setInt(1, customerId);
                st.setInt(2, bookingId);
                st.setInt(3, totalPoints);
                st.setString(4, "Earned from booking #" + bookingId);
                st.setInt(5, POINT_EXPIRY_MONTHS);
                st.executeUpdate();
            }

            try ( PreparedStatement st = cn.prepareStatement(updateCustomerSql)) {
                st.setInt(1, totalPoints);
                st.setDouble(2, totalAmount);
                st.setInt(3, customerId);
                st.executeUpdate();
            }

            cn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // =========================================================
    // REDEMPTION: points -> reward (discount / free wash / free wax...)
    // =========================================================
    public int redeemPoints(int customerId, int rewardId) {

        Reward reward = getRewardById(rewardId);

        if (reward == null || !reward.isActive()) {
            return REDEEM_REWARD_NOT_FOUND;
        }

        String getBalanceSql = "SELECT PointsBalance FROM Customers WHERE CustomerID = ?";
        String insertTxnSql = "INSERT INTO Loyalty_Transactions "
                + "(CustomerID, BookingID, TransactionType, Points, Description, TransactionDate, ExpiryDate, IsExpired) "
                + "VALUES (?, NULL, 'REDEEM', ?, ?, CAST(GETDATE() AS DATE), NULL, 0)";
        String updateBalanceSql = "UPDATE Customers SET PointsBalance = PointsBalance - ? WHERE CustomerID = ?";

        Connection cn = null;

        try {
            cn = DBUtils.getConnection();
            cn.setAutoCommit(false);

            int balance = 0;
            try ( PreparedStatement st = cn.prepareStatement(getBalanceSql)) {
                st.setInt(1, customerId);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    balance = rs.getInt("PointsBalance");
                }
            }

            if (balance < reward.getPointsRequired()) {
                cn.rollback();
                return REDEEM_NOT_ENOUGH_POINTS;
            }

            try ( PreparedStatement st = cn.prepareStatement(insertTxnSql)) {
                st.setInt(1, customerId);
                // stored as a negative amount so SUM(Points) reflects the running balance
                st.setInt(2, -reward.getPointsRequired());
                st.setString(3, "Redeemed: " + reward.getRewardName());
                st.executeUpdate();
            }

            try ( PreparedStatement st = cn.prepareStatement(updateBalanceSql)) {
                st.setInt(1, reward.getPointsRequired());
                st.setInt(2, customerId);
                st.executeUpdate();
            }

            cn.commit();
            return REDEEM_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return REDEEM_ERROR;
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // =========================================================
    // AUTO UPGRADE / DOWNGRADE (run manually by Admin)
    // =========================================================
    /**
     * Reviews every active customer's TotalBookings / TotalSpent against
     * CustomerTiers thresholds and moves them to the highest tier they
     * qualify for (upgrade or downgrade). Returns a human-readable log
     * for the admin screen.
     */
    public List<String> runTierReview() {

        List<String> log = new ArrayList<>();

        String customersSql = "SELECT CustomerID, FullName, TierID, TotalBookings, TotalSpent "
                + "FROM Customers WHERE Status = 1 AND roleId = 2";
        String updateSql = "UPDATE Customers SET TierID = ? WHERE CustomerID = ?";

        List<CustomerTier> tiers = getAllTiers(); // ascending PriorityLevel

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(customersSql);  ResultSet rs = st.executeQuery()) {

            while (rs.next()) {

                int custId = rs.getInt("CustomerID");
                String name = rs.getString("FullName");
                int currentTierId = rs.getInt("TierID");
                int totalBookings = rs.getInt("TotalBookings");
                double totalSpent = rs.getDouble("TotalSpent");

                CustomerTier qualified = tiers.get(0); // default: lowest tier (Member)

                for (CustomerTier t : tiers) {
                    if (totalBookings >= t.getMinBookings() || totalSpent >= t.getMinSpend()) {
                        qualified = t; // tiers is ascending, so the loop keeps the highest match
                    }
                }

                if (qualified.getTierID() != currentTierId) {

                    try ( PreparedStatement up = cn.prepareStatement(updateSql)) {
                        up.setInt(1, qualified.getTierID());
                        up.setInt(2, custId);
                        up.executeUpdate();
                    }

                    String oldName = tierNameOf(tiers, currentTierId);
                    String direction = qualified.getPriorityLevel() > priorityOf(tiers, currentTierId)
                            ? "UPGRADED" : "DOWNGRADED";

                    log.add(name + " (ID " + custId + "): " + oldName + " -> "
                            + qualified.getTierName() + " [" + direction + "]");
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

    private String tierNameOf(List<CustomerTier> tiers, int tierId) {
        for (CustomerTier t : tiers) {
            if (t.getTierID() == tierId) {
                return t.getTierName();
            }
        }
        return "Unknown";
    }

    private int priorityOf(List<CustomerTier> tiers, int tierId) {
        for (CustomerTier t : tiers) {
            if (t.getTierID() == tierId) {
                return t.getPriorityLevel();
            }
        }
        return 0;
    }

    // =========================================================
    // EXPIRY: points expire after 12 months (run manually by Admin)
    // =========================================================
    public List<String> runPointsExpiry() {

        List<String> log = new ArrayList<>();

        String findExpiredSql = "SELECT CustomerID, SUM(Points) AS ExpiredPoints "
                + "FROM Loyalty_Transactions "
                + "WHERE TransactionType = 'EARN' AND (IsExpired = 0 OR IsExpired IS NULL) "
                + "AND ExpiryDate < CAST(GETDATE() AS DATE) "
                + "GROUP BY CustomerID";

        String markExpiredSql = "UPDATE Loyalty_Transactions SET IsExpired = 1 "
                + "WHERE CustomerID = ? AND TransactionType = 'EARN' "
                + "AND (IsExpired = 0 OR IsExpired IS NULL) AND ExpiryDate < CAST(GETDATE() AS DATE)";

        String getBalanceSql = "SELECT PointsBalance, FullName FROM Customers WHERE CustomerID = ?";

        String updateBalanceSql = "UPDATE Customers SET PointsBalance = ? WHERE CustomerID = ?";

        String insertExpireTxnSql = "INSERT INTO Loyalty_Transactions "
                + "(CustomerID, BookingID, TransactionType, Points, Description, TransactionDate, ExpiryDate, IsExpired) "
                + "VALUES (?, NULL, 'EXPIRE', ?, 'Points expired after 12 months', CAST(GETDATE() AS DATE), NULL, 1)";

        Connection cn = null;

        try {
            cn = DBUtils.getConnection();
            cn.setAutoCommit(false);

            try ( PreparedStatement find = cn.prepareStatement(findExpiredSql)) {

                ResultSet rs = find.executeQuery();

                while (rs.next()) {
                    int custId = rs.getInt("CustomerID");
                    int expiredPoints = rs.getInt("ExpiredPoints");

                    int currentBalance = 0;
                    String name = "";
                    try ( PreparedStatement bal = cn.prepareStatement(getBalanceSql)) {
                        bal.setInt(1, custId);
                        ResultSet br = bal.executeQuery();
                        if (br.next()) {
                            currentBalance = br.getInt("PointsBalance");
                            name = br.getString("FullName");
                        }
                    }

                    int newBalance = Math.max(0, currentBalance - expiredPoints);

                    try ( PreparedStatement up = cn.prepareStatement(updateBalanceSql)) {
                        up.setInt(1, newBalance);
                        up.setInt(2, custId);
                        up.executeUpdate();
                    }

                    try ( PreparedStatement mark = cn.prepareStatement(markExpiredSql)) {
                        mark.setInt(1, custId);
                        mark.executeUpdate();
                    }

                    try ( PreparedStatement ins = cn.prepareStatement(insertExpireTxnSql)) {
                        ins.setInt(1, custId);
                        ins.setInt(2, -expiredPoints);
                        ins.executeUpdate();
                    }

                    log.add(name + " (ID " + custId + "): " + expiredPoints
                            + " points expired (balance " + currentBalance + " -> " + newBalance + ")");
                }
            }

            cn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            log.add("ERROR while running points expiry: " + e.getMessage());
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (log.isEmpty()) {
            log.add("No points expired today.");
        }

        return log;
    }
}
