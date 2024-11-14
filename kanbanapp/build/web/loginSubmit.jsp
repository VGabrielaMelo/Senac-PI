<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.jdbc.Driver"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtém os parâmetros do formulário
    String usuario = request.getParameter("usuario");
    String senha = request.getParameter("senha");

    Connection conecta = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    String resposta = "";

    try {
        // Carrega o driver JDBC e estabelece a conexão
        Class.forName("com.mysql.cj.jdbc.Driver");
        conecta = DriverManager.getConnection("jdbc:mysql://localhost:3307/kanbanapp", "root", "");

        // Prepara a consulta SQL
        st = conecta.prepareStatement("SELECT id, nome, usuario, senha FROM usuario WHERE usuario = ? AND senha = ?");
        st.setString(1, usuario);
        st.setString(2, senha);
        rs = st.executeQuery();

        // Verifica se o usuário foi encontrado
        if (rs.next()) {
            // Obtém os dados do usuário
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String user = rs.getString("usuario");
            String password = rs.getString("senha");

            // Armazena os dados do usuário na sessão
            session.setAttribute("userId", id);
            session.setAttribute("userName", nome);
            session.setAttribute("userUsername", user);
            session.setAttribute("userPassword", password);

            resposta = "success";  // Indica sucesso no login
        } else {
            resposta = "fail";  // Indica falha no login
        }
    } catch (ClassNotFoundException e) {
        resposta = "error";
        e.printStackTrace();
    } catch (SQLException e) {
        resposta = "error";
        e.printStackTrace();
    } finally {
        // Fecha os recursos
        if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
        if (st != null) try { st.close(); } catch (SQLException ignore) {}
        if (conecta != null) try { conecta.close(); } catch (SQLException ignore) {}
    }

    // Envia a resposta para o AJAX (success, fail ou error)
    out.print(resposta);
%>