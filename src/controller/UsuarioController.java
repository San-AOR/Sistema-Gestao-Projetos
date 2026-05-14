package controller;

import service.UsuarioService;
import model.Usuario;
import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService = new UsuarioService();

    public void cadastrar(String nome, String cpf, String email,
                          String cargo, String login, String senha, String perfil) {
        usuarioService.cadastrar(nome, cpf, email, cargo, login, senha, perfil);
    }

    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    public void atualizar(int id, String nome, String cpf, String email,
                          String cargo, String login, String senha, String perfil) {
        usuarioService.atualizar(id, nome, cpf, email, cargo, login, senha, perfil);
    }

    public void excluir(int id) {
        usuarioService.excluir(id);
    }

    public Usuario autenticar(String login, String senha) {
        return usuarioService.autenticar(login, senha);
    }
}