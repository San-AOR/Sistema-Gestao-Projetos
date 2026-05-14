
package dao;



import connection.ConnectionFactory;
import model.Equipe;
import java.sql.*;
import java.util.*;

public class EquipeDAO {

    public void cadastrar(Equipe e) {
        String sql = "INSERT INTO equipe (nome, descricao) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNome());
            ps.setString(2, e.getDescricao());
            ps.executeUpdate();
            System.out.println("Equipe cadastrada com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro ao cadastrar equipe: " + ex.getMessage());
        }
    }

    public List<Equipe> listar() {
        List<Equipe> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipe";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Equipe e = new Equipe();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setDescricao(rs.getString("descricao"));
                lista.add(e);
            }

        } catch (Exception ex) {
            System.out.println("Erro ao listar equipes: " + ex.getMessage());
        }
        return lista;
    }

    public void atualizar(Equipe e) {
        String sql = "UPDATE equipe SET nome=?, descricao=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNome());
            ps.setString(2, e.getDescricao());
            ps.setInt(3, e.getId());
            ps.executeUpdate();
            System.out.println("Equipe atualizada com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro ao atualizar equipe: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM equipe WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Equipe excluída com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro ao excluir equipe: " + ex.getMessage());
        }
    }
}
