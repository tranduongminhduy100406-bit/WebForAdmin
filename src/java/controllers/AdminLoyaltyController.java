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

        .create-box {
            background: white;
            border-radius: 12px;
            padding: 20px 25px;
            box-shadow: 0 5px 15px rgba(0,0,0,.1);
            margin-bottom: 25px;
        }

        .create-box h3 {
            color: #333;
            margin-bottom: 15px;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 14px;
            margin-bottom: 14px;
        }

        .form-grid label {
            display: block;
            font-size: 13px;
            color: #555;
            margin-bottom: 4px;
            font-weight: bold;
        }

        .form-grid input,
        .form-grid select {
            width: 100%;
            padding: 9px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        .create-submit-btn {
            background: #198754;
            color: white;
            border: none;
            padding: 12px 22px;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
        }

        .badge {
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            color: white;
        }

        .badge-active {
            background: #198754;
        }

        .badge-inactive {
            background: #adb5bd;
        }

        .toggle-btn {
            border: none;
            padding: 6px 14px;
            border-radius: 6px;
            color: white;
            font-weight: bold;
            cursor: pointer;
            font-size: 13px;
        }

        .toggle-on {
            background: #dc3545;
        }

        .toggle-off {
            background: #0d6efd;
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

    <div class="create-box">
        <h3>Create Discount Reward</h3>
        <form action="AdminLoyaltyController" method="post">
            <input type="hidden" name="action" value="createReward">

            <div class="form-grid">
                <div>
                    <label>Reward Name</label>
                    <input type="text" name="rewardName" placeholder="e.g. 10% Off Next Wash" required>
                </div>
                <div>
                    <label>Discount Type</label>
                    <select name="discountType" required>
                        <option value="PERCENT">Percent discount (%)</option>
                        <option value="AMOUNT">Fixed amount discount (VND)</option>
                        <option value="FREE">Free service (e.g. free wax)</option>
                    </select>
                </div>
                <div>
                    <label>Discount Value</label>
                    <input type="number" step="0.01" name="discountValue"
                           placeholder="e.g. 10 for 10%, or 50000 for VND">
                </div>
                <div>
                    <label>Points Required</label>
                    <input type="number" name="pointsRequired" placeholder="e.g. 300" required>
                </div>
                <div style="grid-column: span 2;">
                    <label>Description</label>
                    <input type="text" name="description" placeholder="Shown to customers on the redeem page">
                </div>
            </div>

            <button type="submit" class="create-submit-btn">+ Create Reward</button>
        </form>
    </div>

    <h3 style="margin-bottom:12px;color:#333;">Rewards</h3>
    <table>
        <tr>
            <th>Reward</th>
            <th>Discount</th>
            <th>Points Required</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <c:forEach var="r" items="${REWARDS}">
            <tr>
                <td>${r.rewardName}</td>
                <td>${r.discountLabel}</td>
                <td>${r.pointsRequired}</td>
                <td>${r.description}</td>
                <td>
                    <span class="badge ${r.active ? 'badge-active' : 'badge-inactive'}">
                        ${r.active ? 'ACTIVE' : 'INACTIVE'}
                    </span>
                </td>
                <td>
                    <form action="AdminLoyaltyController" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="toggleReward">
                        <input type="hidden" name="rewardId" value="${r.rewardID}">
                        <input type="hidden" name="newStatus" value="${r.active ? '0' : '1'}">
                        <button type="submit" class="toggle-btn ${r.active ? 'toggle-on' : 'toggle-off'}">
                            ${r.active ? 'Deactivate' : 'Activate'}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>
