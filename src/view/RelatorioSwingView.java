package view;

import controller.RelatorioController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

public class RelatorioSwingView extends JFrame {

    private RelatorioController controller = new RelatorioController();
    private JPanel painelCentro;

    public RelatorioSwingView() {
        setTitle("Relatórios");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 62, 80));

        // Título
        JLabel titulo = new JLabel("  RELATÓRIOS", SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(36, 50, 64));
        titulo.setPreferredSize(new Dimension(800, 45));
        painel.add(titulo, BorderLayout.NORTH);

        // Painel de botões laterais
        JPanel painelBotoesLaterais = new JPanel();
        painelBotoesLaterais.setLayout(new BoxLayout(painelBotoesLaterais, BoxLayout.Y_AXIS));
        painelBotoesLaterais.setBackground(new Color(36, 50, 64));
        painelBotoesLaterais.setPreferredSize(new Dimension(200, 550));
        painelBotoesLaterais.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnTarefasStatus   = criarBotaoLateral("Tarefas por Status");
        JButton btnProjetosAtrasados = criarBotaoLateral("Projetos Atrasados");
        JButton btnProjetosAndamento = criarBotaoLateral("Projetos em Andamento");
        JButton btnTarefasProjeto  = criarBotaoLateral("Tarefas por Projeto");

        painelBotoesLaterais.add(btnTarefasStatus);
        painelBotoesLaterais.add(Box.createVerticalStrut(10));
        painelBotoesLaterais.add(btnProjetosAtrasados);
        painelBotoesLaterais.add(Box.createVerticalStrut(10));
        painelBotoesLaterais.add(btnProjetosAndamento);
        painelBotoesLaterais.add(Box.createVerticalStrut(10));
        painelBotoesLaterais.add(btnTarefasProjeto);
        painel.add(painelBotoesLaterais, BorderLayout.WEST);

        // Painel central onde o relatório aparece
        painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(new Color(45, 62, 80));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelInicial = new JLabel("Selecione um relatório ao lado", SwingConstants.CENTER);
        labelInicial.setForeground(new Color(127, 140, 141));
        labelInicial.setFont(new Font("Arial", Font.ITALIC, 14));
        painelCentro.add(labelInicial, BorderLayout.CENTER);
        painel.add(painelCentro, BorderLayout.CENTER);

        // Botão Voltar
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
painelRodape.setBackground(new Color(36, 50, 64));

JButton btnImprimir = new JButton("Imprimir");
btnImprimir.setPreferredSize(new Dimension(120, 35));
btnImprimir.setBackground(new Color(52, 152, 219));
btnImprimir.setForeground(Color.WHITE);
btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
btnImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));

JButton btnVoltar = new JButton("Voltar");
btnVoltar.setPreferredSize(new Dimension(120, 35));
btnVoltar.setBackground(new Color(52, 152, 219));
btnVoltar.setForeground(Color.WHITE);
btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));

painelRodape.add(btnImprimir);
painelRodape.add(btnVoltar);
painel.add(painelRodape, BorderLayout.SOUTH);

        add(painel);

        // Ações dos botões
        btnTarefasStatus.addActionListener(e ->
            exibirRelatorio("Tarefas por Status",
                new String[]{"Status", "Quantidade"},
                controller.getTarefasPorStatus())
        );

        btnProjetosAtrasados.addActionListener(e ->
            exibirRelatorio("Projetos Atrasados",
                new String[]{"ID", "Nome", "Fim Previsto", "Status"},
                controller.getProjetosAtrasados())
        );

        btnProjetosAndamento.addActionListener(e ->
            exibirRelatorio("Projetos em Andamento",
                new String[]{"ID", "Nome", "Início", "Fim Previsto"},
                controller.getProjetosEmAndamento())
        );

        btnTarefasProjeto.addActionListener(e ->
            exibirRelatorio("Tarefas por Projeto",
                new String[]{"Projeto", "Tarefa", "Status"},
                controller.getTarefasPorProjeto())
        );

        btnVoltar.addActionListener(e -> dispose());
        btnImprimir.addActionListener(e -> {
    // Verifica se há algum relatório exibido
    if (painelCentro.getComponentCount() <= 1) {
        JOptionPane.showMessageDialog(this,
            "Selecione um relatório antes de imprimir!",
            "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Procura a tabela no painel central
    JTable tabelaParaImprimir = null;
    for (java.awt.Component comp : painelCentro.getComponents()) {
        if (comp instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) comp;
            if (scroll.getViewport().getView() instanceof JTable) {
                tabelaParaImprimir = (JTable) scroll.getViewport().getView();
            }
        }
    }

    if (tabelaParaImprimir == null) {
        JOptionPane.showMessageDialog(this,
            "Nenhum relatório para imprimir!",
            "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
       boolean imprimiu = tabelaParaImprimir.print(
    JTable.PrintMode.FIT_WIDTH,
    new java.text.MessageFormat("SISTEMA DE GESTAO DE PROJETOS"),
    new java.text.MessageFormat("Pagina {0}")
        );
        if (imprimiu) {
            JOptionPane.showMessageDialog(this,
                "Relatório enviado para impressão com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (PrinterException ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao imprimir: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
});
    }

    private void exibirRelatorio(String titulo, String[] colunas, Object[][] dados) {
        painelCentro.removeAll();

        JLabel labelTitulo = new JLabel("  " + titulo, SwingConstants.LEFT);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        labelTitulo.setOpaque(true);
        labelTitulo.setBackground(new Color(52, 152, 219));
        labelTitulo.setPreferredSize(new Dimension(580, 35));
        painelCentro.add(labelTitulo, BorderLayout.NORTH);

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabela = new JTable(modelo);
        tabela.setBackground(new Color(236, 240, 241));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setBackground(new Color(52, 152, 219));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scroll = new JScrollPane(tabela);
        painelCentro.add(scroll, BorderLayout.CENTER);

        painelCentro.revalidate();
        painelCentro.repaint();
    }

    private JButton criarBotaoLateral(String texto) {
        JButton botao = new JButton("<html><center>" + texto + "</center></html>");
        botao.setMaximumSize(new Dimension(180, 60));
        botao.setPreferredSize(new Dimension(180, 60));
        botao.setBackground(new Color(52, 152, 219));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 11));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }
}