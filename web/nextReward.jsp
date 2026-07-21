<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Next Reward</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .container {
            width: 700px;
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
            font-size: 18px;
            margin-bottom: 25px;
            color: #555;
        }

        .balance b {
            color: #0077cc;
        }

        .tier-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
            font-size: 20px;
            font-weight: bold;
        }

        .tier-current {
            color: #28a745;
        }

        .tier-next {
            color: #fd7e14;
        }

        .progress-outer {
            width: 100%;
            height: 26px;
            background: #e9ecef;
            border-radius: 13px;
            overflow: hidden;
            margin: 15px 0;
        }

        .progress-inner {
            height: 100%;
            background: linear-gradient(90deg, #0077cc, #28a745);
            color: white;
            text-align: center;
            line-height: 26px;
            font-weight: bold;
            font-size: 13px;
            transition: width 0.4s ease;
        }

        .detail-box {
            background: #f8f9fa;
            border-radius: 12px;
            padding: 18px;
            margin-top: 20px;
        }

        .detail-box p {
            margin: 8px 0;
        }

        .max-badge {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            color: #d4af37;
            margin: 20px 0;
        }

        .back-btn {
            display: inline-block;
            margin-top: 25px;
            padding: 11px 18px;
            background: #6c757d;
            color: white;
            border-radius: 9px;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Your Loyalty Progress</h2>

    <p class="balance">Current points balance: <b>${POINT_BALANCE}</b></p>

    <c:choose>
        <c:when test="${empty INFO}">
            <p class="balance">Unable to load loyalty progress right now.</p>
        </c:when>

        <c:when test="${INFO.maxTierReached}">
            <div class="tier-row">
                <span class="tier-current">${INFO.currentTier}</span>
            </div>
            <p class="max-badge">You've reached the highest tier. Enjoy your VIP perks!</p>
        </c:when>

        <c:otherwise>
            <div class="tier-row">
                <span class="tier-current">${INFO.currentTier}</span>
                <span class="tier-next">${INFO.nextTier}</span>
            </div>

            <div class="progress-outer">
                <div class="progress-inner" style="width: ${INFO.progressPercent}%;">
                    ${INFO.progressPercent}%
                </div>
            </div>

            <div class="detail-box">
                <p><b>${INFO.remainBookings}</b> more booking(s) to reach <b>${INFO.nextTier}</b></p>
                <p>At <b>${INFO.nextTier}</b> you'll earn <b>+${INFO.nextPointBonusPercent}%</b> bonus points on every booking</p>
            </div>
        </c:otherwise>
    </c:choose>

    <a href="ProfileController" class="back-btn">Back to Dashboard</a>

</div>

</body>
</html>
