package view;

import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.Window;

public class MenuPrincipalView extends JFrame {

    private Usuario usuarioLogado;

    public MenuPrincipalView(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Menu Principal - " + usuario.getNome());
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(new Color(45, 62, 80));

        // Boas vindas
        JLabel labelBemVindo = new JLabel("Bem-vindo(a), " + usuario.getNome() + "!");
        labelBemVindo.setForeground(Color.WHITE);
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 14));
        labelBemVindo.setBounds(30, 20, 440, 25);
        painel.add(labelBemVindo);

        // Perfil
        JLabel labelPerfil = new JLabel("Perfil: " + usuario.getPerfil());
        labelPerfil.setForeground(new Color(52, 152, 219));
        labelPerfil.setFont(new Font("Arial", Font.PLAIN, 12));
        labelPerfil.setBounds(30, 45, 440, 20);
        painel.add(labelPerfil);

        // Botão Usuários
        JButton btnUsuarios = criarBotao("Gerenciar Usuários", 30, 90);
        painel.add(btnUsuarios);

        // Botão Projetos
        JButton btnProjetos = criarBotao("Gerenciar Projetos", 30, 150);
        painel.add(btnProjetos);

        // Botão Tarefas
        JButton btnTarefas = criarBotao("Gerenciar Tarefas", 30, 210);
        painel.add(btnTarefas);
         
        // Botão Minhas Demandas
        JButton btnDemandas = criarBotao("Minhas Demandas", 30, 270);
        painel.add(btnDemandas);

        // Botão Equipes
        JButton btnEquipes = criarBotao("Gerenciar Equipes", 260, 90);
        painel.add(btnEquipes);

        // Botão Relatórios
        JButton btnRelatorios = criarBotao("Relatórios", 260, 150);
        painel.add(btnRelatorios);

        // Botão Sair
        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(180, 340, 140, 40);
        btnSair.setBackground(new Color(231, 76, 60));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Arial", Font.BOLD, 13));
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnSair);

        add(painel);

        // Ações dos botões com controle de acesso
       btnUsuarios.addActionListener(e -> {
    if (usuarioLogado.getPerfil().equals("ADMIN")) {
        new UsuarioSwingView().setVisible(true);
        } else {
        
        JOptionPane.showMessageDialog(this,
            "Acesso negado! Apenas ADMIN pode gerenciar usuários.",
            "Acesso Negado", JOptionPane.WARNING_MESSAGE);
    }
});

       btnDemandas.addActionListener(e -> {
    new MinhasDemandasView(usuarioLogado).setVisible(true);
});
       
        btnProjetos.addActionListener(e -> {
            if (usuarioLogado.getPerfil().equals("ADMIN") ||
                usuarioLogado.getPerfil().equals("GERENTE")) {
                new ProjetoSwingView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Acesso negado! Apenas ADMIN e GERENTE podem gerenciar projetos.",
                    "Acesso Negado", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnTarefas.addActionListener(e ->
            new TarefaSwingView().setVisible(true)
        );

        btnEquipes.addActionListener(e ->
            new EquipeSwingView().setVisible(true)
        );

        btnRelatorios.addActionListener(e -> {
            if (usuarioLogado.getPerfil().equals("ADMIN") ||
                usuarioLogado.getPerfil().equals("GERENTE")) {
                new RelatorioSwingView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Acesso negado! Apenas ADMIN e GERENTE podem ver relatórios.",
                    "Acesso Negado", JOptionPane.WARNING_MESSAGE);
            }
        });

       
        btnSair.addActionListener(e -> {
        int confirmar = JOptionPane.showConfirmDialog(this,
        "Deseja sair do sistema?", "Confirmar Saida",
        JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
        // Fecha todas as janelas abertas
        for (Window w : Window.getWindows()) {
            w.dispose();
        }
        new LoginView().setVisible(true);
    }
});
    }

    private JButton criarBotao(String texto, int x, int y) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, 200, 45);
        botao.setBackground(new Color(52, 152, 219));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
}
