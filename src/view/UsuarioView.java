package view;

import controller.UsuarioController;
import model.Usuario;
import java.util.List;
import java.util.Scanner;

public class UsuarioView {

    private Scanner scanner = new Scanner(System.in);
    private UsuarioController controller = new UsuarioController();

    public void menu() {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n=== MENU USUARIOS ===");
            System.out.println("1 - Cadastrar usuario");
            System.out.println("2 - Listar usuarios");
            System.out.println("3 - Atualizar usuario");
            System.out.println("4 - Excluir usuario");
            System.out.println("5 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: atualizar(); break;
                case 4: excluir(); break;
                case 5: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Usuario ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Perfil (ADMIN/GERENTE/COLABORADOR): ");
        String perfil = scanner.nextLine();

        controller.cadastrar(nome, cpf, email, cargo, login, senha, perfil);
    }

    private void listar() {
        System.out.println("\n--- Lista de Usuarios ---");
        List<Usuario> lista = controller.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
        } else {
            for (Usuario u : lista) {
                System.out.println("ID: " + u.getId() +
                        " | Nome: " + u.getNome() +
                        " | Email: " + u.getEmail() +
                        " | Cargo: " + u.getCargo() +
                        " | Perfil: " + u.getPerfil());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- Atualizar Usuario ---");
        System.out.print("ID do usuario a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        System.out.print("Novo cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Novo login: ");
        String login = scanner.nextLine();
        System.out.print("Nova senha: ");
        String senha = scanner.nextLine();
        System.out.print("Novo perfil (ADMIN/GERENTE/COLABORADOR): ");
        String perfil = scanner.nextLine();

        controller.atualizar(id, nome, cpf, email, cargo, login, senha, perfil);
    }

    private void excluir() {
        System.out.println("\n--- Excluir Usuario ---");
        System.out.print("ID do usuario a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.excluir(id);
    }
}