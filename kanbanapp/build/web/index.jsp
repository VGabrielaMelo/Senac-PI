<%-- 
    Document   : index
    Created on : Nov 10, 2024, 12:15:53 AM
    Author     : berez
--%>

<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Document</title>
</head>
<body>
    <form id="LoginForm" onsubmit="enviarLogin(event)">

        <h2>Entrar</h2>

        <div class="inputBox">
            <input type="text" id="usuario" required name="usuario">
            <label for="usuario">Usuário</label>
        </div>
        <div class="inputBox">
            <input type="password" id="senha" required name="senha">
            <label for="senha">Senha</label>
        </div>
        <div class="inputBox">
            <input type="submit" value="Entrar">
        </div>
        <p>Não possui uma conta? <a href="registrar.jsp">Criar conta</a></p>
    </form>

    <script src="js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>
