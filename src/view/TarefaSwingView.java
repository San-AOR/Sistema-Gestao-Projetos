package view;

import controller.TarefaController;
import controller.ProjetoController;
import controller.UsuarioController;
import model.Tarefa;
import model.Projeto;
import model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TarefaSwingView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TarefaController controller = new TarefaController();
    private ProjetoController projetoController = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();
    private List<Projeto> listaProjetos;

    public TarefaSwingView() {
        setTitle("Gerenciar Tarefas");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 62, 80));

        // Título
        JLabel titulo = new JLabel("  GERENCIAR TAREFAS", SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(36, 50, 64));
        titulo.setPreferredSize(new Dimension(900, 45));
        painel.add(titulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "Descrição", "Status", "ID Projeto", "Responsável"};
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

        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tabela);
        painel.add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(36, 50, 64));

        JButton btnCadastrar = criarBotao("Cadastrar", new Color(52, 152, 219));
        JButton btnEditar    = criarBotao("Editar", new Color(52, 152, 219));
        JButton btnAtribuir  = criarBotao("Atribuir", new Color(52, 152, 219));
        JButton btnExcluir   = criarBotao("Excluir", new Color(231, 76, 60));
        JButton btnVoltar    = criarBotao("Voltar", new Color(52, 152, 219));

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnAtribuir);
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
                    "Selecione uma tarefa para editar!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Tarefa t = new Tarefa();
            t.setId((int) modeloTabela.getValueAt(linha, 0));
            t.setNome((String) modeloTabela.getValueAt(linha, 1));
            t.setDescricao((String) modeloTabela.getValueAt(linha, 2));
            t.setStatus((String) modeloTabela.getValueAt(linha, 3));
            t.setProjetoId((int) modeloTabela.getValueAt(linha, 4));
            abrirFormulario(t);
        });

        btnAtribuir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma tarefa para atribuir!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int tarefaId = (int) modeloTabela.getValueAt(linha, 0);
            String nomeTarefa = (String) modeloTabela.getValueAt(linha, 1);
            abrirDialogAtribuir(tarefaId, nomeTarefa);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma tarefa para excluir!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nome = (String) modeloTabela.getValueAt(linha, 1);
            int confirmar = JOptionPane.showConfirmDialog(this,
                "Deseja excluir a tarefa: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                controller.excluir(id);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void abrirDialogAtribuir(int tarefaId, String nomeTarefa) {
        JDialog dialog = new JDialog(this, "Atribuir Responsável", true);
        dialog.setSize(400, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(new Color(45, 62, 80));

        JLabel lTarefa = new JLabel("Tarefa: " + nomeTarefa);
        lTarefa.setForeground(Color.WHITE);
        lTarefa.setFont(new Font("Arial", Font.BOLD, 13));
        lTarefa.setBounds(30, 20, 340, 25);
        p.add(lTarefa);

        JLabel lResponsavel = new JLabel("Responsável:");
        lResponsavel.setForeground(Color.WHITE);
        lResponsavel.setFont(new Font("Arial", Font.PLAIN, 12));
        lResponsavel.setBounds(30, 70, 120, 25);
        p.add(lResponsavel);

        JComboBox<String> comboUsuario = new JComboBox<>();
        comboUsuario.addItem("-- Sem responsável --");
        List<Usuario> usuarios = usuarioController.listar();
        for (Usuario u : usuarios) {
            comboUsuario.addItem(u.getId() + " - " + u.getNome() +
                " (" + u.getPerfil() + ")");
        }
        comboUsuario.setBounds(160, 70, 200, 25);
        p.add(comboUsuario);

        JButton btnSalvar = new JButton("Atribuir");
        btnSalvar.setBounds(140, 130, 120, 35);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(btnSalvar);

        dialog.add(p);

        btnSalvar.addActionListener(e -> {
            String selecionado = (String) comboUsuario.getSelectedItem();
            if (selecionado.equals("-- Sem responsável --")) {
                JOptionPane.showMessageDialog(dialog,
                    "Selecione um responsável!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            int responsavelId = Integer.parseInt(selecionado.split(" - ")[0]);
            controller.atribuirResponsavel(tarefaId, responsavelId);
            JOptionPane.showMessageDialog(dialog, "Responsável atribuído com sucesso!");
            dialog.dispose();
            carregarTabela();
        });

        dialog.setVisible(true);
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = usuarioController.listar();
        List<Tarefa> tarefas = controller.listar();
        for (Tarefa t : tarefas) {
            String nomeResponsavel = "Sem responsável";
            for (Usuario u : usuarios) {
                if (u.getId() == t.getResponsavelId()) {
                    nomeResponsavel = u.getNome();
                    break;
                }
            }
            modeloTabela.addRow(new Object[]{
                t.getId(), t.getNome(), t.getDescricao(),
                t.getStatus(), t.getProjetoId(), nomeResponsavel
            });
        }
    }

    private void abrirFormulario(Tarefa tarefa) {
        JDialog dialog = new JDialog(this,
            tarefa == null ? "Cadastrar Tarefa" : "Editar Tarefa", true);
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(new Color(45, 62, 80));

        JTextField fNome      = criarCampo(p, "Nome:",      30, 20);
        JTextField fDescricao = criarCampo(p, "Descrição:", 30, 70);

        JLabel lStatus = new JLabel("Status:");
        lStatus.setForeground(Color.WHITE);
        lStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lStatus.setBounds(30, 120, 120, 25);
        p.add(lStatus);
        JComboBox<String> comboStatus = new JComboBox<>(
            new String[]{"PENDENTE", "EM_ANDAMENTO", "CONCLUIDA"});
        comboStatus.setBounds(160, 120, 220, 25);
        p.add(comboStatus);

        JLabel lProjeto = new JLabel("Projeto:");
        lProjeto.setForeground(Color.WHITE);
        lProjeto.setFont(new Font("Arial", Font.PLAIN, 12));
        lProjeto.setBounds(30, 165, 120, 25);
        p.add(lProjeto);

        JComboBox<String> comboProjeto = new JComboBox<>();
        listaProjetos = projetoController.listar();
        for (Projeto proj : listaProjetos) {
            comboProjeto.addItem(proj.getId() + " - " + proj.getNome());
        }
        comboProjeto.addActionListener(e -> {
            int indice = comboProjeto.getSelectedIndex();
            if (indice >= 0) {
                Projeto projetoSelecionado = listaProjetos.get(indice);
                fNome.setText(projetoSelecionado.getNome());
                fDescricao.setText(projetoSelecionado.getDescricao());
            }
        });
        comboProjeto.setBounds(160, 165, 220, 25);
        p.add(comboProjeto);

        if (tarefa != null) {
            fNome.setText(tarefa.getNome());
            fDescricao.setText(tarefa.getDescricao());
            comboStatus.setSelectedItem(tarefa.getStatus());
            for (int i = 0; i < comboProjeto.getItemCount(); i++) {
                if (comboProjeto.getItemAt(i).startsWith(tarefa.getProjetoId() + " ")) {
                    comboProjeto.setSelectedIndex(i);
                    break;
                }
            }
        }

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(150, 230, 120, 35);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(btnSalvar);

        dialog.add(p);

        btnSalvar.addActionListener(e -> {
            String nome       = fNome.getText().trim();
            String descricao  = fDescricao.getText().trim();
            String status     = (String) comboStatus.getSelectedItem();
            String projetoStr = (String) comboProjeto.getSelectedItem();
            int projetoId     = Integer.parseInt(projetoStr.split(" - ")[0]);

            if (tarefa == null) {
                controller.cadastrar(nome, descricao, status, projetoId);
                JOptionPane.showMessageDialog(dialog, "Tarefa cadastrada com sucesso!");
            } else {
                controller.atualizar(tarefa.getId(), nome, descricao, status, projetoId);
                JOptionPane.showMessageDialog(dialog, "Tarefa atualizada com sucesso!");
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
        lbl.setBounds(x, y, 120, 25);
        p.add(lbl);
        JTextField campo = new JTextField();
        campo.setBounds(x + 130, y, 220, 25);
        p.add(campo);
        return campo;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(110, 35));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
}
