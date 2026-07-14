/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CustomerDAO;
import dto.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nguye
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
            //lay giu lieu tu login_page.jsp
            String phone = request.getParameter("txtphonenumber");
            String password = request.getParameter("txtpassword");
            CustomerDAO dao = new CustomerDAO();
            Customer loginuser = dao.getCustomer(phone, password);
            
            
            //tim khong tim thay 
            if(loginuser == null){
                String msg = "Phone number or password is incorrect!!!";
                request.setAttribute("ERROR", msg);
                //ve lai trang login kem theo thong bao loi~
                request.getRequestDispatcher("login_page.jsp").forward(request, response);
            }
            //neu tim thay user
            else{
                //kiem tra xem tai khoan co bi khoa hay k = isStatus
                if(loginuser.isStatus()){
                    //bat buoc phai luu vao session vi neu k thi khi request duoc response, dung session de luu customer(loginuser)
                    //vi vay phai save loginuser vao session de su dung them cac tinh nang khac
                    request.getSession().setAttribute("LOGIN_USER", loginuser);
                    response.sendRedirect("ProfileController");
                }
                //neu tai khoan da bi khoa
                else{
                    response.getWriter().print("Access deny!!!");
                }
            }
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
        processRequest(request, response);
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
