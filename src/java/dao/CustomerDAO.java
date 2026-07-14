/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DBUtils;
import dto.Customer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nguye
 */
public class CustomerDAO {  
    // LOGIN
    public Customer getCustomer(String phone, String password) {
        Customer result = null;
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT CustomerID, FullName, PhoneNumber, PasswordHash, Email, TierID, PointsBalance, CreatedAt, Status, roleId\n"
                        + "FROM dbo.Customers\n"
                        + "WHERE PhoneNumber=? AND PasswordHash=?";
                
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, phone);
                st.setString(2, password);
                
                ResultSet table = st.executeQuery();
                
                while (table.next()) {
                   int cusid = table.getInt("CustomerID");
                   String name = table.getString("FullName");
                   String phoneDb = table.getString("PhoneNumber");
                   String passDb = table.getString("PasswordHash");
                   String emailDb = table.getString("Email");
                   int tierid = table.getInt("TierID"); 
                   int points = table.getInt("PointsBalance"); 
                   Date createDate = table.getDate("CreatedAt");
                   boolean status = table.getBoolean("Status");
                   int roleId = table.getInt("roleId");
                   
                   result = new Customer(cusid, name, phoneDb, passDb, emailDb, tierid, points, createDate, status);
                   
                   result.setRoleId(roleId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    public int createCus(Customer c){
        int result = 0;
        Connection cn =null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert[dbo].[Customers]([FullName],[PhoneNumber],[PasswordHash],[Email],[Address])\n"
                        + "values(?,?,?,?,?)";
                PreparedStatement pp = cn.prepareStatement(sql);
                pp.setString(1, c.getFullname());
                pp.setString(2, c.getPhone());
                pp.setString(3, c.getPassword());
                pp.setString(4, c.getEmail());
                pp.setString(5, c.getAddress());
                result = pp.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //buoc4
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            } finally {
            }
        }
        return result;
    }
    public Customer getCusByPhone(String phone){
        Customer result = null;
        Connection cn =null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [FullName],[PhoneNumber],[PasswordHash],[Email],[Address]\n"
                        + "from [dbo].[Customers] where [PhoneNumber]=?";
                PreparedStatement pp = cn.prepareStatement(sql);
                
                pp.setString(1, phone);
               
                ResultSet table = pp.executeQuery();
                while(table.next()){
                    String fullname=table.getString("FullName");
                    //String phone = table.getString("Phone");
                    String password=table.getString("PasswordHash");
                    String email =table.getString("Email");  
                    String address = table.getString("Address");
                    
                    result=new Customer(fullname, phone, password, email, address);
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //buoc4
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            } finally {
            }
        }
        return result;
    }
    public Customer getCusByMail(String email){
        Customer result = null;
        Connection cn =null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select [FullName],[PhoneNumber],[PasswordHash],[Email],[Address]\n"
                        + "from [dbo].[Customers] where [Email]=?";
                PreparedStatement pp = cn.prepareStatement(sql);
                
                pp.setString(1, email);
               
                ResultSet table = pp.executeQuery();
                while(table.next()){
                    String fullname=table.getString("FullName");
                    String phone = table.getString("PhoneNumber");
                    String password=table.getString("PasswordHash");
                    //String email =table.getString("Email");  
                    String address = table.getString("Address");
                    
                    result=new Customer(fullname, phone, password, email, address);
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //buoc4
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            } finally {
            }
        }
        return result;
    }
    public Customer getCustomerById(int id) {
    Customer result = null;
    Connection cn = null;
    try {
        cn = DBUtils.getConnection();
        if (cn != null) {
            String sql = "SELECT CustomerID, FullName, PhoneNumber, PasswordHash, Email, "
                       + "Address, TierID, TotalBookings, TotalSpent, PointsBalance, CreatedAt, Status, roleId "
                       + "FROM dbo.Customers WHERE CustomerID = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FullName"),
                    rs.getString("PhoneNumber"),
                    rs.getString("PasswordHash"),
                    rs.getString("Email"),
                    rs.getString("Address"),
                    rs.getInt("TierID"),
                    rs.getInt("TotalBookings"),
                    rs.getDouble("TotalSpent"),
                    rs.getInt("PointsBalance"),
                    rs.getDate("CreatedAt"),
                    rs.getBoolean("Status"),
                    rs.getInt("roleId")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (cn != null) cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return result;
}
}
