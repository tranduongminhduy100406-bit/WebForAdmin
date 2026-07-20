/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DBUtils;
import dto.Customer;
import dto.Vehicle;
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
public class VehicleDAO {
    public int createCar(Vehicle v){
        int result = 0;
        Connection cn =null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "insert [dbo].[Vehicles]([CustomerID],[LicensePlate],[Brand],[Model],[Color])\n"
                        + "values(?,?,?,?,?)";
                PreparedStatement pp = cn.prepareStatement(sql);
                pp.setInt(1, v.getCusId());
                pp.setString(2, v.getLicensePlate());
                pp.setString(3, v.getBrand());
                pp.setString(4, v.getModel());
                pp.setString(5, v.getColor());
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
    public List<Vehicle> getAllCars(int custid) {
    List<Vehicle> result = new ArrayList<>();
    Connection cn = null;

    try {
        cn = DBUtils.getConnection();

        if (cn != null) {
            String sql = "SELECT [VehicleID], [CustomerID], [LicensePlate], [Brand], [Model], [Color], [CreatedAt] "
                       + "FROM [dbo].[Vehicles] "
                       + "WHERE CustomerID = ? AND Status = 1";

            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, custid);

            ResultSet table = st.executeQuery();

            while (table.next()) {
                int id = table.getInt("VehicleID");
                String liPlate = table.getString("LicensePlate");
                String brand = table.getString("Brand");
                String model = table.getString("Model");
                String color = table.getString("Color");
                Date date = table.getDate("CreatedAt");

                Vehicle c = new Vehicle(id, custid, liPlate, brand, model, color, date);
                result.add(c);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return result;
}
    
    public Vehicle getCarByLicensePlate(String licensePlate) {
    Vehicle result = null;
    Connection cn = null;

    try {
        cn = DBUtils.getConnection();

        if (cn != null) {
            String sql = "select [VehicleID],[CustomerID],[LicensePlate],[Brand],[Model],[Color],[CreatedAt] "
           + "from [dbo].[Vehicles] where LicensePlate=?";

            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, licensePlate);

            ResultSet table = st.executeQuery();

            if (table.next()) {
                int vehicleId = table.getInt("VehicleID");
                int customerId = table.getInt("CustomerID");
                String plate = table.getString("LicensePlate");
                String brand = table.getString("Brand");
                String model = table.getString("Model");
                String color = table.getString("Color");
                Date createdAt = table.getDate("CreatedAt");

                result = new Vehicle(vehicleId, customerId, plate, brand, model, color, createdAt);
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
    
    public Vehicle getCarById(int id) {
    Vehicle result = null;
    Connection cn = null;

    try {
        cn = DBUtils.getConnection();

        if (cn != null) {
            String sql = "SELECT VehicleID, CustomerID, LicensePlate, Brand, Model, Color, CreatedAt "
                       + "FROM dbo.Vehicles WHERE VehicleID = ?";

            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                result = new Vehicle(
                    rs.getInt("VehicleID"),
                    rs.getInt("CustomerID"),
                    rs.getString("LicensePlate"),
                    rs.getString("Brand"),
                    rs.getString("Model"),
                    rs.getString("Color"),
                    rs.getDate("CreatedAt")
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
    
    public int updateCar(int id, String licenseplate, String brand, String model, String color){
        int result=0;
        Connection cn=null;
        try {
            //buoc 1: make connection
            cn=DBUtils.getConnection();
            if(cn!=null){
                //buoc 2 : viet sql
                String sql = "update [dbo].[Vehicles]\n"
                        + "set [LicensePlate]=?,\n"
                        + "[Brand]=?,\n"
                        + "[Model]=?,\n"
                        + "[Color]=?\n"
                        + "where VehicleID=?";
                PreparedStatement st=cn.prepareStatement(sql);
               st.setString(1, licenseplate);
               st.setString(2, brand);
               st.setString(3, model);
               st.setString(4, color);
               st.setInt(5, id);
               result=st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //buoc 4
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public int removeCar(int id) {
    int result = 0;
    Connection cn = null;

    try {
        cn = DBUtils.getConnection();

        if (cn != null) {

            String sql = "UPDATE Vehicles SET Status = 0 WHERE VehicleID = ?";

            PreparedStatement st = cn.prepareStatement(sql);
            
            st.setInt(1, id);

            result = st.executeUpdate();
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
    public List<Vehicle> searchVehicles(int custId, String keyword) {
        List<Vehicle> result = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [VehicleID],[CustomerID],[LicensePlate],[Brand],[Model],[Color],[CreatedAt] "
                           + "FROM [dbo].[Vehicles] "
                           + "WHERE CustomerID = ? AND ("
                           + "LicensePlate LIKE ? OR Brand LIKE ? OR Model LIKE ? OR Color LIKE ?)";
                
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, custId);
                
                String searchPattern = "%" + keyword + "%"; 
                st.setString(2, searchPattern);
                st.setString(3, searchPattern);
                st.setString(4, searchPattern);
                st.setString(5, searchPattern); 

                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int id = table.getInt("VehicleID");
                        String liPlate = table.getString("LicensePlate");
                        String brand = table.getString("Brand");
                        String model = table.getString("Model");
                        String color = table.getString("Color");
                        Date date = table.getDate("CreatedAt");
                        
                        Vehicle c = new Vehicle(id, custId, liPlate, brand, model, color, date);
                        result.add(c);
                    }
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

