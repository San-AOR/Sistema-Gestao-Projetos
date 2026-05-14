package app;

import controller.UsuarioController;
import controller.ProjetoController;
import controller.TarefaController;
import controller.EquipeController;
import model.Usuario;
import model.Projeto;
import model.Tarefa;
import java.util.List;

public class MainTeste {

    static UsuarioController usuarioController = new UsuarioController();
    static ProjetoController projetoController = new ProjetoController();
    static TarefaController tarefaController   = new TarefaController();
    static EquipeController equipeController   = new EquipeController();

  public static void main(String[] args) {

    System.out.println("=== TESTE BACKEND ===\n");

    // ✅ Ative ou desative cada teste comentando/descomentando a linha
    // testarListarUsuarios();
    // testarAutenticacaoValida();
    // testarAutenticacaoInvalida();
    // testarListarProjetos();
    // testarListarTarefas();
    // testarListarEquipes();
    // testarCadastrarUsuarioInvalido();
    testarCadastrarUsuario(); // ← ativo agora

    System.out.println("\n=== FIM DOS TESTES ===");
}

    static void testarListarUsuarios() {
        System.out.println("--- Teste: Listar Usuários ---");
        List<Usuario> usuarios = usuarioController.listar();
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() +
                " | Nome: " + u.getNome() +
                " | Perfil: " + u.getPerfil());
        }
        System.out.println("Total: " + usuarios.size() + " usuarios\n");
    }

    static void testarAutenticacaoValida() {
        System.out.println("--- Teste: Autenticacao Valida ---");
        Usuario u = usuarioController.autenticar("admin", "admin123");
        if (u != null) {
            System.out.println("PASSOU! Bem-vindo: " + u.getNome());
        } else {
            System.out.println("FALHOU! Login invalido.");
        }
        System.out.println();
    }

    static void testarAutenticacaoInvalida() {
        System.out.println("--- Teste: Autenticacao Invalida ---");
        Usuario u = usuarioController.autenticar("admin", "senhaerrada");
        if (u == null) {
            System.out.println("PASSOU! Senha errada rejeitada corretamente.");
        } else {
            System.out.println("FALHOU! Deveria ter rejeitado.");
        }
        System.out.println();
    }

    static void testarListarProjetos() {
        System.out.println("--- Teste: Listar Projetos ---");
        List<Projeto> projetos = projetoController.listar();
        for (Projeto p : projetos) {
            System.out.println("ID: " + p.getId() +
                " | Nome: " + p.getNome() +
                " | Status: " + p.getStatus());
        }
        System.out.println("Total: " + projetos.size() + " projetos\n");
    }

    static void testarListarTarefas() {
        System.out.println("--- Teste: Listar Tarefas ---");
        List<Tarefa> tarefas = tarefaController.listar();
        for (Tarefa t : tarefas) {
            System.out.println("ID: " + t.getId() +
                " | Nome: " + t.getNome() +
                " | Status: " + t.getStatus());
        }
        System.out.println("Total: " + tarefas.size() + " tarefas\n");
    }

    static void testarListarEquipes() {
        System.out.println("--- Teste: Listar Equipes ---");
        equipeController.listar().forEach(e ->
            System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome())
        );
        System.out.println();
    }

    static void testarCadastrarUsuarioInvalido() {
        System.out.println("--- Teste: Cadastrar Usuario com CPF invalido ---");
        usuarioController.cadastrar(
            "Teste", "cpf-invalido", "email@teste.com",
            "Dev", "login123", "senha123", "COLABORADOR"
        );
        System.out.println();
    }
    static void testarCadastrarUsuario() {
    System.out.println("--- Teste: Cadastrar Usuario ---");
    usuarioController.cadastrar(
        "Teste Backend",        // nome
        "111.222.333-44",       // cpf
        "teste@email.com",      // email
        "Desenvolvedor",        // cargo
        "teste123",             // login
        "senha123",             // senha
        "COLABORADOR"           // perfil
    );
    System.out.println("Verificando se foi cadastrado...");
    List<Usuario> usuarios = usuarioController.listar();
    boolean encontrado = false;
    for (Usuario u : usuarios) {
        if (u.getLogin().equals("teste123")) {
            System.out.println("PASSOU! Usuario encontrado: " + u.getNome());
            encontrado = true;
            break;
        }
    }
    if (!encontrado) {
        System.out.println("FALHOU! Usuario nao foi cadastrado.");
    }
    System.out.println();
}
}