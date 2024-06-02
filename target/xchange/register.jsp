<%--
  Created by IntelliJ IDEA.
  User: olawale
  Date: 02/06/2024
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Now to get started</title>
</head>
<body>
    <h1>Register Now to get started</h1>
    <form action="register" method="post">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username"><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password"><br>
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email"><br><br>
        <input type="submit" value="Register">
    </form>

</body>
</html>
