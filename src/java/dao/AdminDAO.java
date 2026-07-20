package dao;

import dbutils.DBUtils;
import dto.Admin;
import dto.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    public Customer checkAdminLogin(String phone, String password) {

        String sql = "SELECT * FROM Customers "
                + "WHERE PhoneNumber = ? "
                + "AND PasswordHash = ? "
                + "AND RoleID = 1";

        try (
                 Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setString(1, phone);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                Customer admin = new Customer();

                admin.setId(rs.getInt("CustomerID"));
                admin.setFullname(rs.getString("FullName"));
                admin.setPhone(rs.getString("PhoneNumber"));
                admin.setPassword(rs.getString("PasswordHash"));
                admin.setEmail(rs.getString("Email"));
                admin.setAddress(rs.getString("Address"));
                admin.setTierId(rs.getInt("TierID"));
                admin.setTotalBooking(rs.getInt("TotalBookings"));
                admin.setTotalSpent(rs.getDouble("TotalSpent"));
                admin.setPointBalance(rs.getInt("PointsBalance"));
                admin.setCreatedAt(rs.getDate("CreatedAt"));
                admin.setStatus(rs.getBoolean("Status"));
                admin.setRoleId(rs.getInt("RoleID"));

                return admin;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
