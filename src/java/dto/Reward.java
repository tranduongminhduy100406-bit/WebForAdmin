package dto;

/**
 *
 * @author ADMIN
 */
public class Reward {
    private int    rewardID;
    private String rewardName;
    private int    pointsRequired;
    private String description;
    private boolean isActive;

    public Reward() {
    }

    public int getRewardID() { return rewardID; }
    public void setRewardID(int rewardID) { this.rewardID = rewardID; }

    public String getRewardName() { return rewardName; }
    public void setRewardName(String rewardName) { this.rewardName = rewardName; }

    public int getPointsRequired() { return pointsRequired; }
    public void setPointsRequired(int pointsRequired) { this.pointsRequired = pointsRequired; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
}
