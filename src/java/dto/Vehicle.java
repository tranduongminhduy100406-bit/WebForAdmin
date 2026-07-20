/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author OMEN
 */
public class Vehicle {
    private int carId;
    private int cusId;
    private String licensePlate;
    private String brand;
    private String model;
    private String color;
    private Date createCarAt;

    public Vehicle() {
    }

    public Vehicle(int carId, int cusId, String licensePlate, String brand, String model, String color, Date createCarAt) {
        this.carId = carId;
        this.cusId = cusId;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.createCarAt = createCarAt;
    }

    public Vehicle(String licensePlate, String brand, String color) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.color = color;
    }

    public int getCarId() {
        return carId;
    }

    public int getCusId() {
        return cusId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public Date getCreateCarAt() {
        return createCarAt;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCreateCarAt(Date createCarAt) {
        this.createCarAt = createCarAt;
    }
    
    
}
