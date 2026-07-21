package controllers;

import dao.LoyaltyDAO;
import dto.Customer;
import dto.CustomerTier;
import dto.Reward;
import java.io.IOException;
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
        List<Reward> rewards = loyaltyDao.getActiveRewards();

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
        List<String> log;

        if ("reviewTiers".equals(action)) {
            log = loyaltyDao.runTierReview();
            request.setAttribute("LOG_TITLE", "Tier Review Result");
        } else if ("expirePoints".equals(action)) {
            log = loyaltyDao.runPointsExpiry();
            request.setAttribute("LOG_TITLE", "Points Expiry Result");
        } else {
            log = java.util.Collections.singletonList("Unknown action.");
            request.setAttribute("LOG_TITLE", "Result");
        }

        request.setAttribute("LOG", log);
        request.setAttribute("TIERS", loyaltyDao.getAllTiers());
        request.setAttribute("REWARDS", loyaltyDao.getActiveRewards());

        request.getRequestDispatcher("adminLoyalty.jsp").forward(request, response);
    }
}