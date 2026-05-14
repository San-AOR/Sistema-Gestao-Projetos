package dao;

import connection.ConnectionFactory;
import model.Usuario;
import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuario (nome, cpf, email, cargo, login, senha, perfil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getCargo());
            ps.setString(5, u.getLogin());
            ps.setString(6, u.getSenha());
            ps.setString(7, u.getPerfil());
            ps.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setEmail(rs.getString("email"));
                u.setCargo(rs.getString("cargo"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setPerfil(rs.getString("perfil"));
                lista.add(u);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Usuario u) {
        String sql = "UPDATE usuario SET nome=?, cpf=?, email=?, cargo=?, login=?, senha=?, perfil=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getCargo());
            ps.setString(5, u.getLogin());
            ps.setString(6, u.getSenha());
            ps.setString(7, u.getPerfil());
            ps.setInt(8, u.getId());
            ps.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }


    public void excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Usuário excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }


    public Usuario buscarPorLogin(String login, String senha) {
        String sql = "SELECT * FROM usuario WHERE login=? AND senha=?";
        try (Connection conn = ConnectionFactory.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setPerfil(rs.getString("perfil"));
                return u;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }
}
