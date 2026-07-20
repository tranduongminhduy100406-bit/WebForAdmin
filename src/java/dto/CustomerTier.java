/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;

public class CustomerTier implements Serializable {

    private int tierID;
    private String tierName;
    private int minBookings;
    private double discountPercent;
    private int priorityLevel;

    public CustomerTier() {
    }

    public CustomerTier(int tierID,
            String tierName,
            int minBookings,
            double discountPercent,
            int priorityLevel) {

        this.tierID = tierID;
        this.tierName = tierName;
        this.minBookings = minBookings;
        this.discountPercent = discountPercent;
        this.priorityLevel = priorityLevel;
    }

    public int getTierID() {
        return tierID;
    }

    public void setTierID(int tierID) {
        this.tierID = tierID;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public int getMinBookings() {
        return minBookings;
    }

    public void setMinBookings(int minBookings) {
        this.minBookings = minBookings;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}