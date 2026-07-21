package dto;

public class NextRewardInfo {

    private String currentTier;
    private String nextTier;
    private int remainBookings;
    private int progressPercent;
    private int nextPointBonusPercent;
    private int nextPriorityLevel;
    private boolean maxTierReached;

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

    public int getNextPointBonusPercent() {
        return nextPointBonusPercent;
    }

    public void setNextPointBonusPercent(int nextPointBonusPercent) {
        this.nextPointBonusPercent = nextPointBonusPercent;
    }

    public int getNextPriorityLevel() {
        return nextPriorityLevel;
    }

    public void setNextPriorityLevel(int nextPriorityLevel) {
        this.nextPriorityLevel = nextPriorityLevel;
    }

    public boolean isMaxTierReached() {
        return maxTierReached;
    }

    public void setMaxTierReached(boolean maxTierReached) {
        this.maxTierReached = maxTierReached;
    }
}
