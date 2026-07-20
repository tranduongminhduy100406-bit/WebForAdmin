/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

public class NextRewardInfo {

    private String currentTier;
    private String nextTier;
    private int remainBookings;
    private int progressPercent;
    private double nextDiscount;
    private int nextPriorityLevel;

    public String getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(String currentTier) {
        this.currentTier = currentTier;
    }

    public String getNextTier() {
        return nextTier;
    }

    public void setNextTier(String nextTier) {
        this.nextTier = nextTier;
    }

    public int getRemainBookings() {
        return remainBookings;
    }

    public void setRemainBookings(int remainBookings) {
        this.remainBookings = remainBookings;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }

    public double getNextDiscount() {
        return nextDiscount;
    }

    public void setNextDiscount(double nextDiscount) {
        this.nextDiscount = nextDiscount;
    }

    public int getNextPriorityLevel() {
        return nextPriorityLevel;
    }

    public void setNextPriorityLevel(int nextPriorityLevel) {
        this.nextPriorityLevel = nextPriorityLevel;
    }
}