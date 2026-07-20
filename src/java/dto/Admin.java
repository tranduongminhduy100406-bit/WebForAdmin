package dto;

public class Admin {

    private int adminID;
    private String fullName;
    private String phoneNumber;
    private String password;

    public Admin() {
    }

    public Admin(int adminID, String fullName, String phoneNumber, String password) {
        this.adminID = adminID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}