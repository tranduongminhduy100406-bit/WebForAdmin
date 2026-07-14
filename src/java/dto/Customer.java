/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author nguye
 */
public class Customer implements Serializable {
    private int id;
    private String fullname;
    private String phone;
    private String password;
    private String email;
    private String address;
    private int tierId;
    private int totalBooking;
    private double totalSpent;
    private int pointBalance;
    private Date createdAt;
    private boolean status;
    private int roleId;
    public Customer() {
    }

    public Customer(int id, String fullname, String phone, String password, String email, String address, int tierId, int totalBooking, double totalSpent, int pointBalance, Date createdAt, boolean status) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.address = address;
        this.tierId = tierId;
        this.totalBooking = totalBooking;
        this.totalSpent = totalSpent;
        this.pointBalance = pointBalance;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Customer(int id, String fullname, String phone, String password, String email, int tierId, int pointBalance, Date createdAt, boolean status) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.tierId = tierId;
        this.pointBalance = pointBalance;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Customer(int id, String fullname, String phone, String password, String email, String address, int tierId, int totalBooking, double totalSpent, int pointBalance, Date createdAt, boolean status, int roleId) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.address = address;
        this.tierId = tierId;
        this.totalBooking = totalBooking;
        this.totalSpent = totalSpent;
        this.pointBalance = pointBalance;
        this.createdAt = createdAt;
        this.status = status;
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    

    public Customer(String fullname, String phone, String password, String email, String address) {
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getTierId() {
        return tierId;
    }

    public int getTotalBooking() {
        return totalBooking;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public int getPointBalance() {
        return pointBalance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTierId(int tierId) {
        this.tierId = tierId;
    }

    public void setTotalBooking(int totalBooking) {
        this.totalBooking = totalBooking;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void setPointBalance(int pointBalance) {
        this.pointBalance = pointBalance;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}