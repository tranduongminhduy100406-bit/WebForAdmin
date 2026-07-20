<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - AutoWash Pro</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="auth-page">
    <div class="auth-card">
        <h2>Login</h2>
        <p>Welcome back to AutoWash Pro</p>

        <form action="LoginController" method="post">

            <div class="form-group">
                <label>Phone Number</label>
                <input type="text" name="txtphonenumber"
                       placeholder="Enter your phone number" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="txtpassword"
                       placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn-full">Login</button>
        </form>

        <div class="auth-link">
            Don't have an account?
            <a href="giaodiendangki.jsp">Register here</a>
        </div>

        <%
            String msg = (String) request.getAttribute("ERROR");
            if (msg != null) {
        %>
            <div class="error"><%= msg %></div>
        <%
            }
        %>
    </div>
</div>

</body>
</html>