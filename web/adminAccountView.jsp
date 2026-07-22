<%-- 
    Document   : adminAccountView
    Created on : Jul 21, 2026, 3:15:30 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Account Management</title>

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

            <h2>Account & Role Management</h2>

            <div class="top">

                <a href="adminDashboard.jsp" class="backBtn">
                    ← Back Dashboard
                </a>

            </div>

            <div style="margin-bottom:20px;">

                <form action="AdminAccountController" method="get">

                    <input type="text"
                           name="keyword"
                           value="${KEYWORD}"
                           placeholder="Search Phone Number , Email"
                           style="padding:10px;width:300px;">

                    <input type="submit"
                           value="Search"
                           style="padding:10px 18px;">

                </form>

            </div>
            <table>

                <tr>

                    <th>ID</th>
                    <th>FullName</th>
                    <th>PhoneNumber</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>RoleID</th>
                    <th>Status</th>
                    <th>Make Admin</th>

                </tr>

                <c:forEach var="c" items="${customerList}">

                    <tr>

                        <td>${c.id}</td>

                        <td>${c.fullname}</td>

                        <td>${c.phone}</td>

                        <td>${c.email}</td>

                        <td>${c.address}</td>

                        <td>${c.roleId}</td>

                        <td>${c.status}</td>

                        <td>
                            <c:choose>
                                <c:when test="${c.roleId == 1}">
                                    <a href="AdminAccountController?action=removeAdmin&id=${c.id}"
                                       class="btn"
                                       style="background:#dc3545;"
                                    >
                                        Remove Admin
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="AdminAccountController?action=makeAdmin&id=${c.id}"
                                       class="btn update"
                                    >
                                        Make Admin
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>




                    </tr>

                </c:forEach>

            </table>

        </div>

    </body>
</html>
