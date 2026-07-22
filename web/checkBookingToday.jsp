<%-- 
    Document   : checkBookingToday
    Created on : Jul 22, 2026, 8:25:33 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Priority Queue - Today's Bookings</title>
        <style>
            *{
                margin:0;
                padding:0;
                box-sizing:border-box;
                font-family:Arial;
            }
            body{
                background:#f4f6f9;
            }
            .container{
                width:95%;
                margin:30px auto;
            }
            h2{
                text-align:center;
                color:#0077cc;
                margin-bottom:25px;
            }
            .backBtn{
                text-decoration:none;
                background:#6c757d;
                color:white;
                padding:10px 18px;
                border-radius:8px;
                font-weight:bold;
                display:inline-block;
                margin-bottom:20px;
            }
            table{
                width:100%;
                border-collapse:collapse;
                background:white;
                box-shadow:0 5px 15px rgba(0,0,0,.15);
            }
            th{
                background:#0077cc;
                color:white;
                padding:12px;
            }
            td{
                padding:10px;
                text-align:center;
                border-bottom:1px solid #ddd;
            }
            tr:hover{
                background:#f2f2f2;
            }

            .tier-Platinum{
                color:white;
                background:#6f42c1;
                padding:4px 10px;
                border-radius:12px;
                font-weight:bold;
                font-size:12px;
            }
            .tier-Gold{
                color:white;
                background:#e0a800;
                padding:4px 10px;
                border-radius:12px;
                font-weight:bold;
                font-size:12px;
            }
            .tier-Silver{
                color:white;
                background:#6c757d;
                padding:4px 10px;
                border-radius:12px;
                font-weight:bold;
                font-size:12px;
            }
            .tier-Member{
                color:white;
                background:#17a2b8;
                padding:4px 10px;
                border-radius:12px;
                font-weight:bold;
                font-size:12px;
            }

            .pending{
                color:orange;
                font-weight:bold;
            }
            .completed{
                color:green;
                font-weight:bold;
            }
            .cancelled{
                color:red;
                font-weight:bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Priority Queue - Today's Bookings</h2>

            <a href="AdminBookingController" class="backBtn">← Back to Booking Management</a>

            <table>
                <tr>
                    <th>Booking ID</th>
                    <th>Customer</th>
                    <th>License Plate</th>
                    <th>Tier</th>
                    <th>Booking Time</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="b" items="${todayList}">
                    <tr>
                        <td>${b.bookingId}</td>
                        <td>${b.customerName}</td>
                        <td>${b.licensePlate}</td>
                        <td><span class="tier-${b.tierName}">${b.tierName}</span></td>
                        <td><fmt:formatDate value="${b.bookingTime}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td class="${b.status == 'Completed' ? 'completed' : (b.status == 'Cancelled' ? 'cancelled' : 'pending')}">
                            ${b.status}
                        </td>
                        <td>
                            <c:if test="${b.status == 'Pending'}">
                                <a href="CheckBookingController?action=complete&id=${b.bookingId}"
                                   class="btn"
                                   onclick="return confirm('Xác nhận đã hoàn thành rửa xe cho booking #${b.bookingId}?');">
                                    Complete
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
