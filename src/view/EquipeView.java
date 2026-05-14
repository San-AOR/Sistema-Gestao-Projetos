package view;

import controller.EquipeController;
import model.Equipe;
import java.util.List;
import java.util.Scanner;

public class EquipeView {

    private Scanner scanner = new Scanner(System.in);
    private EquipeController controller = new EquipeController();

    public void menu() {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n=== MENU EQUIPES ===");
            System.out.println("1 - Cadastrar equipe");
            System.out.println("2 - Listar equipes");
            System.out.println("3 - Atualizar equipe");
            System.out.println("4 - Excluir equipe");
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
        System.out.println("\n--- Cadastrar Equipe ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        controller.cadastrar(nome, descricao);
    }

    private void listar() {
        System.out.println("\n--- Lista de Equipes ---");
        List<Equipe> lista = controller.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma equipe cadastrada.");
        } else {
            for (Equipe e : lista) {
                System.out.println("ID: " + e.getId() +
                        " | Nome: " + e.getNome() +
                        " | Descricao: " + e.getDescricao());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- Atualizar Equipe ---");
        System.out.print("ID da equipe a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova descricao: ");
        String descricao = scanner.nextLine();

        controller.atualizar(id, nome, descricao);
    }

    private void excluir() {
        System.out.println("\n--- Excluir Equipe ---");
        System.out.print("ID da equipe a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.excluir(id);
    }
}