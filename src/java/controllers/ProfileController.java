package controllers;


import dao.VehicleDAO;

import dto.Customer;
import dto.Vehicle;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="ProfileController", urlPatterns={"/ProfileController"})
public class ProfileController extends HttpServlet {
   
   public void processRequest(HttpServletRequest request, HttpServletResponse response){
       try{
           response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
           response.setHeader("Pragma", "no-cache");
           response.setDateHeader("Expires", 0);
           Customer us = (Customer) request.getSession().getAttribute("LOGIN_USER");
           if(us != null){
               int custid = us.getId();
               
               //goi CarDAO de lay danh sach xe cua khach hang nay
               VehicleDAO d = new VehicleDAO();
               List<Vehicle> list_cars = d.getAllCars(custid);
               
               //luu list_cars vao request attribute de truyen sang trang jsp
               request.setAttribute("LISTCARS", list_cars);
               
               //chuyen tiep sang trang customerDashboard_page.jsp
               request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
           } else {
               response.sendRedirect("index.jsp");
           }
       }catch(Exception e){
          e.printStackTrace();
       } 
   }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request,response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request,response);
    }
}