package view;

import controller.EquipeController;
import model.Equipe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EquipeSwingView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private EquipeController controller = new EquipeController();

    public EquipeSwingView() {
        setTitle("Gerenciar Equipes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 62, 80));

        // Título
        JLabel titulo = new JLabel("  GERENCIAR EQUIPES", SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(36, 50, 64));
        titulo.setPreferredSize(new Dimension(700, 45));
        painel.add(titulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "Descrição"};
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

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(380);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);

        // Botões
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
        carregarTabela();

        btnCadastrar.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma equipe para editar!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Equipe eq = new Equipe();
            eq.setId((int) modeloTabela.getValueAt(linha, 0));
            eq.setNome((String) modeloTabela.getValueAt(linha, 1));
            eq.setDescricao((String) modeloTabela.getValueAt(linha, 2));
            abrirFormulario(eq);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma equipe para excluir!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nome = (String) modeloTabela.getValueAt(linha, 1);
            int confirmar = JOptionPane.showConfirmDialog(this,
                "Deseja excluir a equipe: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                controller.excluir(id);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Equipe excluída com sucesso!");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Equipe> equipes = controller.listar();
        for (Equipe eq : equipes) {
            modeloTabela.addRow(new Object[]{
                eq.getId(), eq.getNome(), eq.getDescricao()
            });
        }
    }

    private void abrirFormulario(Equipe equipe) {
        JDialog dialog = new JDialog(this,
            equipe == null ? "Cadastrar Equipe" : "Editar Equipe", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(new Color(45, 62, 80));

        JTextField fNome      = criarCampo(p, "Nome:",      30, 30);
        JTextField fDescricao = criarCampo(p, "Descrição:", 30, 90);

        // Preenche campos se for edição
        if (equipe != null) {
            fNome.setText(equipe.getNome());
            fDescricao.setText(equipe.getDescricao());
        }

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 160, 120, 35);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(btnSalvar);

        dialog.add(p);

        btnSalvar.addActionListener(e -> {
            String nome      = fNome.getText().trim();
            String descricao = fDescricao.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (equipe == null) {
                controller.cadastrar(nome, descricao);
                JOptionPane.showMessageDialog(dialog, "Equipe cadastrada com sucesso!");
            } else {
                controller.atualizar(equipe.getId(), nome, descricao);
                JOptionPane.showMessageDialog(dialog, "Equipe atualizada com sucesso!");
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
        campo.setBounds(x + 110, y, 220, 25);
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