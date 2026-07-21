<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redeem Rewards</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .container {
            width: 900px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 18px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.12);
        }

        h2 {
            text-align: center;
            color: #0077cc;
        }

        .balance {
            text-align: center;
            font-size: 20px;
            margin-bottom: 20px;
        }

        .balance b {
            color: #0077cc;
            font-size: 26px;
        }

        .msg {
            text-align: center;
            font-weight: bold;
            margin-bottom: 18px;
            padding: 10px;
            border-radius: 8px;
        }

        .msg-success {
            color: #155724;
            background: #d4edda;
        }

        .msg-fail {
            color: #721c24;
            background: #f8d7da;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th {
            background: #0077cc;
            color: white;
            padding: 13px;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        .redeem-btn {
            background: #28a745;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        .redeem-btn:hover {
            opacity: 0.9;
        }

        .redeem-btn:disabled {
            background: #adb5bd;
            cursor: not-allowed;
        }

        .back-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 11px 18px;
            background: #6c757d;
            color: white;
            border-radius: 9px;
            text-decoration: none;
            font-weight: bold;
        }

        .empty {
            text-align: center;
            color: red;
            font-weight: bold;
            padding: 20px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Redeem Your Points</h2>

    <p class="balance">Current balance: <b>${CUSTOMER.pointBalance}</b> points</p>

    <c:if test="${not empty MESSAGE}">
        <p class="msg ${SUCCESS ? 'msg-success' : 'msg-fail'}">${MESSAGE}</p>
    </c:if>

    <c:choose>
        <c:when test="${empty REWARDS}">
            <p class="empty">No rewards are available right now.</p>
        </c:when>

        <c:otherwise>
            <table>
                <tr>
                    <th>Reward</th>
                    <th>Description</th>
                    <th>Points Required</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="r" items="${REWARDS}">
                    <tr>
                        <td>${r.rewardName}</td>
                        <td>${r.description}</td>
                        <td>${r.pointsRequired}</td>
                        <td>
                            <form action="RedeemController" method="post"
                                  onsubmit="return confirm('Redeem ${r.pointsRequired} points for ${r.rewardName}?');">
                                <input type="hidden" name="rewardId" value="${r.rewardID}">
                                <button type="submit" class="redeem-btn"
                                        ${CUSTOMER.pointBalance < r.pointsRequired ? 'disabled' : ''}>
                                    Redeem
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <a href="ProfileController" class="back-btn">Back to Dashboard</a>

</div>

</body>
</html>
