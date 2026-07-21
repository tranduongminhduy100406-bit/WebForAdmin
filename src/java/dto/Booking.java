package dto;

import java.io.Serializable;
import java.sql.Timestamp; 

public class Booking implements Serializable {
    private int bookingID;
    private int customerID;
    private int vehicleID;
    private Timestamp bookingDate;
    private String serviceType;
    private double totalAmount;
    private String bookingStatus;
    private String notes;

    public Booking() {
    }



    
    public Booking(int bookingID, int customerID, int vehicleID, Timestamp bookingDate, 
                   String serviceType, double totalAmount, String bookingStatus, String notes) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.bookingDate = bookingDate;
        this.serviceType = serviceType;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
        this.notes = notes;
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

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getNotes() {
        return notes;
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

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
    
}