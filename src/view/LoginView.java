package view;

import controller.UsuarioController;
import model.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JLabel labelMensagem;
    private UsuarioController usuarioController = new UsuarioController();

    public LoginView() {
        // Configuração da janela
        setTitle("Sistema de Gestão de Projetos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza na tela
        setResizable(false);

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(new Color(45, 62, 80));

        // Título
        JLabel labelTitulo = new JLabel("SISTEMA DE GESTÃO DE PROJETOS");
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        labelTitulo.setBounds(50, 20, 320, 30);
        painel.add(labelTitulo);

        // Label Login
        JLabel labelLogin = new JLabel("Login:");
        labelLogin.setForeground(Color.WHITE);
        labelLogin.setFont(new Font("Arial", Font.PLAIN, 13));
        labelLogin.setBounds(60, 80, 80, 25);
        painel.add(labelLogin);

        // Campo Login
        campoLogin = new JTextField();
        campoLogin.setBounds(140, 80, 200, 25);
        painel.add(campoLogin);

        // Label Senha
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setForeground(Color.WHITE);
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 13));
        labelSenha.setBounds(60, 120, 80, 25);
        painel.add(labelSenha);

        // Campo Senha
        campoSenha = new JPasswordField();
        campoSenha.setBounds(140, 120, 200, 25);
        painel.add(campoSenha);

        // Botão Entrar
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(140, 170, 200, 35);
        botaoEntrar.setBackground(new Color(52, 152, 219));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        botaoEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(botaoEntrar);

        // Label Mensagem de erro
        labelMensagem = new JLabel("");
        labelMensagem.setForeground(new Color(231, 76, 60));
        labelMensagem.setFont(new Font("Arial", Font.PLAIN, 12));
        labelMensagem.setBounds(60, 220, 320, 25);
        painel.add(labelMensagem);

        add(painel);

        // Ação do botão Entrar
        botaoEntrar.addActionListener(e -> fazerLogin());

        // Permite pressionar Enter para logar
        campoSenha.addActionListener(e -> fazerLogin());
        campoLogin.addActionListener(e -> fazerLogin());
    }

    private void fazerLogin() {
        String login = campoLogin.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (login.isEmpty() || senha.isEmpty()) {
            labelMensagem.setText("Preencha login e senha!");
            return;
        }

        Usuario usuario = usuarioController.autenticar(login, senha);

        if (usuario != null) {
            dispose(); // fecha a tela de login
            new MenuPrincipalView(usuario).setVisible(true); // abre o menu
        } else {
            labelMensagem.setText("Login ou senha invalidos!");
            campoSenha.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
