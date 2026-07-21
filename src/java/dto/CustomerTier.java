/*
 * DTO for table dbo.CustomerTiers
 * Updated for Loyalty Engine - fields now match the actual DB schema
 * (previous version had a "discountPercent" field that did not exist
 * in the database; the real column is PointBonusPercent).
 */
package dto;

import java.io.Serializable;

public class CustomerTier implements Serializable {

    private int tierID;
    private String tierName;
    private int minBookings;
    private double minSpend;
    private int pointBonusPercent;
    private int priorityLevel;
    private String perks;

    public CustomerTier() {
    }

    public CustomerTier(int tierID, String tierName, int minBookings, double minSpend,
            int pointBonusPercent, int priorityLevel, String perks) {
        this.tierID = tierID;
        this.tierName = tierName;
        this.minBookings = minBookings;
        this.minSpend = minSpend;
        this.pointBonusPercent = pointBonusPercent;
        this.priorityLevel = priorityLevel;
        this.perks = perks;
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

    public double getMinSpend() {
        return minSpend;
    }

    public void setMinSpend(double minSpend) {
        this.minSpend = minSpend;
    }

    public int getPointBonusPercent() {
        return pointBonusPercent;
    }

    public void setPointBonusPercent(int pointBonusPercent) {
        this.pointBonusPercent = pointBonusPercent;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getPerks() {
        return perks;
    }

    public void setPerks(String perks) {
        this.perks = perks;
    }
}
