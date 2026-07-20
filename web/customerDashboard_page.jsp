<%@page import="dto.Vehicle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Customer us = (Customer) session.getAttribute("LOGIN_USER");

    if (us == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Vehicle> list = (ArrayList) request.getAttribute("LISTCARS");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Customer Dashboard - AutoWash Pro</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">

        <style>
            .dashboard-page {
                min-height: 100vh;
                background: #f4f7fb;
                padding-bottom: 40px;
            }

            .dashboard-navbar {
                background: white;
                padding: 18px 60px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 3px 12px rgba(0,0,0,0.08);
            }

            .dashboard-navbar .brand {
                font-size: 24px;
                font-weight: bold;
                color: #0077cc;
            }

            .dashboard-navbar a {
                margin-left: 20px;
                color: #333;
                text-decoration: none;
                font-weight: bold;
            }

            .dashboard-navbar a:hover {
                color: #0077cc;
            }

            .dashboard-container {
                width: 1100px;
                margin: 35px auto;
            }

            .welcome-card {
                background: linear-gradient(135deg, #0077cc, #28a745);
                color: white;
                padding: 35px;
                border-radius: 20px;
                box-shadow: 0 15px 35px rgba(0,0,0,0.18);
                margin-bottom: 25px;
            }

            .welcome-card h1 {
                margin-bottom: 8px;
                font-size: 34px;
            }

            .welcome-card p {
                opacity: 0.95;
                font-size: 16px;
            }

            .card-grid {
                display: flex;
                gap: 20px;
                margin-bottom: 25px;
            }

            .info-card {
                flex: 1;
                background: white;
                padding: 25px;
                border-radius: 18px;
                box-shadow: 0 8px 25px rgba(0,0,0,0.08);
            }

            .info-card h3 {
                color: #0077cc;
                margin-bottom: 15px;
            }

            .info-card ul {
                list-style: none;
                padding: 0;
            }

            .info-card li {
                margin-bottom: 10px;
                color: #444;
            }

            .main-card {
                background: white;
                padding: 30px;
                border-radius: 18px;
                box-shadow: 0 8px 25px rgba(0,0,0,0.08);
            }

            .main-card h3 {
                color: #0077cc;
                margin-bottom: 20px;
            }

            .search-form {
                display: flex;
                gap: 12px;
                margin-bottom: 20px;
            }

            .search-form input[type="text"] {
                flex: 1;
                padding: 12px 14px;
                border: 1px solid #ccd3dd;
                border-radius: 9px;
                font-size: 15px;
            }

            .search-form input[type="submit"] {
                padding: 12px 25px;
                border: none;
                border-radius: 9px;
                background: #0077cc;
                color: white;
                font-weight: bold;
                cursor: pointer;
            }

            .search-form input[type="submit"]:hover {
                background: #005fa3;
            }

            .car-table {
                width: 100%;
                border-collapse: collapse;
                overflow: hidden;
                border-radius: 12px;
            }

            .car-table th {
                background: #0077cc;
                color: white;
                padding: 14px;
                text-align: center;
            }

            .car-table td {
                padding: 13px;
                border-bottom: 1px solid #e2e6ea;
                text-align: center;
                color: #333;
            }

            .car-table tr:hover {
                background: #f4f9ff;
            }

            .empty-row {
                color: red;
                text-align: center;
                padding: 18px;
                font-weight: bold;
            }

            .action-btn {
                padding: 8px 14px;
                border-radius: 7px;
                border: none;
                color: white;
                cursor: pointer;
                font-weight: bold;
                margin: 2px;
            }

            .edit-btn {
                background: #0077cc;
            }

            .remove-btn {
                background: #dc3545;
            }

            .edit-btn:hover {
                background: #005fa3;
            }

            .remove-btn:hover {
                background: #b02a37;
            }

            .dashboard-actions {
                margin-top: 25px;
                display: flex;
                gap: 12px;
                flex-wrap: wrap;
            }

            .dashboard-actions a,
            .dashboard-actions form {
                display: inline-block;
            }

            .dashboard-actions button {
                padding: 12px 18px;
                border: none;
                border-radius: 9px;
                color: white;
                font-weight: bold;
                cursor: pointer;
            }

            .btn-green {
                background: #28a745;
            }

            .btn-blue {
                background: #0077cc;
            }

            .btn-dark {
                background: #333;
            }

            .btn-orange {
                background: #fd7e14;
            }

            .btn-red {
                background: #dc3545;
            }
            
            .btn-brown {
                background: #8D432E;
            }

            .dashboard-actions button:hover {
                opacity: 0.9;
            }
        </style>
    </head>

    <body>

        <div class="dashboard-page">

            <div class="dashboard-navbar">
                <div class="brand">AutoWash Pro</div>
            </div>

            <div class="dashboard-container">

                <div class="welcome-card">
                    <h1>Welcome, <%= us.getFullname()%></h1>
                    <p>Manage your profile, vehicles, loyalty points and rewards in one place.</p>
                </div>

                <div class="card-grid">
                    <div class="info-card">
                        <h3>Personal Information</h3>
                        <ul>
                            <li><b>Full Name:</b> <%= us.getFullname()%></li>
                            <li><b>Email:</b> <%= us.getEmail()%></li>
                            <li><b>Created At:</b> <%= us.getCreatedAt()%></li>
                        </ul>
                    </div>

                            <div class="info-card">
                                <h3>Loyalty Program</h3>
                                <ul>
                                    <%
                                        int tierID = us.getTierId();
                                        String tierName = "Member";
                                        int maxDay = 7;

                                        if (tierID == 2) {
                                            tierName = "Silver";
                                            maxDay = 10;
                                        } else if (tierID == 3) {
                                            tierName = "Gold";
                                            maxDay = 12;
                                        } else if (tierID == 4) {
                                            tierName = "Platinum";
                                            maxDay = 14;
                                        }
                                    %>

                                    <li><b>Tier:</b> <%= tierName%></li>
                                    <li><b>Points:</b> <%= us.getPointBalance()%> points</li>
                                    <li><b>Benefit:</b> Can book up to <%= maxDay%> days in advance</li>
                                </ul>
                            </div>
                </div> 

                <div class="main-card">
                    <h3>Your Vehicles</h3>

                    <form action="SearchCarController" method="POST" class="search-form">
                        <input type="text"
                               name="txtSearch"
                               placeholder="Search by license plate, brand, model or color..."
                               value="<%= (request.getParameter("txtSearch") != null) ? request.getParameter("txtSearch") : ""%>" />

                        <input type="submit" value="Search" />
                    </form>

                    <table class="car-table">
                        <tr>
                            <th>License Plate</th>
                            <th>Brand</th>
                            <th>Model</th>
                            <th>Color</th>
                            <th>Action</th>
                        </tr>

                        <%
                            if (list != null && !list.isEmpty()) {
                                for (Vehicle c : list) {
                        %>
                        <tr>
                            <td><%= c.getLicensePlate()%></td>
                            <td><%= c.getBrand()%></td>
                            <td><%= c.getModel()%></td>
                            <td><%= c.getColor()%></td>

                            <td>
                                <a href="EditCarController?id=<%= c.getCarId()%>">
                                    <button type="button" class="action-btn edit-btn">Edit</button>
                                </a>

                                <a href="RemoveCarController?id=<%= c.getCarId()%>"
                                   onclick="return confirm('Bạn có chắc muốn remove xe này không?');">
                                    <button type="button" class="action-btn remove-btn">Remove</button>
                                </a>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5" class="empty-row">
                                You have not registered any vehicles yet. Please register a new vehicle.
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>

                    <div class="dashboard-actions">
                        <a href="dangkixe.jsp">
                            <button type="button" class="btn-green">Register New Vehicle</button>
                        </a>

                        <a href="RedeemController">
                            <button type="button" class="btn-blue">Redeem Reward</button>
                        </a>

                        <a href="WashHistoryController">
                            <button type="button" class="btn-dark">Wash History</button>
                        </a>
                        <a href="AdminLoyaltyController">
                            <button type="button" class="btn-orange">Admin Redeem</button>
                        </a>

                        <a href="NextRewardController">
                            <button type="button" class="btn-dark">Next Reward</button>
                        </a>
                        <% if (us.getRoleId() == 1) { %>
                        <a href="PriorityQueueController">
                            <button type="button" class="btn-orange">
                                Priority Queue
                            </button>
                        </a>
                        <% }%>
                        <form action="LogoutController" method="post">
                            <button type="submit" class="btn-red">Logout</button>
                        </form>
                        <a href="BookingController">
                            <button type="button" class="btn-brown">Booking Wash</button>
                        </a>
                        <a href="MyBookingController">
                            <button type="button" class="btn-blue">My Bookings</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>