<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.IOException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Recuperando os dados enviados pelo formulário
    String id = request.getParameter("id");
    String descricao = request.getParameter("descricao");
    String prioridade = request.getParameter("prioridade");
    String coluna = request.getParameter("coluna");
    String prazo = request.getParameter("prazo");

    // Configuração de conexão com o banco de dados
    Connection conecta = null;
    PreparedStatement stmt = null;

    try {
        // Carregar o driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Conectar ao banco de dados
        conecta = DriverManager.getConnection("jdbc:mysql://localhost:3307/kanbanapp", "root", "");

        // Comando SQL para atualizar a tarefa
        String sql = "UPDATE tarefas SET descricao = ?, prioridade = ?, coluna = ?, prazo = ? WHERE id = ?";

        // Preparar o statement para executar o SQL
        stmt = conecta.prepareStatement(sql);
        
        // Definir os parâmetros da query
        stmt.setString(1, descricao);
        stmt.setString(2, prioridade);
        stmt.setString(3, coluna);
        stmt.setString(4, prazo);  // Prazo é uma string no formato "dd/MM/yyyy", pode ser necessário converter se for Date no banco
        stmt.setInt(5, Integer.parseInt(id)); // ID deve ser convertido para inteiro

        // Executar o comando de update
        int rowsAffected = stmt.executeUpdate();

        // Se a atualização foi bem-sucedida, exiba uma mensagem de sucesso
        if (rowsAffected > 0) {
            out.println("<script>alert('Tarefa atualizada com sucesso!');</script>");
        } else {
            out.println("<script>alert('Erro ao atualizar a tarefa.');</script>");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        out.println("<script>alert('Erro no banco de dados.');</script>");
    } finally {
        // Fechar as conexões
        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
        if (conecta != null) try { conecta.close(); } catch (SQLException ignore) {}
    }

    // Redirecionar de volta para a página do Kanban ou qualquer outra página necessária
    response.sendRedirect("quadro.jsp");
%>