<%-- 
    Document   : register
    Created on : 7 Jan 2025, 2:29:48 pm
    Author     : Vaidehi
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #000;
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .register-container {
            background-color: #1a1a1a;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(255, 255, 255, 0.1);
            padding: 40px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #00b4d8;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            font-size: 16px;
            border: 1px solid #444;
            border-radius: 8px;
            background-color: #2a2a2a;
            color: #fff;
            box-sizing: border-box;
        }

        input::placeholder {
            color: #aaa;
        }

        button {
            width: 100%;
            padding: 12px;
            font-size: 18px;
            border: none;
            border-radius: 8px;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        p {
            margin-top: 15px;
            font-size: 14px;
        }

        a {
            color: #00b4d8;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: red;
            margin-bottom: 10px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Register</h2>

        <!--error message-->
        <% String error = request.getParameter("error"); if (error != null) { %>
            <div class="error-message"><%= error %></div>
        <% } %>

        <!--form for registering->
        <form method="POST" action="RegisterServlet">
            <input type="text" name="name" placeholder="Enter your name" required />

            <input type="email" name="email" placeholder="Enter your email" required />

            <input type="password" name="password" placeholder="Create a password" required />

            <button type="submit">Register</button>
        </form>

        <!--link to login-->
        <p>Already have an account? <a href="login.jsp">Log in</a></p>
    </div>
</body>
</html>

