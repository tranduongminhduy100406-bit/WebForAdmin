<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - AutoWash Pro</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="auth-page">
    <div class="auth-card">
        <h2>Register Account</h2>
        <p>Create your AutoWash Pro account</p>

        <form action="RegisterServlet" method="post">

            <div class="form-group">
                <label for="fullname">Full Name</label>
                <input type="text" id="fullname" name="txtfullname" required
                       value="${param.txtfullname}">
            </div>

            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="txtphone" required
                       value="${param.txtphone}">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="txtpassword" required>
            </div>

            <div class="form-group">
                <label for="confirm">Confirm Password</label>
                <input type="password" id="confirm" name="txtconfirm" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="txtemail"
                       placeholder="example@gmail.com" required
                       value="${param.txtemail}">
            </div>

            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="txtaddress"
                       placeholder="Ho Chi Minh City" required
                       value="${param.txtaddress}">
            </div>

            <button type="submit" class="btn-full">Register</button>
        </form>

        <div class="auth-link">
            Already have an account?
            <a href="login_page.jsp">Log In</a>
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