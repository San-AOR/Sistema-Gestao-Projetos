package view;

import controller.ProjetoController;
import controller.UsuarioController;
import model.Projeto;
import model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjetoSwingView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ProjetoController controller = new ProjetoController();
    private UsuarioController usuarioController = new UsuarioController();

    // Formatter para exibição DD-MM-AAAA
    private static final DateTimeFormatter FMT_TELA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ProjetoSwingView() {
        setTitle("Gerenciar Projetos");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 62, 80));

        // Título
        JLabel titulo = new JLabel("  GERENCIAR PROJETOS", SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(36, 50, 64));
        titulo.setPreferredSize(new Dimension(900, 45));
        painel.add(titulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"ID", "Nome", "Descrição", "Início", "Fim Previsto", "Status", "ID Gerente"};
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
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(80);

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
                JOptionPane.showMessageDialog(this, "Selecione um projeto para editar!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Projeto p = new Projeto();
            p.setId((int) modeloTabela.getValueAt(linha, 0));
            p.setNome((String) modeloTabela.getValueAt(linha, 1));
            p.setDescricao((String) modeloTabela.getValueAt(linha, 2));
            // Tabela exibe DD-MM-AAAA, converte para LocalDate
            p.setDataInicio(parseDDMMAAAA((String) modeloTabela.getValueAt(linha, 3)));
            p.setDataFimPrevista(parseDDMMAAAA((String) modeloTabela.getValueAt(linha, 4)));
            p.setStatus((String) modeloTabela.getValueAt(linha, 5));
            p.setGerenteId((int) modeloTabela.getValueAt(linha, 6));
            abrirFormulario(p);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um projeto para excluir!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nome = (String) modeloTabela.getValueAt(linha, 1);
            int confirmar = JOptionPane.showConfirmDialog(this,
                "Deseja excluir o projeto: " + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                controller.excluir(id);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Projeto excluído com sucesso!");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Projeto> projetos = controller.listar();
        for (Projeto p : projetos) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getDescricao(),
                // Exibe no formato DD-MM-AAAA
                p.getDataInicio().format(FMT_TELA),
                p.getDataFimPrevista().format(FMT_TELA),
                p.getStatus(), p.getGerenteId()
            });
        }
    }

    private void abrirFormulario(Projeto projeto) {
        JDialog dialog = new JDialog(this,
            projeto == null ? "Cadastrar Projeto" : "Editar Projeto", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(new Color(45, 62, 80));

        JTextField fNome      = criarCampo(p, "Nome:",      30, 20);
        JTextField fDescricao = criarCampo(p, "Descrição:", 30, 70);

        // Label + campo Início (DD-MM-AAAA)
        JLabel lInicio = new JLabel("Inicio (DD-MM-AAAA):");
        lInicio.setForeground(Color.WHITE);
        lInicio.setFont(new Font("Arial", Font.PLAIN, 11));
        lInicio.setBounds(30, 120, 145, 25);
        p.add(lInicio);
        JTextField fInicio = new JTextField();
        fInicio.setBounds(180, 120, 220, 25);
        p.add(fInicio);
        aplicarMascaraData(fInicio);

        // Label + campo Fim Previsto (DD-MM-AAAA)
        JLabel lFim = new JLabel("Fim Previsto (DD-MM-AAAA):");
        lFim.setForeground(Color.WHITE);
        lFim.setFont(new Font("Arial", Font.PLAIN, 11));
        lFim.setBounds(30, 170, 175, 25);
        p.add(lFim);
        JTextField fFim = new JTextField();
        fFim.setBounds(210, 170, 190, 25);
        p.add(fFim);
        aplicarMascaraData(fFim);

        // Combo Status
        JLabel lStatus = new JLabel("Status:");
        lStatus.setForeground(Color.WHITE);
        lStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lStatus.setBounds(30, 220, 140, 25);
        p.add(lStatus);
        JComboBox<String> comboStatus = new JComboBox<>(
            new String[]{"PLANEJADO", "EM_ANDAMENTO", "CONCLUIDO"});
        comboStatus.setBounds(180, 220, 220, 25);
        p.add(comboStatus);

        // Combo Gerente
        JLabel lGerente = new JLabel("Gerente:");
        lGerente.setForeground(Color.WHITE);
        lGerente.setFont(new Font("Arial", Font.PLAIN, 12));
        lGerente.setBounds(30, 265, 140, 25);
        p.add(lGerente);

        JComboBox<String> comboGerente = new JComboBox<>();
        List<Usuario> usuarios = usuarioController.listar();
        for (Usuario u : usuarios) {
            if (u.getPerfil().equals("ADMIN") || u.getPerfil().equals("GERENTE")) {
                comboGerente.addItem(u.getId() + " - " + u.getNome());
            }
        }
        comboGerente.setBounds(180, 265, 220, 25);
        p.add(comboGerente);

        // Preenche campos se for edição (exibe DD-MM-AAAA)
        if (projeto != null) {
            fNome.setText(projeto.getNome());
            fDescricao.setText(projeto.getDescricao());
            fInicio.setText(projeto.getDataInicio().format(FMT_TELA));
            fFim.setText(projeto.getDataFimPrevista().format(FMT_TELA));
            comboStatus.setSelectedItem(projeto.getStatus());
            for (int i = 0; i < comboGerente.getItemCount(); i++) {
                if (comboGerente.getItemAt(i).startsWith(projeto.getGerenteId() + " ")) {
                    comboGerente.setSelectedIndex(i);
                    break;
                }
            }
        }

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(160, 320, 120, 35);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(btnSalvar);

        dialog.add(p);

        btnSalvar.addActionListener(e -> {
            try {
                String nome       = fNome.getText().trim();
                String descricao  = fDescricao.getText().trim();
                // Converte DD-MM-AAAA para LocalDate
                LocalDate inicio  = parseDDMMAAAA(fInicio.getText().trim());
                LocalDate fim     = parseDDMMAAAA(fFim.getText().trim());
                String status     = (String) comboStatus.getSelectedItem();
                String gerenteStr = (String) comboGerente.getSelectedItem();
                int gerenteId     = Integer.parseInt(gerenteStr.split(" - ")[0]);

                if (projeto == null) {
                    controller.cadastrar(nome, descricao, inicio, fim, status, gerenteId);
                    JOptionPane.showMessageDialog(dialog, "Projeto cadastrado com sucesso!");
                } else {
                    controller.atualizar(projeto.getId(), nome, descricao, inicio, fim, status, gerenteId);
                    JOptionPane.showMessageDialog(dialog, "Projeto atualizado com sucesso!");
                }
                dialog.dispose();
                carregarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Erro: verifique os dados!\nDatas devem estar no formato DD-MM-AAAA",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    // ---------------------------------------------------------------
    // Aplica máscara automática DD-MM-AAAA: digita 12052026 → 12-05-2026
    // ---------------------------------------------------------------
    private void aplicarMascaraData(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Permite teclas de navegação/delete sem reformatar
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE
                        || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                    return;
                }
                String texto = campo.getText().replaceAll("[^0-9]", "");
                if (texto.length() > 8) texto = texto.substring(0, 8);
                StringBuilder sb = new StringBuilder(texto);
                if (sb.length() > 2) sb.insert(2, "-");
                if (sb.length() > 5) sb.insert(5, "-");
                campo.setText(sb.toString());
                // Posiciona cursor no final
                campo.setCaretPosition(campo.getText().length());
            }
        });
    }

    // Converte String DD-MM-AAAA para LocalDate
    private LocalDate parseDDMMAAAA(String texto) {
        return LocalDate.parse(texto.trim(), FMT_TELA);
    }

    private JTextField criarCampo(JPanel p, String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setBounds(x, y, 140, 25);
        p.add(lbl);
        JTextField campo = new JTextField();
        campo.setBounds(x + 150, y, 220, 25);
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
