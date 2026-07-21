package dto;

import java.sql.Timestamp;

public class BookingSlot {

    private int bookingID;
    private int customerID;
    private int vehicleID;
    private String bookingDate;
    private String slotTime;
    private String serviceType;
    private String bookingStatus;
    private String notes;
    private double totalAmount;
    private Timestamp createdAt;
    private String licensePlate;
    // Constructor 1: Empty
    public BookingSlot() {
    }

    
    // Constructor 2: For INSERT
    public BookingSlot(int customerID, int vehicleID, String bookingDate,
                       String slotTime, String serviceType, String notes) {
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.bookingDate = bookingDate;
        this.slotTime = slotTime;
        this.serviceType = serviceType;
        this.bookingStatus = "Pending";
        this.notes = notes;
    }

    public BookingSlot(int bookingID, int customerID, int vehicleID, String bookingDate, String slotTime, String serviceType, String bookingStatus, String notes, Timestamp createdAt, String licensePlate) {
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

    public BookingSlot(int customerID, int vehicleID, String bookingDate, String serviceType, double totalAmount) {
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.bookingDate = bookingDate;
        this.serviceType = serviceType;
        this.totalAmount = totalAmount;
    }

    
    // Constructor 3: Full data

    public BookingSlot(int bookingID, int customerID, int vehicleID, String bookingDate, String slotTime, String serviceType, String bookingStatus, String notes, double totalAmount, Timestamp createdAt, String licensePlate) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.bookingDate = bookingDate;
        this.slotTime = slotTime;
        this.serviceType = serviceType;
        this.bookingStatus = bookingStatus;
        this.notes = notes;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.licensePlate = licensePlate;
    }
   

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "BookingSlot{"
                + "bookingID=" + bookingID
                + ", customerID=" + customerID
                + ", vehicleID=" + vehicleID
                + ", bookingDate='" + bookingDate + '\''
                + ", slotTime='" + slotTime + '\''
                + ", serviceType='" + serviceType + '\''
                + ", bookingStatus='" + bookingStatus + '\''
                + ", notes='" + notes + '\''
                + ", createdAt=" + createdAt
                + '}';
    }
}