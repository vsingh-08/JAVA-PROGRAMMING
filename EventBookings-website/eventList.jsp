<%-- 
    Document   : eventList
    Created on : 7 Jan 2025, 2:30:13 PM
    Author     : Vaidehi
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.mycompany.javaproject.DBConnection"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet" %>
<%
    //session validation
    if (request.getSession(false) == null || request.getSession(false).getAttribute("userId") == null) {
        response.sendRedirect("login.jsp?error=Please log in to access this page.");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Dashboard</title>
    <!--css path-->
    <link rel="stylesheet" href="css/styles.css">
    <style>
        /* web page design */
        body {
            background-color: #000;
            color: #fff;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            display: flex;
            flex-direction: column;
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }

        /* msg*/
        .welcome-message {
            text-align: center;
            margin-bottom: 40px;
        }

        .welcome-message h1 {
            font-size: 2.5em;
            color: #00b4d8;
        }

        .welcome-message p {
            font-size: 1.2em;
            color: #ccc;
        }

        /* event grid*/
        .event-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
        }

        .event-card {
            background-color: #1a1a1a;
            color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            transition: transform 0.3s ease;
        }

        .event-card:hover {
            transform: scale(1.05);
        }

        .event-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .event-content {
            padding: 20px;
        }

        .event-title {
            font-size: 1.8em;
            margin: 0 0 10px;
        }

        .event-location {
            font-size: 1em;
            color: #00b4d8;
            margin-bottom: 10px;
        }

        .event-description {
            margin-bottom: 15px;
            color: #ccc;
        }

        .event-date {
            font-size: 1.1em;
            color: #ffcc00;
            font-weight: bold;
        }

        .book-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007BFF;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
            margin-top: 15px;
            transition: background-color 0.3s ease;
        }

        .book-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="welcome-message">
            <h1>Welcome to the Event Dashboard</h1>
            <p>Explore the upcoming events and book your tickets now!</p>
        </div>

        <div class="event-grid">
            <%
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    conn = DBConnection.getConnection();
                    String query = "SELECT eid, ename, elocation, edescription, edate FROM events";
                    ps = conn.prepareStatement(query);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        String eventName = rs.getString("ename");
                        String location = rs.getString("elocation");
                        String description = rs.getString("edescription");
                        String date = rs.getString("edate");

                        // uploading images 
                        String imagePath = "";
                        if ("Japan Habba 2025".equalsIgnoreCase(eventName)) {
                            imagePath = "images/japan_habba_2025.jpg";
                        } else if ("ARJUN JANYA LIVE IN CONCERT".equalsIgnoreCase(eventName)) {
                            imagePath = "images/arjun_2025.jpg";
                        } else if ("Piyush Mishra - Live in Concert".equalsIgnoreCase(eventName)) {
                            imagePath = "images/piyush_2025.jpg";
                        } else if ("Ed Sheeran: +-=:x India Tour".equalsIgnoreCase(eventName)) {
                            imagePath = "images/edsheeran_2025.jpg";
                        } else {
                            imagePath = "images/default.jpg"; // Default image for unmatched events
                        }
            %>
            <div class="event-card">
                <img src="<%= request.getContextPath() + "/" + imagePath %>" alt="<%= eventName %>" class="event-image">
                <div class="event-content">
                    <h2 class="event-title"><%= eventName %></h2>
                    <p class="event-location"><%= location %></p>
                    <p class="event-description"><%= description %></p>
                    <p class="event-date">Date: <%= date %></p>
                    
                    <form method="GET" action="bookEvent.jsp">
                        <input type="hidden" name="eventId" value="<%= rs.getInt("eid") %>">
                        <button type="submit" class="book-button">Book Now</button>
                    </form>
                </div>
            </div>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                }
            %>
        </div>
    </div>
</body>
</html>
