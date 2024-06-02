package com.example.servlet;

import com.example.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class UserDashboardServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserDashboardServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String username = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT username FROM users WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("username");
                session.setAttribute("username", username);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load user data", e);
            response.sendRedirect("login.jsp");
            return;
        }

        request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
    }
}
