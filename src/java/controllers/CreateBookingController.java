package controller;

import dao.BookingDAO;
import dto.Booking;
import dto.BookingSlot;
import dto.Customer;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "CreateBookingController", urlPatterns = {"/CreateBookingController"})
public class CreateBookingController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();

            Customer cus = (Customer) session.getAttribute("LOGIN_USER");

            if (cus == null) {
                response.sendRedirect("login_page.jsp");
                return;
            }

            int customerID = cus.getId();
            int vehicleID = Integer.parseInt(request.getParameter("vehicleID"));
            String bookingDate = request.getParameter("bookingDate");
            String serviceType = request.getParameter("serviceType");
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
 
            BookingSlot bo = new BookingSlot(customerID, vehicleID, bookingDate, serviceType, totalAmount);
                    BookingDAO dao = new BookingDAO();
            boolean check = dao.createBooking(bo);

            if (check) {
                session.setAttribute("MSG", "Booking successfully!");
            } else {
                session.setAttribute("MSG", "Booking failed!");
            }

            response.sendRedirect(request.getContextPath() + "/BookingController");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("ERROR: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("BookingController");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
