/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DBUtils;
import dto.MyBooking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OMEN
 */
public class BookingDAO {
    public List<MyBooking> getMyBookings(int customerID) {
        List<MyBooking> list = new ArrayList<>();

        String sql = "SELECT B.BookingID, B.CustomerID, B.VehicleID, "
                + "B.BookingDate, B.SlotTime, B.ServiceType, "
                + "B.BookingStatus, B.Notes, B.CreatedAt, "
                + "V.LicensePlate "
                + "FROM Bookings B "
                + "JOIN Vehicles V ON B.VehicleID = V.VehicleID "
                + "WHERE B.CustomerID = ? "
                + "ORDER BY B.BookingDate DESC";

        try (
                Connection cn = DBUtils.getConnection();  
                PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, customerID);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                MyBooking b = new MyBooking(
                        rs.getInt("BookingID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("VehicleID"),
                        rs.getString("BookingDate"),
                        rs.getString("SlotTime"),
                        rs.getString("ServiceType"),
                        rs.getString("BookingStatus"),
                        rs.getString("Notes"),
                        rs.getTimestamp("CreatedAt")
                );

                b.setLicensePlate(rs.getString("LicensePlate"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
