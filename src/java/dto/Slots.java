/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author OMEN
 */
public class Slots {
    private int slotID;
    private String slotTime;
    private boolean status;

    public Slots() {
    }

    public Slots(int slotID, String slotTime, boolean status) {
        this.slotID = slotID;
        this.slotTime = slotTime;
        this.status = status;
    }

    public int getSlotID() {
        return slotID;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
