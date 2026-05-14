package service;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.List;
import service.SenhaUtil;


public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(String nome, String cpf, String email,
                          String cargo, String login, String senha, String perfil) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Erro: Nome obrigatorio.");
            return;
        }
        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
    System.out.println("Erro: CPF invalido. Use o formato 000.000.000-00.");
    return;
}
if (!email.contains("@") || !email.contains(".")) {
    System.out.println("Erro: Email invalido.");
    return;
}
        if (login == null || login.isEmpty()) {
            System.out.println("Erro: Login obrigatorio.");
            return;
        }
        if (senha == null || senha.length() < 6) {
            System.out.println("Erro: Senha deve ter no minimo 6 caracteres.");
            return;
        }
        Usuario u = new Usuario();
        u.setNome(nome); u.setCpf(cpf); u.setEmail(email);
        u.setCargo(cargo); u.setLogin(login); u.setSenha(SenhaUtil.criptografar(senha)); u.setPerfil(perfil);
        usuarioDAO.cadastrar(u);
    }

    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    public void atualizar(int id, String nome, String cpf, String email,
                          String cargo, String login, String senha, String perfil) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        Usuario u = new Usuario();
        u.setId(id); u.setNome(nome); u.setCpf(cpf); u.setEmail(email);
        u.setCargo(cargo); u.setLogin(login); u.setSenha(SenhaUtil.criptografar(senha)); u.setPerfil(perfil);
        usuarioDAO.atualizar(u);
    }

    public void excluir(int id) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        usuarioDAO.excluir(id);
    }

    public Usuario autenticar(String login, String senha) {
    if (login == null || senha == null) {
        System.out.println("Erro: Login e senha obrigatorios.");
        return null;
    }
    String senhaCriptografada = SenhaUtil.criptografar(senha);
    return usuarioDAO.buscarPorLogin(login, senhaCriptografada);
}
      }
