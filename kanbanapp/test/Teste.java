
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author berez
 */
public class Teste {
    // Configurações do banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/kanbanapp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Conexão com o banco de dados
    private static Connection conecta;

    public static void main(String[] args) {
        try {
            // Carregar o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelecer a conexão com o banco de dados
            conecta = DriverManager.getConnection(URL, USER, PASSWORD);

            // Executar operações de CRUD
            criarUsuario("Carlos", "usuario1", "senha123");
//            lerUsuarios();
//            atualizarUsuario(1, "Joao", "usuarioAtualizado", "novaSenha");
//            deletarUsuario(1);
//            lerUsuarios();

            // Fechar a conexão
            conecta.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para criar um novo Usuario
    public static void criarUsuario(String nome, String usuario, String senha) {
        String sql = "INSERT INTO usuario (nome, usuario, senha) VALUES (?, ?, ?)";
        try (PreparedStatement st = conecta.prepareStatement(sql)) {
            st.setString(1, nome);
            st.setString(2, usuario);
            st.setString(3, senha);
            st.executeUpdate();
            System.out.println("==========-TESTE DE INSERT==========-");
            System.out.println("Usuario criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para ler todos os Usuarios
    public static void lerUsuarios() {
        String sql = "SELECT * FROM usuario";
        try (PreparedStatement st = conecta.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            System.out.println("Lista de Usuarios:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String usuario = rs.getString("usuario");
                String senha = rs.getString("senha");
                
                System.out.println("==========-TESTE DE SELECT==========-");
                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Usuario: " + usuario);
                System.out.println("Senha: " + senha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar um Usuario
    public static void atualizarUsuario(int id, String nome, String novoUsuario, String novaSenha) {
        String sql = "UPDATE usuario SET nome = ?, usuario = ?, senha = ? WHERE id = ?";
        try (PreparedStatement st = conecta.prepareStatement(sql)) {
            st.setString(1, nome);
            st.setString(2, novoUsuario);
            st.setString(3, novaSenha);
            st.setInt(4, id);
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("==========-TESTE DE UPDATE==========-");
                System.out.println("Usuario atualizado com sucesso!");
            } else {
                System.out.println("==========-TESTE DE UPDATE==========-");
                System.out.println("Usuario não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar um Usuario
    public static void deletarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement st = conecta.prepareStatement(sql)) {
            st.setInt(1, id);
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("==========-TESTE DE DELETE==========-");
                System.out.println("Usuario deletado com sucesso!");
            } else {
                System.out.println("==========-TESTE DE DELETE==========-");
                System.out.println("Usuario não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
