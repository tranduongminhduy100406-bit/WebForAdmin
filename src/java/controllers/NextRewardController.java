package controllers;

import dao.CustomerDAO;
import dao.LoyaltyDAO;
import dto.Customer;
import dto.NextRewardInfo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Shows the logged-in customer how close they are to the next loyalty
 * tier (Customer Profile requirement: "View: tier, points balance,
 * next reward"). Pure read-only screen backed by
 * LoyaltyDAO.getNextRewardInfo(...).
 */
@WebServlet(name = "NextRewardController", urlPatterns = {"/NextRewardController"})
public class NextRewardController extends HttpServlet {

    private final LoyaltyDAO loyaltyDao = new LoyaltyDAO();
    private final CustomerDAO customerDao = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer cus = session == null ? null : (Customer) session.getAttribute("LOGIN_USER");

        if (cus == null) {
            response.sendRedirect("login_page.jsp");
            return;
        }

        // Re-fetch so the point balance is never stale after a redemption.
        Customer freshCustomer = customerDao.getCustomerById(cus.getId());
        NextRewardInfo info = loyaltyDao.getNextRewardInfo(cus.getId());

        request.setAttribute("INFO", info);
        request.setAttribute("POINT_BALANCE", freshCustomer.getPointBalance());

        request.getRequestDispatcher("nextReward.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}