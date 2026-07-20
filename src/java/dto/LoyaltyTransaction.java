package dto;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class LoyaltyTransaction {
    private int    transactionID;
    private int    customerID;
    private int    bookingID;
    private String transactionType;  // 'EARN' hoac 'REDEEM'
    private int    points;
    private String description;
    private Date   transactionDate;
    private Date   expiryDate;
    private boolean isExpired;

    public LoyaltyTransaction() {
    }

    public LoyaltyTransaction(int transactionID, int customerID, int bookingID, String transactionType, int points, String description, Date transactionDate, Date expiryDate, boolean isExpired) {
        this.transactionID = transactionID;
        this.customerID = customerID;
        this.bookingID = bookingID;
        this.transactionType = transactionType;
        this.points = points;
        this.description = description;
        this.transactionDate = transactionDate;
        this.expiryDate = expiryDate;
        this.isExpired = isExpired;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public boolean isIsExpired() {
        return isExpired;
    }

}