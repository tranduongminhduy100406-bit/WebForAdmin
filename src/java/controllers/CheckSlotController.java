package controller;

import dao.BookingDAO;
import dto.BookingSlot;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "CheckSlotController", urlPatterns = {"/CheckSlotController"})
public class CheckSlotController extends HttpServlet {

    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("BookingController");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("checkSlot".equals(action)) {
            handleCheckSlot(request, response);
        } else if ("createBooking".equals(action)) {
            handleCreateBooking(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleCheckSlot(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String bookingDate = request.getParameter("bookingDate");
        String slotTime = request.getParameter("slotTime");

        response.setContentType("application/json;charset=UTF-8");

        if (bookingDate == null || bookingDate.isEmpty()
                || slotTime == null || slotTime.isEmpty()) {

            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Please fill in all required fields.\"}");
            return;
        }

        boolean available = bookingDAO.isSlotAvailable(bookingDate, slotTime);
        int currentBookings = bookingDAO.getBookingCountByDateAndSlot(bookingDate, slotTime);

        response.getWriter().write(
                "{"
                + "\"success\":true,"
                + "\"available\":" + available + ","
                + "\"currentBookings\":" + currentBookings + ","
                + "\"maxBookings\":5"
                + "}");
    }

    private void handleCreateBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            int customerID = Integer.parseInt(request.getParameter("customerID"));
            int vehicleID = Integer.parseInt(request.getParameter("vehicleID"));

            String bookingDate = request.getParameter("bookingDate");
            String slotTime = request.getParameter("slotTime");
            String serviceType = request.getParameter("serviceType");
            String notes = request.getParameter("notes");

            if (bookingDate == null || bookingDate.isEmpty()
                    || slotTime == null || slotTime.isEmpty()
                    || serviceType == null || serviceType.isEmpty()) {

                session.setAttribute("MSG", "Please fill in all required information.");
                response.sendRedirect("BookingController");
                return;
            }

            if (!bookingDAO.isSlotAvailable(bookingDate, slotTime)) {
                int currentCount = bookingDAO.getBookingCountByDateAndSlot(bookingDate, slotTime);

                session.setAttribute("MSG",
                        "Selected slot is full (" + currentCount + "/5 bookings). Please choose another slot.");

                response.sendRedirect("BookingController");
                return;
            }

            if (!bookingDAO.isVehicleAvailableForSlot(vehicleID, bookingDate, slotTime)) {
                session.setAttribute("MSG", "This vehicle already has a booking in the selected slot.");
                response.sendRedirect("BookingController");
                return;
            }

            BookingSlot booking = new BookingSlot(
                    customerID,
                    vehicleID,
                    bookingDate,
                    slotTime,
                    serviceType,
                    notes
            );

            boolean result = bookingDAO.createBooking(booking);

            if (result) {
                session.setAttribute("MSG", "Booking created successfully!");
            } else {
                session.setAttribute("MSG", "Failed to create booking. Please try again.");
            }

            response.sendRedirect("BookingController");

        } catch (NumberFormatException e) {
            session.setAttribute("MSG", "Invalid input. Customer ID and Vehicle ID must be numeric.");
            response.sendRedirect("BookingController");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("MSG", "System Error: " + e.getMessage());
            response.sendRedirect("BookingController");
        }
    }
}
