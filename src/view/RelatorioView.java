package view;

import controller.RelatorioController;
import java.util.Scanner;

public class RelatorioView {

    private Scanner scanner = new Scanner(System.in);
    private RelatorioController controller = new RelatorioController();

    public void menu() {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n=== MENU RELATORIOS ===");
            System.out.println("1 - Tarefas por status");
            System.out.println("2 - Projetos atrasados");
            System.out.println("3 - Projetos em andamento");
            System.out.println("4 - Tarefas por projeto");
            System.out.println("5 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: controller.tarefasPorStatus(); break;
                case 2: controller.projetosAtrasados(); break;
                case 3: controller.projetosEmAndamento(); break;
                case 4: controller.tarefasPorProjeto(); break;
                case 5: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        }
    }
}