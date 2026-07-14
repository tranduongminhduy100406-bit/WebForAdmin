<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Bookings</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .container {
            width: 1000px;
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

        .msg {
            text-align: center;
            color: green;
            font-weight: bold;
            margin-bottom: 18px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
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

        .status-pending {
            color: #fd7e14;
            font-weight: bold;
        }

        .status-cancelled {
            color: #dc3545;
            font-weight: bold;
        }

        .status-completed {
            color: #28a745;
            font-weight: bold;
        }

        .cancel-btn {
            background: #dc3545;
            color: white;
            border: none;
            padding: 8px 13px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        .cancel-btn:hover {
            opacity: 0.9;
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

    <h2>My Bookings</h2>

    <c:if test="${not empty sessionScope.MSG}">
        <p class="msg">${sessionScope.MSG}</p>
        <c:remove var="MSG" scope="session"/>
    </c:if>

    <c:choose>
        <c:when test="${empty LIST}">
            <p class="empty">You have no bookings yet.</p>
        </c:when>

        <c:otherwise>
            <table>
                <tr>
                    <th>ID</th>
                    <th>License Plate</th>
                    <th>Date</th>
                    <th>Slot</th>
                    <th>Service</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="b" items="${LIST}">
                    <tr>
                        <td>${b.bookingID}</td>
                        <td>${b.licensePlate}</td>
                        <td>${b.bookingDate}</td>
                        <td>${b.slotTime}</td>
                        <td>${b.serviceType}</td>

                        <td>
                            <c:choose>
                                <c:when test="${b.bookingStatus == 'Pending'}">
                                    <span class="status-pending">${b.bookingStatus}</span>
                                </c:when>
                                <c:when test="${b.bookingStatus == 'Cancelled'}">
                                    <span class="status-cancelled">${b.bookingStatus}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-completed">${b.bookingStatus}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:if test="${b.bookingStatus == 'Pending'}">
                                <form action="CancelBookingController" method="post"
                                      onsubmit="return confirm('Are you sure you want to cancel this booking?');">
                                    <input type="hidden" name="demo" value="${b.bookingID}">
                                    <button type="submit" class="cancel-btn">Cancel</button>
                                </form>
                            </c:if>

                            <c:if test="${b.bookingStatus != 'Pending'}">
                                -
                            </c:if>
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