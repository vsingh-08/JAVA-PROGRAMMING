/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author Vaidehi
 */
@WebServlet("/BookEventServlet")
public class BookEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("uid") == null) {
            response.sendRedirect("login.jsp?error=Please log in to book an event.");
            return;
        }

        // Get userId from session
        int userId = (int) session.getAttribute("uid");

        // Get eventId from request
        String eventId = request.getParameter("eid");
        if (eventId == null || eventId.isEmpty()) {
            response.sendRedirect("events.jsp?error=Invalid event.");
            return;
        }

        // Insert booking into the database
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO bookings (user_id, event_id, booking_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, Integer.parseInt(eventId));
            ps.executeUpdate();

            // Redirect to confirmation page
            response.sendRedirect("bookingConfirmation.jsp?eid=" + eventId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("events.jsp?error=An error occurred while booking the event.");
        }
    }
}
