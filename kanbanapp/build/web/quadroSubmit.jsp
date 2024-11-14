<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



 <% 
     // Captura os parâmetros do formulário
     String descricao = request.getParameter("descricao");
     String prioridade = request.getParameter("prioridade");
     String prazo = request.getParameter("prazo");
     String coluna = request.getParameter("coluna");

     // Verifica se os parâmetros não são nulos antes de tentar inseri-los
     if (descricao == null || prioridade == null || prazo == null || coluna == null) {
         response.getWriter().println("Erro: parâmetros não recebidos corretamente.");
         return; // Evita continuar a execução do código se algo estiver errado.
     }

     // Conectar ao banco de dados
     Connection conecta = null;
     PreparedStatement st = null;
     String resposta = "";

     try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         conecta = DriverManager.getConnection("jdbc:mysql://localhost:3307/kanbanapp", "root", "");

         // Inserir tarefa no banco de dados
         st = conecta.prepareStatement("INSERT INTO tarefas (descricao, prioridade, prazo, coluna) VALUES (?, ?, ?, ?)");
         st.setString(1, descricao);
         st.setString(2, prioridade);
         st.setString(3, prazo);
         st.setString(4, coluna);

         // Executar o insert
         st.executeUpdate();

         

     } catch (ClassNotFoundException e) {
         resposta = "Erro: Driver JDBC não encontrado.";
         e.printStackTrace();
     } catch (SQLException e) {
         e.printStackTrace();
     } finally {
         // Fecha os recursos
         if (st != null) try { st.close(); } catch (SQLException ignore) {}
         if (conecta != null) try { conecta.close(); } catch (SQLException ignore) {}
         // Redireciona para o quadro após inserção
         response.sendRedirect("quadro.jsp");
     }
 %>