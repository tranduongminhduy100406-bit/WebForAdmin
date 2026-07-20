/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Timestamp;

/**
 *
 * @author nguye
 */
public class BookingPriorityDTO {
    private int bookingId;
    private String customerName;
    private String licensePlate;
    private String tierName; // Platinum, Gold, Silver, Member
    private Timestamp bookingTime;
    private String status;

    public BookingPriorityDTO() {
    }

    public BookingPriorityDTO(int bookingId, String customerName, String licensePlate, String tierName, Timestamp bookingTime, String status) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.licensePlate = licensePlate;
        this.tierName = tierName;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}