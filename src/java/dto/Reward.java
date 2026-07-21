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

    // Discount info: lets Admin define WHAT the reward actually does
    // instead of just a free-text description.
    // discountType: "PERCENT" (e.g. 10% off), "AMOUNT" (e.g. 50,000 VND off),
    //               "FREE" (free service, e.g. free wax / free wash)
    private String discountType;
    private double discountValue;

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

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public double getDiscountValue() { return discountValue; }
    public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }

    /** Human-readable label, e.g. "10% off" / "50,000 VND off" / "Free service". */
    public String getDiscountLabel() {
        if (discountType == null) {
            return "";
        }
        switch (discountType) {
            case "PERCENT":
                return (int) discountValue + "% off";
            case "AMOUNT":
                return String.format("%,.0f VND off", discountValue);
            case "FREE":
                return "Free service";
            default:
                return "";
        }
    }
}
