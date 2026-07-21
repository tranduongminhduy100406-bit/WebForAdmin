<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Car Wash Booking Operations</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
        }

        body {
            background-color: #f4f6f8;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px 20px;
        }

        .booking-card {
            background: #ffffff;
            width: 100%;
            max-width: 580px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            padding: 35px;
        }

        .header {
            text-align: center;
            margin-bottom: 25px;
        }

        .header h2 {
            color: #007bff;
            font-size: 26px;
            font-weight: 700;
            margin-bottom: 6px;
        }

        .header p {
            color: #6c757d;
            font-size: 14px;
        }

        .info-box {
            background-color: #e7f5ff;
            border-left: 4px solid #007bff;
            border-radius: 6px;
            padding: 14px 18px;
            margin-bottom: 15px;
        }

        .info-box h4 {
            color: #0056b3;
            font-size: 14px;
            font-weight: 700;
            margin-bottom: 6px;
        }

        .info-box ul {
            list-style: none;
            color: #495057;
            font-size: 13px;
            line-height: 1.6;
        }

        /* Thêm style cho Tier Box đồng bộ phong cách */
        .tier-box {
            background-color: #f0f7ff;
            border-left: 4px solid #00a8ff;
            border-radius: 6px;
            padding: 14px 18px;
            margin-bottom: 25px;
            font-size: 13px;
            color: #2d3436;
        }

        .tier-box p {
            margin-bottom: 4px;
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
            margin-bottom: 15px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 18px;
        }

        label {
            font-size: 13px;
            font-weight: 600;
            color: #333333;
            margin-bottom: 8px;
        }

        label .required {
            color: #dc3545;
        }

        input[type="text"],
        input[type="date"],
        select,
        textarea {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ced4da;
            border-radius: 6px;
            font-size: 14px;
            color: #212529;
            outline: none;
            transition: border-color 0.2s, box-shadow 0.2s;
            background-color: #fff;
        }

        input:focus, select:focus, textarea:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.15);
        }

        input[readonly] {
            background-color: #e9ecef;
            color: #495057;
            cursor: not-allowed;
        }

        .slot-container {
            background-color: #f8f9fa;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 16px;
            margin: 5px 0 20px 0;
        }

        .btn-check-slot {
            width: 100%;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            font-weight: 700;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .btn-check-slot:hover {
            background-color: #0056b3;
        }

        .slot-warning {
            background-color: #fff3cd;
            color: #664d03;
            font-size: 12px;
            padding: 10px;
            border-radius: 6px;
            margin-top: 10px;
            text-align: center;
            border: 1px solid #ffecb5;
        }

        .alert-msg {
            background-color: #f8d7da;
            color: #842029;
            border: 1px solid #f5c2c7;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 15px;
            font-size: 14px;
            text-align: center;
        }

        .btn-confirm {
            width: 100%;
            background-color: #198754;
            color: white;
            border: none;
            padding: 13px;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 700;
            cursor: pointer;
            transition: background-color 0.2s;
            margin-top: 10px;
        }

        .btn-confirm:hover {
            background-color: #146c43;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="booking-card">

        <div class="header">
            <h2>Car Wash Booking</h2>
            <p>Online Booking System</p>
        </div>

        <!-- HIỂN THỊ THÔNG BÁO LỖI NẾU BỊ BẮT KHI TẠO BOOKING -->
        <c:if test="${not empty sessionScope.MSG}">
            <div class="alert-msg">
                <strong>${sessionScope.MSG}</strong>
            </div>
            <%-- Xóa thông báo khỏi session sau khi hiển thị --%>
            <c:remove var="MSG" scope="session" />
        </c:if>

        <!-- Important Notice Box -->
        <div class="info-box">
            <h4>Important Notice:</h4>
            <ul>
                <li>Maximum 5 vehicles per time slot (NFR-03)</li>
                <li>A vehicle cannot be booked twice in the same slot (NFR-02)</li>
                <li>Please check slot availability before booking.</li>
            </ul>
        </div>

        <!-- [MỚI 1] THÊM THÔNG TIN TIER THÀNH VIÊN -->
        <div class="tier-box">
            <p><strong>Your tier:</strong></p>
            <p>You can book up to <strong>7 days</strong> in advance.</p>
        </div>

        <!-- FORM CHÍNH -->
        <form action="CheckSlotController" method="post">

            <!-- Customer ID (Ẩn) & Vehicle Dropdown [MỚI 2] -->
            <input type="hidden" name="customerID" value="${not empty sessionScope.user.customerID ? sessionScope.user.customerID : (not empty param.customerID ? param.customerID : '1')}">

            <div class="form-group">
                <label>Vehicle <span class="required">*</span></label>
                <select name="vehicleID" required>
                    <option value="">-- Select Vehicle --</option>
                    <c:forEach var="v" items="${vehicleList}">
                        <option value="${v.vehicleID}" ${param.vehicleID == v.vehicleID ? 'selected' : ''}>
                            ${v.licensePlate} (${v.vehicleType})
                        </option>
                    </c:forEach>
                    <%-- Dữ liệu mẫu hiển thị nếu chưa truyền vehicleList từ Controller --%>
                    <c:if test="${empty vehicleList}">
                        <option value="1" ${param.vehicleID == '1' ? 'selected' : ''}>Vehicle 1 - 51F-123.45</option>
                        <option value="2" ${param.vehicleID == '2' ? 'selected' : ''}>Vehicle 2 - 51H-999.99</option>
                    </c:if>
                </select>
            </div>

            <!-- Booking Date & Time Slot -->
            <div class="form-grid">
                <div class="form-group">
                    <label>Booking Date <span class="required">*</span></label>
                    <input type="date" name="bookingDate" required value="${param.bookingDate}">
                </div>

                <div class="form-group">
                    <label>Time Slot <span class="required">*</span></label>
                    <select name="slotID" required>
                        <option value="">-- Select Time Slot --</option>
                        <option value="1" ${param.slotID == '1' ? 'selected' : ''}>Slot 1 (08:00 - 09:00)</option>
                        <option value="2" ${param.slotID == '2' ? 'selected' : ''}>Slot 2 (09:00 - 10:00)</option>
                        <option value="3" ${param.slotID == '3' ? 'selected' : ''}>Slot 3 (10:00 - 11:00)</option>
                        <option value="4" ${param.slotID == '4' ? 'selected' : ''}>Slot 4 (13:00 - 14:00)</option>
                        <option value="5" ${param.slotID == '5' ? 'selected' : ''}>Slot 5 (14:00 - 15:00)</option>
                    </select>
                </div>
            </div>

            <!-- Check Slot Availability Block -->
            <div class="slot-container">
                <button type="submit" formaction="CheckSlotController" formmethod="get" class="btn-check-slot">
                     Check Slot Availability
                </button>
                
                <div class="slot-warning">
                    <c:choose>
                        <c:when test="${not empty MSG_SLOT}">
                            <strong>${MSG_SLOT}</strong>
                        </c:when>
                        <c:otherwise>
                            NFR-03: Maximum 5 vehicles per slot | NFR-02: Availability check required
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Service Type [MỚI 3: Thêm data-price và sk event listener] -->
            <div class="form-group">
                <label>Service Type <span class="required">*</span></label>
                <select name="serviceID" id="serviceSelect" onchange="updateTotalAmount()" required>
                    <option value="Basic Wash" data-price="150000">Basic Wash - 150,000 VND</option>
                    <option value="Premium Wash & Wax" data-price="300000">Premium Wash & Wax - 300,000 VND</option>
                    <option value="Full Interior Detailing" data-price="500000">Full Interior Detailing - 500,000 VND</option>
                </select>
            </div>

            <!-- Total Amount [MỚI 4] -->
            <div class="form-group">
                <label>Total Amount</label>
                <input type="text" id="totalAmount" name="totalAmount" value="150000" readonly>
            </div>

            <!-- Additional Notes -->
            <div class="form-group">
                <label>Additional Notes</label>
                <textarea name="notes" rows="3" placeholder="Enter additional notes or special requests..."></textarea>
            </div>

            <!-- Confirm Booking Button -->
            <button type="submit" name="action" value="createBooking" class="btn-confirm">Confirm Booking</button>

        </form>

        <a href="adminDashboard.jsp" class="back-link">← Back to Admin Dashboard</a>

    </div>

    <!-- Script tự động cập nhật Total Amount khi đổi dịch vụ -->
    <script>
        function updateTotalAmount() {
            var select = document.getElementById("serviceSelect");
            var selectedOption = select.options[select.selectedIndex];
            var price = selectedOption.getAttribute("data-price");
            
            if (price) {
                document.getElementById("totalAmount").value = price;
            } else {
                document.getElementById("totalAmount").value = "0";
            }
        }
    </script>

</body>
</html>
