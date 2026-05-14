package view;

import controller.ProjetoController;
import model.Projeto;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProjetoView {

    private Scanner scanner = new Scanner(System.in);
    private ProjetoController controller = new ProjetoController();

    public void menu() {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n=== MENU PROJETOS ===");
            System.out.println("1 - Cadastrar projeto");
            System.out.println("2 - Listar projetos");
            System.out.println("3 - Atualizar projeto");
            System.out.println("4 - Excluir projeto");
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
        System.out.println("\n--- Cadastrar Projeto ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();
        System.out.print("Data de inicio (AAAA-MM-DD): ");
        LocalDate dataInicio = LocalDate.parse(scanner.nextLine());
        System.out.print("Data de fim prevista (AAAA-MM-DD): ");
        LocalDate dataFimPrevista = LocalDate.parse(scanner.nextLine());
        System.out.print("Status (PLANEJADO/EM_ANDAMENTO/CONCLUIDO): ");
        String status = scanner.nextLine();
        System.out.print("ID do gerente: ");
        int gerenteId = scanner.nextInt();
        scanner.nextLine();

        controller.cadastrar(nome, descricao, dataInicio, dataFimPrevista, status, gerenteId);
    }

    private void listar() {
        System.out.println("\n--- Lista de Projetos ---");
        List<Projeto> lista = controller.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
        } else {
            for (Projeto p : lista) {
                System.out.println("ID: " + p.getId() +
                        " | Nome: " + p.getNome() +
                        " | Status: " + p.getStatus() +
                        " | Inicio: " + p.getDataInicio() +
                        " | Fim previsto: " + p.getDataFimPrevista() +
                        " | Gerente ID: " + p.getGerenteId());
            }
        }
    }

    private void atualizar() {
        System.out.println("\n--- Atualizar Projeto ---");
        System.out.print("ID do projeto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova descricao: ");
        String descricao = scanner.nextLine();
        System.out.print("Nova data de inicio (AAAA-MM-DD): ");
        LocalDate dataInicio = LocalDate.parse(scanner.nextLine());
        System.out.print("Nova data de fim prevista (AAAA-MM-DD): ");
        LocalDate dataFimPrevista = LocalDate.parse(scanner.nextLine());
        System.out.print("Novo status (PLANEJADO/EM_ANDAMENTO/CONCLUIDO): ");
        String status = scanner.nextLine();
        System.out.print("Novo ID do gerente: ");
        int gerenteId = scanner.nextInt();
        scanner.nextLine();

        controller.atualizar(id, nome, descricao, dataInicio, dataFimPrevista, status, gerenteId);
    }

    private void excluir() {
        System.out.println("\n--- Excluir Projeto ---");
        System.out.print("ID do projeto a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.excluir(id);
    }
}