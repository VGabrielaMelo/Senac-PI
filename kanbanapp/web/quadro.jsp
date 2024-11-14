<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mysql.jdbc.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.mysql.jdbc.Driver"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Conexão com o banco de dados
    Connection conecta = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    List<Map<String, String>> tarefas = new ArrayList<>();
    List<Map<String, String>> tarefasColuna1 = new ArrayList<>();
    List<Map<String, String>> tarefasColuna2 = new ArrayList<>();
    List<Map<String, String>> tarefasColuna3 = new ArrayList<>();
    List<Map<String, String>> tarefasColuna4 = new ArrayList<>();

    // Formatação de data
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    try {
        // Carregar o driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");
        conecta = DriverManager.getConnection("jdbc:mysql://localhost:3307/kanbanapp", "root", "");

        // Consultar todas as tarefas no banco de dados
        st = conecta.prepareStatement("SELECT * FROM tarefas");
        rs = st.executeQuery();

        // Processando o resultado da consulta e dividindo as tarefas por coluna
        while (rs.next()) {
            Map<String, String> tarefa = new HashMap<>();
            tarefa.put("id", String.valueOf(rs.getInt("id")));
            tarefa.put("descricao", rs.getString("descricao"));
            tarefa.put("prioridade", rs.getString("prioridade"));

            // Formatar o prazo (data) para o formato dd/MM/yyyy
            Date prazo = rs.getDate("prazo");
            String prazoFormatado = prazo != null ? sdf.format(prazo) : "";
            tarefa.put("prazo", prazoFormatado);

            tarefa.put("coluna", rs.getString("coluna"));

            // Dividir as tarefas de acordo com a coluna
            String coluna = rs.getString("coluna");
            if ("1".equals(coluna)) {
                tarefasColuna1.add(tarefa);
            } else if ("2".equals(coluna)) {
                tarefasColuna2.add(tarefa);
            } else if ("3".equals(coluna)) {
                tarefasColuna3.add(tarefa);
            } else if ("4".equals(coluna)) {
                tarefasColuna4.add(tarefa);
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
        if (st != null) try { st.close(); } catch (SQLException ignore) {}
        if (conecta != null) try { conecta.close(); } catch (SQLException ignore) {}
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/quadro.css">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <title>Kanban App</title>
</head>
<body>
   <%String nomeUsuario = (String) session.getAttribute("userName");%> 
    <div class="header">
        <h1>Kanban App</h1>
        <h1>Olá, <%= nomeUsuario %></h1>
    </div>

    <div class="container">
        <!-- Coluna A Fazer -->
        <div class="column" data-column="1" ondrop="drop_handler(event);" ondragover="dragover_handler(event);">
            <div class="head">
                <span>A Fazer</span>
            </div>
            <div class="body">
                <div class="cards-list">
                    <% for (Map<String, String> tarefa : tarefasColuna1) { %>
                        <div id="<%= tarefa.get("id") %>" class="card" ondblclick="openEditModal(<%= tarefa.get("id") %>)" draggable="true" ondragstart="dragstart_handler(event);">
                            <div class="info">
                                <b>ID:</b> 
                                <span><%= tarefa.get("id") %></span>
                            </div>
                            <div class="info">
                                <b>Descrição:</b>
                                <span><%= tarefa.get("descricao") %></span>
                            </div>
                            <div class="info">
                                <b>Prioridade:</b>
                                <span><%= tarefa.get("prioridade") %></span>
                            </div>
                            <div class="info">
                                <b>Prazo:</b>
                                <span><%= tarefa.get("prazo") %></span>
                            </div>
                        </div>
                    <% } %>
                </div>
                <button class="add-btn" onclick="openModal(1)">Adicionar Item</button>
            </div>
        </div>

        <!-- Coluna Em Progresso -->
        <div class="column" data-column="2" ondrop="drop_handler(event);" ondragover="dragover_handler(event);">
            <div class="head">
                <span>Em progresso</span>
            </div>
            <div class="body">
                <div class="cards-list">
                    <% for (Map<String, String> tarefa : tarefasColuna2) { %>
                       <div id="<%= tarefa.get("id") %>" class="card" ondblclick="openEditModal(<%= tarefa.get("id") %>)" draggable="true" ondragstart="dragstart_handler(event);">
                           <div class="info">
                                <b>ID:</b> 
                                <span><%= tarefa.get("id") %></span>
                            </div>
                            <div class="info">
                                <b>Descrição:</b>
                                <span><%= tarefa.get("descricao") %></span>
                            </div>
                            <div class="info">
                                <b>Prioridade:</b>
                                <span><%= tarefa.get("prioridade") %></span>
                            </div>
                            <div class="info">
                                <b>Prazo:</b>
                                <span><%= tarefa.get("prazo") %></span>
                            </div>
                        </div>
                    <% } %>
                </div>
                <button class="add-btn" onclick="openModal(2)">Adicionar Item</button>
            </div>
        </div>

        <!-- Coluna Finalizado -->
        <div class="column" data-column="3" ondrop="drop_handler(event);" ondragover="dragover_handler(event);">
            <div class="head">
                <span>Finalizado</span>
            </div>
            <div class="body">
                <div class="cards-list">
                    <% for (Map<String, String> tarefa : tarefasColuna3) { %>
                        <div id="<%= tarefa.get("id") %>" class="card" ondblclick="openEditModal(<%= tarefa.get("id") %>)" draggable="true" ondragstart="dragstart_handler(event);">
                            <div class="info">
                                <b>ID:</b> 
                                <span><%= tarefa.get("id") %></span>
                            </div>
                            <div class="info">
                                <b>Descrição:</b>
                                <span><%= tarefa.get("descricao") %></span>
                            </div>
                            <div class="info">
                                <b>Prioridade:</b>
                                <span><%= tarefa.get("prioridade") %></span>
                            </div>
                            <div class="info">
                                <b>Prazo:</b>
                                <span><%= tarefa.get("prazo") %></span>
                            </div>
                        </div>
                    <% } %>
                </div>
                <button class="add-btn" onclick="openModal(3)">Adicionar Item</button>
            </div>
        </div>

        <!-- Coluna Arquivado -->
        <div class="column" data-column="4" ondrop="drop_handler(event);" ondragover="dragover_handler(event);">
            <div class="head">
                <span>Arquivado</span>
            </div>
            <div class="body">
                <div class="cards-list">
                    <% for (Map<String, String> tarefa : tarefasColuna4) { %>
                        <div id="<%= tarefa.get("id") %>" class="card" ondblclick="openEditModal(<%= tarefa.get("id") %>)" draggable="true" ondragstart="dragstart_handler(event);">
                            <div class="info">
                                <b>ID:</b> 
                                <span><%= tarefa.get("id") %></span>
                            </div>
                            <div class="info">
                                <b>Descrição:</b>
                                <span><%= tarefa.get("descricao") %></span>
                            </div>
                            <div class="info">
                                <b>Prioridade:</b>
                                <span><%= tarefa.get("prioridade") %></span>
                            </div>
                            <div class="info">
                                <b>Prazo:</b>
                                <span><%= tarefa.get("prazo") %></span>
                            </div>
                        </div>
                    <% } %>
                </div>
                <button class="add-btn" onclick="openModal(4)">Adicionar Item</button>
            </div>
        </div>
    </div>

                <!-- MODAL DE INSERT -->
    <form id="taskForm" action="quadroSubmit.jsp" method="POST">
        <div id="modal">
            <div class="box">
                <div class="head">
                    <span id="creationModeTitle">Nova tarefa</span>
                    <span id="editingModeTitle">Editar tarefa</span>
                    <button type="button" onclick="closeModal()">X</button>
                </div>
                <div class="form">
                    <input type="hidden" id="idInput" name="id">
                    <div class="form-group">
                        <label for="description">Descrição</label>
                        <textarea id="description" name="descricao" rows="3"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="priority">Prioridade</label>
                        <select id="priority" name="prioridade">
                            <option value="Baixa">Baixa</option>
                            <option value="Média">Média</option>
                            <option value="Alta">Alta</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="column">Coluna</label>
                        <select id="column" name="coluna">
                            <option value="1">A Fazer</option>
                            <option value="2">Em progresso</option>
                            <option value="3">Finalizado</option>
                            <option value="4">Arquivado</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="deadLine">Prazo</label>
                        <input type="date" id="deadLine" name="prazo">
                    </div>

                    <button type="submit" class="novaTarefa" id="creationModeButton">Cadastrar Nova Tarefa</button>
                    <button type="submit" class="novaTarefa" id="editingModeButton">Salvar Tarefa</button>
                </div>
            </div>
        </div>
    </form>
                
                

    
    <script src="js/kanban.js"></script>
</body>
</html>