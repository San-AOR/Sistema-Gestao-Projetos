package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_gestao_projetos";
    private static final String USER = "root";
    private static final String PASSWORD = "SUA_SENHA_AQUI"; // alterar conforme ambiente local

    public static Connection getConexao() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}