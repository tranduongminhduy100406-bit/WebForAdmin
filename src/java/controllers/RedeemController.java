package controllers;

import dao.CustomerDAO;
import dao.LoyaltyDAO;
import dto.Customer;
import dto.Reward;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Customer-facing redemption screen for the Loyalty Engine.
 *
 * GET  -> shows the active rewards list plus the customer's current
 *         point balance (redeemRewards.jsp).
 * POST -> redeems the chosen reward via LoyaltyDAO.redeemPoints(...)
 *         and reloads the page with a result message.
 *
 * Wire a link/button to "RedeemController" from wherever the customer's
 * profile / dashboard page is (e.g. customerDashboard_page.jsp).
 */
@WebServlet(name = "RedeemController", urlPatterns = {"/RedeemController"})
public class RedeemController extends HttpServlet {

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

        loadRewardsAndForward(request, response, cus.getId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer cus = session == null ? null : (Customer) session.getAttribute("LOGIN_USER");

        if (cus == null) {
            response.sendRedirect("login_page.jsp");
            return;
        }

        int customerId = cus.getId();

        try {
            int rewardId = Integer.parseInt(request.getParameter("rewardId"));

            int result = loyaltyDao.redeemReward(customerId, rewardId);

            String message;
            switch (result) {
                case LoyaltyDAO.REDEEM_SUCCESS:
                    message = "Redeemed successfully! Please show this screen at the counter.";
                    break;
                case LoyaltyDAO.REDEEM_NOT_ENOUGH_POINTS:
                    message = "Not enough points for this reward.";
                    break;
                case LoyaltyDAO.REDEEM_REWARD_NOT_FOUND:
                    message = "This reward is no longer available.";
                    break;
                default:
                    message = "Something went wrong. Please try again.";
            }

            request.setAttribute("MESSAGE", message);
            request.setAttribute("SUCCESS", result == LoyaltyDAO.REDEEM_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("MESSAGE", "Invalid request.");
            request.setAttribute("SUCCESS", false);
        }

        loadRewardsAndForward(request, response, customerId);
    }

    private void loadRewardsAndForward(HttpServletRequest request, HttpServletResponse response,
            int customerId) throws ServletException, IOException {

        // Re-fetch the customer to show the up-to-date point balance
        // (session copy may be stale after a redemption).
        Customer freshCustomer = customerDao.getCustomerById(customerId);
        List<Reward> rewards = loyaltyDao.getAllRewards();

        request.setAttribute("CUSTOMER", freshCustomer);
        request.setAttribute("REWARDS", rewards);

        request.getRequestDispatcher("redeemRewards.jsp").forward(request, response);
    }
}