package com.example.servlet;

import com.example.DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT user_id, username FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String retrievedUsername = resultSet.getString("username");

                // Set the user ID in the session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("username", retrievedUsername);

                // Redirect to the dashboard
                response.sendRedirect("userDashboard.jsp");
            } else {
                out.println("<p>Login Failed: Invalid username or password.</p>");
                out.println("<a href='login.jsp'>Try Again</a>");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Login Failed", e);
            out.println("<p>Login Failed: " + e.getMessage() + "</p>");
        } finally {
            out.println("</body></html>");
            out.close();
        }
    }
}
