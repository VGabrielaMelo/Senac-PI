<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>

<%
    String nome = request.getParameter("nome");
    String usuario = request.getParameter("usuario");
    String senha = request.getParameter("senha");

    Connection conecta = null;
    PreparedStatement st = null;
    String jsonResponse = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conecta = DriverManager.getConnection("jdbc:mysql://localhost:3307/kanbanapp", "root", "");

        // Verificar se o usuário já existe
        st = conecta.prepareStatement("SELECT COUNT(*) FROM usuario WHERE usuario = ?");
        st.setString(1, usuario);
        ResultSet rs = st.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            jsonResponse = "{\"status\": \"error\", \"message\": \"Usuário já existe!\"}";
        } else {
            // Inserir novo usuário
            st = conecta.prepareStatement("INSERT INTO usuario (nome, usuario, senha) VALUES (?, ?, ?)");
            st.setString(1, nome);
            st.setString(2, usuario);
            st.setString(3, senha);
            st.executeUpdate();
            jsonResponse = "{\"status\": \"success\", \"message\": \"Usuário cadastrado com sucesso!\"}";
        }
    } catch (ClassNotFoundException e) {
        jsonResponse = "{\"status\": \"error\", \"message\": \"Erro: Driver JDBC não encontrado.\"}";
        e.printStackTrace();
    } catch (SQLException e) {
        jsonResponse = "{\"status\": \"error\", \"message\": \"Erro ao inserir dados no banco de dados.\"}";
        e.printStackTrace();
    } finally {
        if (st != null) try { st.close(); } catch (SQLException ignore) {}
        if (conecta != null) try { conecta.close(); } catch (SQLException ignore) {}
    }

    // Enviar resposta JSON
    out.print(jsonResponse);
%>