package view;

import controller.UsuarioController;
import model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioSwingView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private UsuarioController controller = new UsuarioController();

    public UsuarioSwingView() {
        setTitle("Gerenciar Usuários");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 62, 80));

        // Título
        JLabel titulo = new JLabel("  GERENCIAR USUÁRIOS", SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(36, 50, 64));
        titulo.setPreferredSize(new Dimension(800, 45));
        painel.add(titulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setBackground(new Color(236, 240, 241));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setBackground(new Color(52, 152, 219));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Largura das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(160);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(90);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(new Color(36, 50, 64));

       JButton btnCadastrar = criarBotao("Cadastrar", new Color(52, 152, 219));
       JButton btnEditar    = criarBotao("Editar", new Color(52, 152, 219));
       JButton btnExcluir   = criarBotao("Excluir", new Color(231, 76, 60));
       JButton btnVoltar    = criarBotao("Voltar", new Color(52, 152, 219));

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnVoltar);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);

        // Carrega os dados na tabela
        carregarTabela();

        // Ações dos botões
        btnCadastrar.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um usuário para editar!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            String nome  = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
            String cpf   = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
            String email = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
            String cargo = (String) modeloTabela.getValueAt(linhaSelecionada, 4);
            String login = (String) modeloTabela.getValueAt(linhaSelecionada, 5);
            String perfil = (String) modeloTabela.getValueAt(linhaSelecionada, 6);
            Usuario u = new Usuario();
            u.setId(id); u.setNome(nome); u.setCpf(cpf); u.setEmail(email);
            u.setCargo(cargo); u.setLogin(login); u.setPerfil(perfil);
            abrirFormulario(u);
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um usuário para excluir!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
            int confirmar = JOptionPane.showConfirmDialog(this,
                "Deseja excluir o usuário: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
                controller.excluir(id);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = controller.listar();
        for (Usuario u : usuarios) {
            modeloTabela.addRow(new Object[]{
                u.getId(), u.getNome(), u.getCpf(),
                u.getEmail(), u.getCargo(), u.getLogin(), u.getPerfil()
            });
        }
    }

    private void abrirFormulario(Usuario usuario) {
        JDialog dialog = new JDialog(this, usuario == null ? "Cadastrar Usuário" : "Editar Usuário", true);
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(new Color(45, 62, 80));

        // Campos do formulário
        JTextField fNome  = criarCampo(p, "Nome:",   30, 20);
        JTextField fCpf   = criarCampo(p, "CPF:",    30, 70);
        JTextField fEmail = criarCampo(p, "Email:",  30, 120);
        JTextField fCargo = criarCampo(p, "Cargo:",  30, 170);
        JTextField fLogin = criarCampo(p, "Login:",  30, 220);
        JPasswordField fSenha = new JPasswordField();
        fSenha.setBounds(150, 270, 220, 25);
        p.add(fSenha);
        JLabel lSenha = new JLabel("Senha:");
        lSenha.setForeground(Color.WHITE);
        lSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        lSenha.setBounds(30, 270, 100, 25);
        p.add(lSenha);

        // Combo perfil
        JLabel lPerfil = new JLabel("Perfil:");
        lPerfil.setForeground(Color.WHITE);
        lPerfil.setFont(new Font("Arial", Font.PLAIN, 12));
        lPerfil.setBounds(30, 310, 100, 25);
        p.add(lPerfil);
        JComboBox<String> comboPerfil = new JComboBox<>(new String[]{"ADMIN", "GERENTE", "COLABORADOR"});
        comboPerfil.setBounds(150, 310, 220, 25);
        p.add(comboPerfil);

        // Preenche os campos se for edição
        if (usuario != null) {
            fNome.setText(usuario.getNome());
            fCpf.setText(usuario.getCpf());
            fEmail.setText(usuario.getEmail());
            fCargo.setText(usuario.getCargo());
            fLogin.setText(usuario.getLogin());
            comboPerfil.setSelectedItem(usuario.getPerfil());
        }

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(150, 355, 120, 35);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(btnSalvar);

        dialog.add(p);

        btnSalvar.addActionListener(e -> {
            String nome   = fNome.getText().trim();
            String cpf    = fCpf.getText().trim();
            String email  = fEmail.getText().trim();
            String cargo  = fCargo.getText().trim();
            String login  = fLogin.getText().trim();
            String senha  = new String(fSenha.getPassword()).trim();
            String perfil = (String) comboPerfil.getSelectedItem();

            if (usuario == null) {
                controller.cadastrar(nome, cpf, email, cargo, login, senha, perfil);
                JOptionPane.showMessageDialog(dialog, "Usuário cadastrado com sucesso!");
            } else {
                controller.atualizar(usuario.getId(), nome, cpf, email, cargo, login, senha, perfil);
                JOptionPane.showMessageDialog(dialog, "Usuário atualizado com sucesso!");
            }
            dialog.dispose();
            carregarTabela();
        });

        dialog.setVisible(true);
    }

    private JTextField criarCampo(JPanel p, String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setBounds(x, y, 100, 25);
        p.add(lbl);
        JTextField campo = new JTextField();
        campo.setBounds(x + 120, y, 220, 25);
        p.add(campo);
        return campo;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(120, 35));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
}