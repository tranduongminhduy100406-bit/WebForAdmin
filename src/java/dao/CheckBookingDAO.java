/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DBUtils;
import dto.BookingPriorityDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class CheckBookingDAO {

    public List<BookingPriorityDTO> getTodayBookingsByPriority() {
        List<BookingPriorityDTO> list = new ArrayList<>();

        String sql = "SELECT B.BookingID, C.FullName, V.LicensePlate, "
                + "T.TierName, B.BookingDate, B.BookingStatus "
                + "FROM Bookings B "
                + "JOIN Customers C ON B.CustomerID = C.CustomerID "
                + "JOIN Vehicles V ON B.VehicleID = V.VehicleID "
                + "JOIN CustomerTiers T ON C.TierID = T.TierID "
                + "WHERE CAST(B.BookingDate AS DATE) = CAST(GETDATE() AS DATE) "
                + "AND B.BookingStatus = 'Pending' "
                + "ORDER BY T.PriorityLevel DESC, B.BookingDate ASC";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                BookingPriorityDTO b = new BookingPriorityDTO(
                        rs.getInt("BookingID"),
                        rs.getString("FullName"),
                        rs.getString("LicensePlate"),
                        rs.getString("TierName"),
                        rs.getTimestamp("BookingDate"),
                        rs.getString("BookingStatus")
                );
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateStatusToCompleted(int bookingId) {
        boolean result = false;
        String sql = "UPDATE dbo.Bookings SET BookingStatus = 'Completed' WHERE BookingID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setInt(1, bookingId);
            int rowsAffected = st.executeUpdate();
            result = rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
