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
                 Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {
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

    public List<MyBooking> getAllBookings() {

        List<MyBooking> list = new ArrayList<>();

        String sql = "SELECT B.BookingID, "
                + "B.CustomerID, "
                + "B.VehicleID, "
                + "B.BookingDate, "
                + "B.SlotTime, "
                + "B.ServiceType, "
                + "B.BookingStatus, "
                + "B.Notes, "
                + "B.CreatedAt, "
                + "V.LicensePlate "
                + "FROM Bookings B "
                + "JOIN Vehicles V "
                + "ON B.VehicleID = V.VehicleID "
                + "ORDER BY B.BookingDate DESC";

        try (
                 Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql);  ResultSet rs = st.executeQuery()) {

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
                        rs.getTimestamp("CreatedAt"),
                        rs.getString("LicensePlate")
                );

                list.add(b);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<MyBooking> searchBookings(String keyword, String type) {

        List<MyBooking> list = new ArrayList<>();

        String sql = "";
        if ("customer".equals(type)) {

            sql
                    = "SELECT B.BookingID,B.CustomerID,B.VehicleID,"
                    + "B.BookingDate,B.SlotTime,"
                    + "B.ServiceType,B.BookingStatus,"
                    + "B.Notes,B.CreatedAt,"
                    + "V.LicensePlate "
                    + "FROM Bookings B "
                    + "JOIN Vehicles V "
                    + "ON B.VehicleID=V.VehicleID "
                    + "WHERE CAST(B.CustomerID AS VARCHAR) LIKE ? "
                    + "ORDER BY B.BookingDate DESC";

        } else if ("plate".equals(type)) {

            sql
                    = "SELECT B.BookingID,B.CustomerID,B.VehicleID,"
                    + "B.BookingDate,B.SlotTime,"
                    + "B.ServiceType,B.BookingStatus,"
                    + "B.Notes,B.CreatedAt,"
                    + "V.LicensePlate "
                    + "FROM Bookings B "
                    + "JOIN Vehicles V "
                    + "ON B.VehicleID=V.VehicleID "
                    + "WHERE V.LicensePlate LIKE ? "
                    + "ORDER BY B.BookingDate DESC";

            }else if ("service".equals(type)) {

            sql
                    = "SELECT B.BookingID,B.CustomerID,B.VehicleID,"
                    + "B.BookingDate,B.SlotTime,"
                    + "B.ServiceType,B.BookingStatus,"
                    + "B.Notes,B.CreatedAt,"
                    + "V.LicensePlate "
                    + "FROM Bookings B "
                    + "JOIN Vehicles V "
                    + "ON B.VehicleID=V.VehicleID "
                    + "WHERE B.ServiceType LIKE ? "
                    + "ORDER BY B.BookingDate DESC";
        } else {

            sql
                    = "SELECT B.BookingID,B.CustomerID,B.VehicleID,"
                    + "B.BookingDate,B.SlotTime,"
                    + "B.ServiceType,B.BookingStatus,"
                    + "B.Notes,B.CreatedAt,"
                    + "V.LicensePlate "
                    + "FROM Bookings B "
                    + "JOIN Vehicles V "
                    + "ON B.VehicleID=V.VehicleID "
                    + "WHERE B.BookingStatus LIKE ? "
                    + "ORDER BY B.BookingDate DESC";

        }

        try (
                 Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

           st.setString(1, "%" + keyword.trim() + "%");

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


        public boolean updateBookingStatus(int bookingId, String status) {

        String sql = "UPDATE Bookings SET BookingStatus = ? WHERE BookingID = ?";

        try ( Connection cn = DBUtils.getConnection();  PreparedStatement st = cn.prepareStatement(sql)) {

            st.setString(1, status);
            st.setInt(2, bookingId);

            return st.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean isSlotAvailable(String bookingDate, String slotTime) {

        String sql = "SELECT COUNT(*) "
                + "FROM Bookings "
                + "WHERE CAST(BookingDate AS DATE) = ? "
                + "AND SlotTime = ?";
    
        try (
                Connection cn = DBUtils.getConnection();
                PreparedStatement st = cn.prepareStatement(sql)
        ) {
    
            st.setString(1, bookingDate);
            st.setString(2, slotTime);
    
            ResultSet rs = st.executeQuery();
    
            if (rs.next()) {
                int count = rs.getInt(1);
    
                // Tối đa 5 booking trong 1 ngày + 1 slot
                return count < 5;
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return false;
    }
    
    
        public int getBookingCountByDateAndSlot(String bookingDate, String slotTime) {
    
        String sql = "SELECT COUNT(*) "
                + "FROM Bookings "
                + "WHERE CAST(BookingDate AS DATE) = ? "
                + "AND SlotTime = ?";
    
        try (
                Connection cn = DBUtils.getConnection();
                PreparedStatement st = cn.prepareStatement(sql)
        ) {
    
            st.setString(1, bookingDate);
            st.setString(2, slotTime);
    
            ResultSet rs = st.executeQuery();
    
            if (rs.next()) {
                return rs.getInt(1);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return 0;
    }
        // =========================================================
// CHECK VEHICLE AVAILABLE FOR DATE + SLOT
// Một xe không được đặt 2 lần cùng ngày và cùng slot
// =========================================================
public boolean isVehicleAvailableForSlot(
        int vehicleID,
        String bookingDate,
        String slotTime) {

    String sql = "SELECT COUNT(*) "
            + "FROM Bookings "
            + "WHERE VehicleID = ? "
            + "AND CAST(BookingDate AS DATE) = ? "
            + "AND SlotTime = ?";

    try (
            Connection cn = DBUtils.getConnection();
            PreparedStatement st = cn.prepareStatement(sql)
    ) {

        st.setInt(1, vehicleID);
        st.setString(2, bookingDate);
        st.setString(3, slotTime);

        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) == 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


// =========================================================
// CREATE BOOKING
// =========================================================
public boolean createBooking(dto.BookingSlot booking) {

    String sql = "INSERT INTO Bookings "
            + "(CustomerID, VehicleID, BookingDate, SlotTime, "
            + "ServiceType, Notes, BookingStatus, CreatedAt) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";

    try (
            Connection cn = DBUtils.getConnection();
            PreparedStatement st = cn.prepareStatement(sql)
    ) {

        st.setInt(1, booking.getCustomerID());
        st.setInt(2, booking.getVehicleID());
        st.setString(3, booking.getBookingDate());
        st.setString(4, booking.getSlotTime());
        st.setString(5, booking.getServiceType());
        st.setString(6, booking.getNotes());
        st.setString(7, "Pending");

        return st.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
