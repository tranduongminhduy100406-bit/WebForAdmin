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
        
        // 1) Kiểm tra Admin login
        AdminDAO adminDao = new AdminDAO();
        Customer admin = adminDao.checkAdminLogin(phone, password);
        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_ADMIN", admin);
            response.sendRedirect("AdminDashboardController");
            return;
        }
        
        // 2) Nếu không phải Admin → Kiểm tra Customer login
        CustomerDAO customerDao = new CustomerDAO();
        Customer customer = customerDao.getCustomer(phone, password);
        if (customer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_USER", customer);
            response.sendRedirect("ProfileController");
            return;
        }
        
        // 3) Cả Admin và Customer đều thất bại → Báo lỗi
        request.setAttribute("ERROR", "Phone number or password is incorrect.");
        request.getRequestDispatcher("login_page.jsp").forward(request, response);
    }
}
