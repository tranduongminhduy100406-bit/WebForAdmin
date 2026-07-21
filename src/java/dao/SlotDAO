/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DBUtils;
import dto.Customer;
import dto.Slots;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OMEN
 */
public class SlotDAO {
    public List<Slots> getSlots(){
        List<Slots> result = new ArrayList<>();
    Connection cn = null;

    try {
        cn = DBUtils.getConnection();

        if (cn != null) {
            String sql = "SELECT SlotID, SlotTime, Status "
                       + "FROM dbo.Slots "
                       + "WHERE Status = 1";

            PreparedStatement st = cn.prepareStatement(sql);

            ResultSet table = st.executeQuery();

            while (table.next()) {
                int slotID = table.getInt("SlotID");
                String slotTime = table.getString("SlotTime");
                boolean status = table.getBoolean("Status");

                Slots s = new Slots(slotID, slotTime, status);

                result.add(s);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();

    } finally {
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return result;
    }
    
}
