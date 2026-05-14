package dao;

import connection.ConnectionFactory;
import model.Tarefa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void cadastrar(Tarefa t) {
        String sql = "INSERT INTO tarefa (nome, descricao, status, projeto_id, data_inicio) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNome());
            ps.setString(2, t.getDescricao());
            ps.setString(3, t.getStatus());
            ps.setInt(4, t.getProjetoId());
            ps.executeUpdate();
            System.out.println("Tarefa cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar tarefa: " + e.getMessage());
        }
    }

    public List<Tarefa> listar() {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefa";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }
        return lista;
    }

    public List<Tarefa> listarPorProjeto(int projetoId) {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefa WHERE projeto_id = ?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projetoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }
        return lista;
    }

    public List<Tarefa> listarPorResponsavel(int responsavelId) {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefa WHERE responsavel_id = ?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, responsavelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        }
        return lista;
    }

    private Tarefa mapear(ResultSet rs) throws SQLException {
        Tarefa t = new Tarefa();
        t.setId(rs.getInt("id"));
        t.setNome(rs.getString("nome"));
        t.setDescricao(rs.getString("descricao"));
        t.setStatus(rs.getString("status"));
        t.setProjetoId(rs.getInt("projeto_id"));
        t.setResponsavelId(rs.getInt("responsavel_id"));
        t.setDetalhes(rs.getString("detalhes"));
        t.setObservacao(rs.getString("observacao"));
        t.setDocumento(rs.getString("documento"));
        Timestamp di = rs.getTimestamp("data_inicio");
        Timestamp da = rs.getTimestamp("data_atribuicao");
        Timestamp du = rs.getTimestamp("data_ultima_atualizacao");
        Timestamp df = rs.getTimestamp("data_fim");
        t.setDataInicio(di != null ? di.toString().substring(0, 16) : "");
        t.setDataAtribuicao(da != null ? da.toString().substring(0, 16) : "");
        t.setDataUltimaAtualizacao(du != null ? du.toString().substring(0, 16) : "");
        t.setDataFim(df != null ? df.toString().substring(0, 16) : "");
        return t;
    }

    public void atualizar(Tarefa t) {
        String sqlFim = t.getStatus().equals("CONCLUIDA") ?
            ", data_fim = NOW()" : "";
        String sql = "UPDATE tarefa SET nome=?, descricao=?, status=?, projeto_id=?, responsavel_id=?, data_ultima_atualizacao=NOW()" + sqlFim + " WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNome());
            ps.setString(2, t.getDescricao());
            ps.setString(3, t.getStatus());
            ps.setInt(4, t.getProjetoId());
            if (t.getResponsavelId() > 0) {
                ps.setInt(5, t.getResponsavelId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setInt(6, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tarefa WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tarefa: " + e.getMessage());
        }
    }

    public void atribuirResponsavel(int tarefaId, int responsavelId) {
        String sql = "UPDATE tarefa SET responsavel_id=?, data_atribuicao=NOW(), data_ultima_atualizacao=NOW() WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, responsavelId);
            ps.setInt(2, tarefaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atribuir responsavel: " + e.getMessage());
        }
    }

    public void atualizarDetalhes(int id, String detalhes, String observacao, String documento, String status) {
        String sqlFim = status != null && status.equals("CONCLUIDA") ? ", data_fim = NOW()" : "";
        String sql = "UPDATE tarefa SET detalhes=?, observacao=?, documento=?, data_ultima_atualizacao=NOW()" + sqlFim;
        if (status != null) sql += ", status=?";
        sql += " WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, detalhes);
            ps.setString(2, observacao);
            ps.setString(3, documento);
            if (status != null) {
                ps.setString(4, status);
                ps.setInt(5, id);
            } else {
                ps.setInt(4, id);
            }
            ps.executeUpdate();
            System.out.println("Detalhes atualizados com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar detalhes: " + e.getMessage());
        }
         }
  
    public void salvar(int tarefaId, String nomeArquivo, String caminhoArquivo) {
        String sql = "INSERT INTO tarefa_arquivo (tarefa_id, nome_arquivo, caminho_arquivo) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tarefaId);
            ps.setString(2, nomeArquivo);
            ps.setString(3, caminhoArquivo);
            ps.executeUpdate();
            System.out.println("Arquivo salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public List<String[]> listarPorTarefa(int tarefaId) {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT nome_arquivo, caminho_arquivo, data_envio FROM tarefa_arquivo WHERE tarefa_id = ? ORDER BY data_envio ASC";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tarefaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("nome_arquivo"),
                    rs.getString("caminho_arquivo"),
                    rs.getTimestamp("data_envio").toString().substring(0, 16)
                });
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar arquivos: " + e.getMessage());
        }
        return lista;
    }

    public void excluir(int tarefaId, String nomeArquivo) {
        String sql = "DELETE FROM tarefa_arquivo WHERE tarefa_id = ? AND nome_arquivo = ?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tarefaId);
            ps.setString(2, nomeArquivo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir arquivo: " + e.getMessage());
        }
        }
    
    }