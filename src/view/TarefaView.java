package view;

import controller.TarefaController;
import model.Tarefa;
import java.util.List;
import java.util.Scanner;

public class TarefaView {

    private Scanner scanner = new Scanner(System.in);
    private TarefaController controller = new TarefaController();

    public void menu() {
        int opcao = 0;
        while (opcao != 6) {
            System.out.println("\n=== MENU TAREFAS ===");
            System.out.println("1 - Cadastrar tarefa");
            System.out.println("2 - Listar todas as tarefas");
            System.out.println("3 - Listar tarefas por projeto");
            System.out.println("4 - Atualizar tarefa");
            System.out.println("5 - Excluir tarefa");
            System.out.println("6 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: listarPorProjeto(); break;
                case 4: atualizar(); break;
                case 5: excluir(); break;
                case 6: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Tarefa ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();
        System.out.print("Status (PENDENTE/EM_ANDAMENTO/CONCLUIDA): ");
        String status = scanner.nextLine();
        System.out.print("ID do projeto: ");
        int projetoId = scanner.nextInt();
        scanner.nextLine();

        controller.cadastrar(nome, descricao, status, projetoId);
    }

    private void listar() {
        System.out.println("\n--- Lista de Tarefas ---");
        List<Tarefa> lista = controller.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            for (Tarefa t : lista) {
                System.out.println("ID: " + t.getId() +
                        " | Nome: " + t.getNome() +
                        " | Status: " + t.getStatus() +
                        " | Projeto ID: " + t.getProjetoId());
            }
        }
    }

    private void listarPorProjeto() {
        System.out.println("\n--- Tarefas por Projeto ---");
        System.out.print("ID do projeto: ");
        int projetoId = scanner.nextInt();
        scanner.nextLine();

        List<Tarefa> lista = controller.listarPorProjeto(projetoId);
        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para este projeto.");
        } else {
            for (Tarefa t : lista) {
                System.out.println("ID: " + t.getId() +
                        " | Nome: " + t.getNome() +
                        " | Status: " + t.getStatus() +
                        " | Descricao: " + t.getDescricao());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- Atualizar Tarefa ---");
        System.out.print("ID da tarefa a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova descricao: ");
        String descricao = scanner.nextLine();
        System.out.print("Novo status (PENDENTE/EM_ANDAMENTO/CONCLUIDA): ");
        String status = scanner.nextLine();
        System.out.print("Novo ID do projeto: ");
        int projetoId = scanner.nextInt();
        scanner.nextLine();

        controller.atualizar(id, nome, descricao, status, projetoId);
    }

    private void excluir() {
        System.out.println("\n--- Excluir Tarefa ---");
        System.out.print("ID da tarefa a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.excluir(id);
    }
}