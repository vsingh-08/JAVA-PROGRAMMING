<%-- 
    Document   : login
    Created on : 7 Jan 2025, 2:29:39 pm
    Author     : Vaidehi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('images/login.jpg');
            background-size: cover; 
            background-repeat: no-repeat; 
            background-position: center; 
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            text-align: center;
        }

        .header {
            margin-bottom: 20px;
        }

        .header h1 {
            font-size: 36px;
            margin: 0;
            color: #ffcc00; 
        }

        .header p {
            font-size: 18px;
            margin: 5px 0 30px;
            color: #fff;
        }

        .container {
            background-color: rgba(26, 26, 26, 0.85); 
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5); 
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

        input[type="text"], input[type="password"] {
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

        input[type="text"]::placeholder, input[type="password"]::placeholder {
            color: #aaa;
        }

        input[type="submit"] {
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

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .error-message {
            color: red;
            margin-bottom: 10px;
            font-size: 14px;
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
    </style>
</head>
<body>
    <!-- Heading-->
    <div class="header">
        <h1>BUILDING MOMENTS</h1>
        <p>Building Events For You To Have Magical Moments.</p>
    </div>

    <!-- Login Container -->
    <div class="container">
        <h2>Login</h2>

        <!-- form to enter email and password -->
        <form action="LoginServlet" method="POST">
            <!--email-->
            <input type="text" id="email" name="email" placeholder="Enter your email" required />

            <!--password-->
            <input type="password" id="password" name="password" placeholder="Enter your password" required />

            <!--submit button-->
            <input type="submit" value="Login" />

            <!--for registering -->
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </form>

        <!--error message-->
        <% String error = request.getParameter("error"); if (error != null) { %>
            <div class="error-message"><%= error %></div>
        <% } %>
    </div>
</body>
</html>
