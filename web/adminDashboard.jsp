<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>

    <style>
        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
            font-family:Arial, Helvetica, sans-serif;
        }

        body{
            background:#eef3f8;
        }

        .header{
            background:#0d6efd;
            color:white;
            padding:18px 40px;
            display:flex;
            justify-content:space-between;
            align-items:center;
        }

        .header h2{
            font-size:28px;
        }

        .logout{
            background:white;
            color:#0d6efd;
            padding:10px 18px;
            border-radius:8px;
            text-decoration:none;
            font-weight:bold;
        }

        .container{
            width:1100px;
            margin:40px auto;
        }

        .welcome{
            margin-bottom:30px;
        }

        .welcome h3{
            color:#333;
            margin-bottom:8px;
        }

        .welcome p{
            color:#666;
        }

        .grid{
            display:grid;
            grid-template-columns:repeat(3,1fr);
            gap:30px;
        }

        .card{
            background:white;
            border-radius:15px;
            padding:35px;
            text-align:center;
            box-shadow:0 5px 15px rgba(0,0,0,.15);
            transition:.25s;
        }

        .card:hover{
            transform:translateY(-6px);
        }

        .icon{
            font-size:60px;
            margin-bottom:20px;
        }

        .card h3{
            margin-bottom:15px;
            color:#333;
        }

        .card p{
            color:#666;
            min-height:45px;
            margin-bottom:20px;
        }

        .btn{
            display:inline-block;
            padding:12px 24px;
            background:#0d6efd;
            color:white;
            text-decoration:none;
            border-radius:8px;
            font-weight:bold;
        }

        .btn:hover{
            background:#0b5ed7;
        }
    </style>

</head>

<body>

<div class="header">

    <h2>Car Wash Admin</h2>

    <a href="LogoutController" class="logout">
        Logout
    </a>

</div>

<div class="container">

    <div class="welcome">
        <h3>Welcome Admin!</h3>
        <p>Select a management function below.</p>
    </div>

    <div class="grid">

        <!-- Booking -->

        <div class="card">

            <div class="icon">📅</div>

            <h3>Manage Bookings</h3>

            <p>
                View all customer bookings and update booking status.
            </p>

            <a href="AdminBookingController" class="btn">
                View All Bookings
            </a>

        </div>

        <!-- Placeholder -->

        <div class="card">

            <div class="icon">👥</div>

            <h3>Manage Customers</h3>

            <p>
                Coming Soon...
            </p>

            <a href="#" class="btn">
                Coming Soon
            </a>

        </div>

        <!-- Placeholder -->

        <div class="card">

            <div class="icon">🚗</div>

            <h3>Manage Services</h3>

            <p>
                Coming Soon...
            </p>

            <a href="#" class="btn">
                Coming Soon
            </a>

        </div>

    </div>

</div>

</body>
</html>