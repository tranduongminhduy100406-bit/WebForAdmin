package controllers;

import dao.LoyaltyDAO;
import dto.Customer;
import dto.CustomerTier;
import dto.Reward;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminLoyaltyController", urlPatterns = {"/AdminLoyaltyController"})
public class AdminLoyaltyController extends HttpServlet {

    private final LoyaltyDAO loyaltyDao = new LoyaltyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer admin = session == null ? null : (Customer) session.getAttribute("LOGIN_ADMIN");

        if (admin == null) {
            response.sendRedirect("login_page.jsp");
            return;
        }

        List<CustomerTier> tiers = loyaltyDao.getAllTiers();
        List<Reward> rewards = loyaltyDao.getAllRewardsForAdmin();

        request.setAttribute("TIERS", tiers);
        request.setAttribute("REWARDS", rewards);

        request.getRequestDispatcher("adminLoyalty.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Customer admin = session == null ? null : (Customer) session.getAttribute("LOGIN_ADMIN");

        if (admin == null) {
            response.sendRedirect("login_page.jsp");
            return;
        }

        String action = request.getParameter("action");
        List<String> log = null;

        if ("reviewTiers".equals(action)) {

            log = loyaltyDao.runMonthlyTierReview();
            request.setAttribute("LOG_TITLE", "Tier Review Result");

        } else if ("expirePoints".equals(action)) {

            log = loyaltyDao.runExpirePoints();
            request.setAttribute("LOG_TITLE", "Points Expiry Result");

        } else if ("createReward".equals(action)) {

            log = handleCreateReward(request);
            request.setAttribute("LOG_TITLE", "Create Discount Reward Result");

        } else if ("toggleReward".equals(action)) {

            log = handleToggleReward(request);
            request.setAttribute("LOG_TITLE", "Reward Status Updated");

        } else {

            log = java.util.Collections.singletonList("Unknown action.");
            request.setAttribute("LOG_TITLE", "Result");
        }

        request.setAttribute("LOG", log);
        request.setAttribute("TIERS", loyaltyDao.getAllTiers());
        request.setAttribute("REWARDS", loyaltyDao.getAllRewardsForAdmin());

        request.getRequestDispatcher("adminLoyalty.jsp").forward(request, response);
    }

    /**
     * Admin creates a new discount-type reward, e.g.
     * "10% Off Next Wash" (PERCENT, 10) requiring 300 points, or
     * "Free Wax" (FREE, 0) requiring 500 points.
     */
    private List<String> handleCreateReward(HttpServletRequest request) {

        List<String> log = new ArrayList<>();

        try {
            String rewardName = request.getParameter("rewardName");
            String description = request.getParameter("description");
            String discountType = request.getParameter("discountType"); // PERCENT | AMOUNT | FREE
            String pointsStr = request.getParameter("pointsRequired");
            String valueStr = request.getParameter("discountValue");

            if (rewardName == null || rewardName.trim().isEmpty()
                    || pointsStr == null || pointsStr.trim().isEmpty()
                    || discountType == null || discountType.trim().isEmpty()) {
                log.add("Please fill in reward name, points required, and discount type.");
                return log;
            }

            int pointsRequired = Integer.parseInt(pointsStr.trim());
            double discountValue = 0;

            if (!"FREE".equals(discountType)) {
                if (valueStr == null || valueStr.trim().isEmpty()) {
                    log.add("Please enter a discount value for " + discountType + ".");
                    return log;
                }
                discountValue = Double.parseDouble(valueStr.trim());

                if ("PERCENT".equals(discountType) && (discountValue <= 0 || discountValue > 100)) {
                    log.add("Percent discount must be between 1 and 100.");
                    return log;
                }
                if ("AMOUNT".equals(discountType) && discountValue <= 0) {
                    log.add("Discount amount must be greater than 0.");
                    return log;
                }
            }

            if (pointsRequired <= 0) {
                log.add("Points required must be greater than 0.");
                return log;
            }

            boolean ok = loyaltyDao.createReward(rewardName.trim(), pointsRequired,
                    description == null ? "" : description.trim(), discountType, discountValue);

            log.add(ok
                    ? "Created reward \"" + rewardName.trim() + "\" successfully."
                    : "Failed to create reward. Please try again.");

        } catch (NumberFormatException e) {
            log.add("Points required and discount value must be numbers.");
        } catch (Exception e) {
            e.printStackTrace();
            log.add("Error creating reward: " + e.getMessage());
        }

        return log;
    }

    private List<String> handleToggleReward(HttpServletRequest request) {

        List<String> log = new ArrayList<>();

        try {
            int rewardId = Integer.parseInt(request.getParameter("rewardId"));
            boolean newStatus = "1".equals(request.getParameter("newStatus"));

            boolean ok = loyaltyDao.toggleRewardStatus(rewardId, newStatus);

            log.add(ok
                    ? "Reward #" + rewardId + " is now " + (newStatus ? "ACTIVE" : "INACTIVE") + "."
                    : "Failed to update reward status.");

        } catch (Exception e) {
            e.printStackTrace();
            log.add("Error updating reward status: " + e.getMessage());
        }

        return log;
    }
}
