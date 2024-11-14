<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Criar Conta</title>
</head>
<body>
    <form id="registroForm" onsubmit="return enviarDadosRegistro(event)">

        <h2>Criar Conta</h2>
        <div class="inputBox">
            <input type="text" required name="nome" id="nome">
            <label for="">Nome</label>
        </div>
        <div class="inputBox">
            <input type="text" required name="usuario" id="usuario">
            <label for="">Usuario</label>
        </div>
        <div class="inputBox">
            <input type="password" required name="senha" id="senha">
            <label for="">Senha</label>
        </div>
        <div class="inputBox">
            <input type="password" required id="senha2">
            <label for="">Repita&nbspsua&nbspsenha</label>
        </div>
        <div class="inputBox">
            <input type="submit" value="Criar conta">
        </div>
        <p>Já possui uma conta? <a href="index.jsp">Entrar</a></p>
    </form>

    <div id="mensagem"></div> <!-- Div para exibir mensagens de sucesso ou erro -->
    
    

    <script src="js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
</html>
