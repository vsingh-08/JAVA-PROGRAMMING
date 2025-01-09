<%-- 
    Document   : bookingConfirmation
    Created on : 7 Jan 2025, 6:21:04 pm
    Author     : Vaidehi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation</title>
    <style>
        body {
            background-color: black;
            color: white;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .confirmation-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: #333;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(255, 255, 255, 0.2);
            text-align: center;
        }
        .confirmation-header {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .details {
            margin: 20px 0;
            line-height: 1.6;
        }
        .button-container {
            margin-top: 20px;
        }
        .back-button {
            background-color: #007BFF; 
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }
        .back-button:hover {
            background-color: #0056b3; 
        }
    </style>
</head>
<body>
    <div class="confirmation-container">
        <div class="confirmation-header">Booking Confirmation</div>
        <p>Your booking has been confirmed!</p>
        <div class="details">
            <p><strong>Event Name:</strong> <%= request.getParameter("eventName") %></p>
            <p><strong>Event Date:</strong> <%= request.getParameter("eventDate") %></p>
            <p><strong>Regular Tickets:</strong> <%= request.getParameter("regularCount") %></p>
            <p><strong>VIP Tickets:</strong> <%= request.getParameter("vipCount") %></p>
            <p><strong>Total Bill Amount:</strong> ₹<%= request.getParameter("totalPrice") %></p>
        </div>
        <div class="button-container">
            <a class="back-button" href="eventList.jsp">Go Back to Event List</a>
        </div>
    </div>
</body>
</html>
