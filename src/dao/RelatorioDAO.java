package dao;

import connection.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public void tarefasPorStatus() {
        String sql = "SELECT status, COUNT(*) as quantidade FROM tarefa GROUP BY status";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Tarefas por Status ===");
            while (rs.next()) {
                System.out.println("Status: " + rs.getString("status") +
                    " | Quantidade: " + rs.getInt("quantidade"));
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void projetosAtrasados() {
        String sql = "SELECT id, nome, data_fim_prevista, status FROM projeto " +
                     "WHERE data_fim_prevista < CURDATE() AND status != 'CONCLUIDO'";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Projetos Atrasados ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                    " | Nome: " + rs.getString("nome") +
                    " | Fim Previsto: " + rs.getString("data_fim_prevista"));
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void projetosEmAndamento() {
        String sql = "SELECT id, nome, data_inicio, data_fim_prevista FROM projeto " +
                     "WHERE status = 'EM_ANDAMENTO'";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Projetos em Andamento ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                    " | Nome: " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void tarefasPorProjeto() {
        String sql = "SELECT p.nome as projeto, t.nome as tarefa, t.status " +
                     "FROM tarefa t JOIN projeto p ON t.projeto_id = p.id " +
                     "ORDER BY p.nome";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Tarefas por Projeto ===");
            while (rs.next()) {
                System.out.println("Projeto: " + rs.getString("projeto") +
                    " | Tarefa: " + rs.getString("tarefa") +
                    " | Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public Object[][] getTarefasPorStatus() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT status, COUNT(*) as quantidade FROM tarefa GROUP BY status";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{rs.getString("status"), rs.getInt("quantidade")});
            }
            } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return lista.toArray(new Object[0][]);
    }

    public Object[][] getProjetosAtrasados() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nome, data_fim_prevista, status FROM projeto " +
                     "WHERE data_fim_prevista < CURDATE() AND status != 'CONCLUIDO'";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"), rs.getString("nome"),
                    rs.getString("data_fim_prevista"), rs.getString("status")
                });
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return lista.toArray(new Object[0][]);
    }

    public Object[][] getProjetosEmAndamento() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, nome, data_inicio, data_fim_prevista FROM projeto " +
                     "WHERE status = 'EM_ANDAMENTO'";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"), rs.getString("nome"),
                    rs.getString("data_inicio"), rs.getString("data_fim_prevista")
                });
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return lista.toArray(new Object[0][]);
    }

    public Object[][] getTarefasPorProjeto() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT p.nome as projeto, t.nome as tarefa, t.status " +
                     "FROM tarefa t JOIN projeto p ON t.projeto_id = p.id " +
                     "ORDER BY p.nome";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("projeto"), rs.getString("tarefa"), rs.getString("status")
                });
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return lista.toArray(new Object[0][]);
    }
}