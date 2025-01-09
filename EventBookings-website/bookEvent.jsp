<%@ page import="com.mycompany.javaproject.DBConnection" %>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Event</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        body {
            background-color: black;
            color: white;
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: auto;
        }

        h1 {
            text-align: center;
        }

        .event-details, .ticket-options, .total-price {
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #555;
            border-radius: 5px;
            background-color: #222;
        }

        .ticket-options div {
            margin-bottom: 10px;
        }

        .ticket-options {
            display: flex;
            justify-content: space-between;
        }

        .proceed-button {
            width: 100%;
            padding: 10px;
            background-color: #1e90ff; 
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .proceed-button:disabled {
            background-color: #555;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Book Your Tickets</h1>
        <div class="event-details">
            <%
                int eventId = Integer.parseInt(request.getParameter("eventId"));
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String eventName = "";
                String eventDate = "";
                try {
                    conn = DBConnection.getConnection();
                    String query = "SELECT ename, edate, elocation FROM events WHERE eid = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, eventId);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        eventName = rs.getString("ename");
                        eventDate = rs.getString("edate");
            %>

            <p><strong>Event :</strong> <%= eventName %></p>
            <p><strong>Date :</strong> <%= eventDate %></p>
            <p><strong>Location :</strong> <%= rs.getString("elocation") %></p>

            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                }
            %>
        </div>

        <div class="ticket-options">
            <div>
                <span>Regular Ticket (₹500) :</span>
                <input type="number" id="regular-ticket" value="0" min="0">
            </div>
            <div>
                <span>VIP Ticket (₹1000) :</span>
                <input type="number" id="vip-ticket" value="0" min="0">
            </div>
        </div>

        <div class="total-price">
            Total Price: ₹<span id="total-price">0</span>
        </div>

        <button class="proceed-button" id="proceed-button" disabled onclick="proceedBooking()">Proceed</button>
    </div>

    <script>
        function updateTotalPrice() {
            const regularPrice = 500;
            const vipPrice = 1000;

            const regularCount = document.getElementById('regular-ticket').value;
            const vipCount = document.getElementById('vip-ticket').value;

            const totalPrice = (regularCount * regularPrice) + (vipCount * vipPrice);

            document.getElementById('total-price').innerText = totalPrice;

            const proceedButton = document.getElementById('proceed-button');
            proceedButton.disabled = totalPrice === 0;
        }

        document.getElementById('regular-ticket').addEventListener('input', updateTotalPrice);
        document.getElementById('vip-ticket').addEventListener('input', updateTotalPrice);

        function proceedBooking() {
            const eventName = "<%= eventName %>";
            const eventDate = "<%= eventDate %>";
            const regularCount = document.getElementById('regular-ticket').value;
            const vipCount = document.getElementById('vip-ticket').value;
            const totalAmount = document.getElementById('total-price').innerText;

            window.location.href = 'bookingConfirmation.jsp?eventName=' + encodeURIComponent(eventName) +
                '&eventDate=' + encodeURIComponent(eventDate) +
                '&regularCount=' + encodeURIComponent(regularCount) +
                '&vipCount=' + encodeURIComponent(vipCount) +
                '&totalPrice=' + encodeURIComponent(totalAmount);
        }
    </script>
</body>
</html>
