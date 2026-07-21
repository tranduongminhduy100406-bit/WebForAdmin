<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Booking Management</title>

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

            .top{
                display:flex;
                justify-content:space-between;
                margin-bottom:20px;
            }

            .backBtn{
                text-decoration:none;
                background:#6c757d;
                color:white;
                padding:10px 18px;
                border-radius:8px;
                font-weight:bold;
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

            .btn{
                padding:6px 12px;
                border:none;
                border-radius:6px;
                cursor:pointer;
                color:white;
                text-decoration:none;
                font-size:13px;
            }

            .update{
                background:#28a745;
            }

            .view{
                background:#17a2b8;
            }
        </style>

    </head>

    <body>

        <div class="container">

            <h2>Booking Management</h2>

            <div class="top">

                <a href="adminDashboard.jsp" class="backBtn">
                    ← Back Dashboard
                </a>

            </div>

            <div style="margin-bottom:20px;">

                <form action="AdminBookingController" method="get">

                    <select name="type" style="padding:10px;">

                        <option value="customer"
                                ${TYPE=="customer"?"selected":""}>
                            Customer
                        </option>

                        <option value="plate"
                                ${TYPE=="plate"?"selected":""}>
                            License Plate
                        </option>
                        
                        <option value="service"
                                ${TYPE=="service"?"selected":""}>
                            Service
                        </option>

                        <option value="status"
                                ${TYPE=="status"?"selected":""}>
                            Booking Status
                        </option>

                    </select>

                    <input type="text"
                           name="keyword"
                           value="${KEYWORD}"
                           placeholder="Enter keyword"
                           style="padding:10px;width:250px;">

                    <input type="submit"
                           value="Search"
                           style="padding:10px 18px;">

                </form>

            </div>
            <table>

                <tr>

                    <th>ID</th>
                    <th>Customer</th>
                    <th>Vehicle</th>
                    <th>Date</th>
                    <th>Slot</th>
                    <th>Service</th>
                    <th>Status</th>

                </tr>

                <c:forEach var="b" items="${LIST}">

                    <tr>

                        <td>${b.bookingID}</td>

                        <td>${b.customerID}</td>

                        <td>${b.licensePlate}</td>

                        <td>${b.bookingDate}</td>

                        <td>${b.slotTime}</td>

                        <td>${b.serviceType}</td>

                        <td>

                            <c:choose>

                                <c:when test="${b.bookingStatus=='Pending'}">
                                    <span class="pending">
                                        ${b.bookingStatus}
                                    </span>
                                </c:when>

                                <c:when test="${b.bookingStatus=='Completed'}">
                                    <span class="completed">
                                        ${b.bookingStatus}
                                    </span>
                                </c:when>

                                <c:otherwise>
                                    <span class="cancelled">
                                        ${b.bookingStatus}
                                    </span>
                                </c:otherwise>

                            </c:choose>

                        </td>



                    </tr>

                </c:forEach>

            </table>

        </div>

    </body>
</html>