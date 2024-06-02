<%@ page import="java.sql.*, com.example.DatabaseConnection" %>

<%
    // Check for existing session and user ID

    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Retrieve username from session attribute
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>
<h1>Welcome, <%= username %>!</h1>
<p>This is your user dashboard.</p>
<!-- Future sections for user information listings can go here -->
<a href="logout">Logout</a>
</body>
</html>
