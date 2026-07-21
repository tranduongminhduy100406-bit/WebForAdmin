<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Loyalty Management - AutoWash Pro</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial;
        }

        body {
            background: #f4f6f9;
        }

        .container {
            width: 95%;
            max-width: 1100px;
            margin: 30px auto;
        }

        h2 {
            text-align: center;
            color: #0077cc;
            margin-bottom: 25px;
        }

        .top {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .backBtn {
            text-decoration: none;
            background: #6c757d;
            color: white;
            padding: 10px 18px;
            border-radius: 8px;
            font-weight: bold;
        }

        .actions {
            display: flex;
            gap: 15px;
            margin-bottom: 25px;
        }

        .actions form {
            display: inline-block;
        }

        .action-btn {
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            color: white;
            font-weight: bold;
            cursor: pointer;
        }

        .review-btn {
            background: #0d6efd;
        }

        .expiry-btn {
            background: #dc3545;
        }

        .log-box {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 5px 15px rgba(0,0,0,.1);
            margin-bottom: 25px;
        }

        .log-box h3 {
            color: #333;
            margin-bottom: 10px;
        }

        .log-box ul {
            list-style: none;
        }

        .log-box li {
            padding: 8px 0;
            border-bottom: 1px solid #eee;
            color: #444;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 5px 15px rgba(0,0,0,.1);
            margin-bottom: 30px;
        }

        th {
            background: #0077cc;
            color: white;
            padding: 12px;
        }

        td {
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Loyalty Program Management</h2>

    <div class="top">
        <a href="adminDashboard.jsp" class="backBtn">&larr; Back Dashboard</a>
    </div>

    <div class="actions">
        <form action="AdminLoyaltyController" method="post">
            <input type="hidden" name="action" value="reviewTiers">
            <button type="submit" class="action-btn review-btn">
                Run Tier Review (Auto Upgrade/Downgrade)
            </button>
        </form>

        <form action="AdminLoyaltyController" method="post">
            <input type="hidden" name="action" value="expirePoints">
            <button type="submit" class="action-btn expiry-btn">
                Run Points Expiry (12-month rule)
            </button>
        </form>
    </div>

    <c:if test="${not empty LOG}">
        <div class="log-box">
            <h3>${LOG_TITLE}</h3>
            <ul>
                <c:forEach var="line" items="${LOG}">
                    <li>${line}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <h3 style="margin-bottom:12px;color:#333;">Customer Tiers</h3>
    <table>
        <tr>
            <th>Tier</th>
            <th>Min Bookings</th>
            <th>Min Spend (VND)</th>
            <th>Point Bonus</th>
            <th>Priority</th>
            <th>Perks</th>
        </tr>
        <c:forEach var="t" items="${TIERS}">
            <tr>
                <td>${t.tierName}</td>
                <td>${t.minBookings}</td>
                <td><fmt:formatNumber value="${t.minSpend}" pattern="#,##0"/></td>
                <td>+${t.pointBonusPercent}%</td>
                <td>${t.priorityLevel}</td>
                <td>${t.perks}</td>
            </tr>
        </c:forEach>
    </table>

    <h3 style="margin-bottom:12px;color:#333;">Active Rewards</h3>
    <table>
        <tr>
            <th>Reward</th>
            <th>Points Required</th>
            <th>Description</th>
        </tr>
        <c:forEach var="r" items="${REWARDS}">
            <tr>
                <td>${r.rewardName}</td>
                <td>${r.pointsRequired}</td>
                <td>${r.description}</td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>
