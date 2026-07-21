package controller;

import dao.AdminDAO;
import dao.CustomerDAO;
import dto.Admin;
import dto.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name="LoginController",urlPatterns={"/LoginController"})
public class LoginController extends HttpServlet{

    @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String phone = request.getParameter("txtphonenumber");
    String password = request.getParameter("txtpassword");

    AdminDAO dao = new AdminDAO();

    Customer admin = dao.checkAdminLogin(phone, password);

    if (admin != null) {

        HttpSession session = request.getSession();

        session.setAttribute("LOGIN_ADMIN", admin);

        response.sendRedirect("AdminDashboardController");

    } else {

        request.setAttribute("ERROR", "Phone number or password is incorrect.");

        request.getRequestDispatcher("login_page.jsp").forward(request, response);

    }
}

}
