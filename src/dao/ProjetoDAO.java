package dao;

import connection.ConnectionFactory;
import model.Projeto;
import java.sql.*;
import java.util.*;

public class ProjetoDAO {

    public void cadastrar(Projeto p) {
        String sql = "INSERT INTO projeto (nome, descricao, data_inicio, data_fim_prevista, status, gerente_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setDate(3, java.sql.Date.valueOf(p.getDataInicio()));
            ps.setDate(4, java.sql.Date.valueOf(p.getDataFimPrevista()));
            ps.setString(5, p.getStatus());
            ps.setInt(6, p.getGerenteId());
            ps.executeUpdate();
            System.out.println("Projeto cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar projeto: " + e.getMessage());
        }
    }

    public List<Projeto> listar() {
        List<Projeto> lista = new ArrayList<>();
        String sql = "SELECT * FROM projeto";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {          // ← CORRIGIDO (era rs.next como link)
                Projeto p = new Projeto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                p.setDataFimPrevista(rs.getDate("data_fim_prevista").toLocalDate());
                p.setStatus(rs.getString("status"));
                p.setGerenteId(rs.getInt("gerente_id"));
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar projetos: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Projeto p) {
        String sql = "UPDATE projeto SET nome=?, descricao=?, data_inicio=?, data_fim_prevista=?, status=?, gerente_id=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setDate(3, java.sql.Date.valueOf(p.getDataInicio()));
            ps.setDate(4, java.sql.Date.valueOf(p.getDataFimPrevista()));
            ps.setString(5, p.getStatus());
            ps.setInt(6, p.getGerenteId());
            ps.setInt(7, p.getId());
            ps.executeUpdate();
            System.out.println("Projeto atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar projeto: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM projeto WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Projeto excluído com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir projeto: " + e.getMessage());
        }
    }
}
