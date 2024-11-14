<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>

<%
    String taskId = request.getParameter("id");
    JSONObject taskDetails = new JSONObject();

    if (taskId != null) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Conexão com o banco de dados
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/kanbanapp", "root", "");
            
            // Buscar dados da tarefa
            ps = conn.prepareStatement("SELECT * FROM tarefas WHERE id = ?");
            ps.setInt(1, Integer.parseInt(taskId));
            rs = ps.executeQuery();

            if (rs.next()) {
                taskDetails.put("id", rs.getInt("id"));
                taskDetails.put("descricao", rs.getString("descricao"));
                taskDetails.put("prioridade", rs.getString("prioridade"));
                taskDetails.put("prazo", rs.getString("prazo"));
                taskDetails.put("coluna", rs.getString("coluna"));
            }

            response.getWriter().write(taskDetails.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception ignore) {}
            if (ps != null) try { ps.close(); } catch (Exception ignore) {}
            if (conn != null) try { conn.close(); } catch (Exception ignore) {}
        }
    }
%>
