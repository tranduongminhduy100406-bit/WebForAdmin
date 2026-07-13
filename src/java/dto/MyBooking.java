/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Timestamp;

/**
 *
 * @author OMEN
 */
public class MyBooking {
    
    private int bookingID;
    private int customerID;
    private int vehicleID;
    private String bookingDate;
    private String slotTime;
    private String serviceType;
    private String bookingStatus;
    private String notes;
    private Timestamp createdAt;
    private String licensePlate;

    public MyBooking() {
    }

    public MyBooking(int bookingID, int customerID, int vehicleID, String bookingDate, String slotTime, String serviceType, String bookingStatus, String notes, Timestamp createdAt) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.bookingDate = bookingDate;
        this.slotTime = slotTime;
        this.serviceType = serviceType;
        this.bookingStatus = bookingStatus;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    
    public MyBooking(int bookingID, int customerID, int vehicleID, String bookingDate, String slotTime, String serviceType, String bookingStatus, String notes, Timestamp createdAt, String licensePlate) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.bookingDate = bookingDate;
        this.slotTime = slotTime;
        this.serviceType = serviceType;
        this.bookingStatus = bookingStatus;
        this.notes = notes;
        this.createdAt = createdAt;
        this.licensePlate = licensePlate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getNotes() {
        return notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    
}
