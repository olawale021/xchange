package com.example.servlet;

import com.example.DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Save user information to the database
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<p>Registration Successful</p>");
                out.println("<a href='index.jsp'>Back to Home</a>");
            } else {
                out.println("<p>Registration Failed: No rows inserted.</p>");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Registration Failed", e);
            out.println("<p>Registration Failed: " + e.getMessage() + "</p>");
        } finally {
            out.println("</body></html>");
            out.close();
        }
    }
}