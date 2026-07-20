package controller;

import dao.BookingDAO;
import dto.MyBooking;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AdminBookingController", urlPatterns = {"/AdminBookingController"})
public class AdminBookingController extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            BookingDAO dao = new BookingDAO();

            String keyword = request.getParameter("keyword");

            List<MyBooking> list;

            if (keyword == null || keyword.trim().isEmpty()) {

                list = dao.getAllBookings();

            } else {

                list = dao.searchBookings(keyword);

            }

            request.setAttribute("LIST", list);
            request.setAttribute("KEYWORD", keyword);

            request.setAttribute("LIST", list);

            request.getRequestDispatcher("adminBookingView.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminDashboard.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

}
