/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;

import dto.Customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author OMEN
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterServlet"})
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("txtfullname");
        String phone = request.getParameter("txtphone");
        String password = request.getParameter("txtpassword");
        String confirm = request.getParameter("txtconfirm");
        String email = request.getParameter("txtemail");
        String address = request.getParameter("txtaddress");

        fullname = fullname.trim();
        phone = phone.trim();
        email = email.trim().toLowerCase();

        if(!password.equals(confirm)){
            String msg = "Please confirm correct password!";
            request.setAttribute("ERROR", msg);
            request.getRequestDispatcher("giaodiendangki.jsp").forward(request, response);
        }
        if(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
            String msg = "Please enter email correctly!";
            request.setAttribute("ERROR", msg);
            request.getRequestDispatcher("giaodiendangki.jsp").forward(request, response);
        }
        if(!phone.matches("0\\d{9}")){
            String msg = "Please enter phone number correctly!";
            request.setAttribute("ERROR", msg);
            request.getRequestDispatcher("giaodiendangki.jsp").forward(request, response);
        } 
        
        Customer c = new Customer();
        c.setFullname(fullname);
        c.setPhone(phone);
        c.setPassword(password);
        c.setEmail(email);
        c.setAddress(address);

        CustomerDAO cusdao = new CustomerDAO();

        Customer foundCus = cusdao.getCusByPhone(phone);
        Customer foundMail = cusdao.getCusByMail(email);
        if (foundCus == null && foundMail==null) {
            int resultCus = cusdao.createCus(c);
            if (resultCus >= 1) {
                String msg = "Register Successfully";
                request.setAttribute("ERROR", msg);
                request.getRequestDispatcher("giaodiendangki.jsp").forward(request, response);
            } else {
                response.getWriter().print("Loi tao customer roi");
            }
        } else {
            String msg = "Phone or Email is existed";
            request.setAttribute("ERROR", msg);
            request.getRequestDispatcher("giaodiendangki.jsp").forward(request, response);
        }
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
