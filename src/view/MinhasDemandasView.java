package view;

import controller.TarefaController;
import model.Tarefa;
import model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import dao.TarefaDAO;

public class MinhasDemandasView extends JFrame {

    private JTable tabelaDemandas;
    private DefaultTableModel modeloTabela;
    private TarefaController controller = new TarefaController();
    private TarefaDAO arquivoDAO = new TarefaDAO();
    private Usuario usuarioLogado;

    public MinhasDemandasView(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Minhas Demandas — " + usuario.getNome());
        setSize(980, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(45, 62, 80));

        JLabel titulo = new JLabel("  MINHAS DEMANDAS — " + usuario.getNome().toUpperCase(), SwingConstants.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(36, 50, 64));
        titulo.setPreferredSize(new Dimension(980, 45));
        painel.add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Titulo", "Descricao", "Status", "ID Projeto", "Resolucao",
                            "Data Inicio", "Data Atribuicao", "Ultima Atualizacao", "Data Fim"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabelaDemandas = new JTable(modeloTabela);
        tabelaDemandas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaDemandas.setBackground(new Color(236, 240, 241));
        tabelaDemandas.setRowHeight(25);
        tabelaDemandas.getTableHeader().setBackground(new Color(52, 152, 219));
        tabelaDemandas.getTableHeader().setForeground(Color.WHITE);
        tabelaDemandas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        tabelaDemandas.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaDemandas.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelaDemandas.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaDemandas.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabelaDemandas.getColumnModel().getColumn(4).setPreferredWidth(60);
        tabelaDemandas.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabelaDemandas.getColumnModel().getColumn(6).setPreferredWidth(110);
        tabelaDemandas.getColumnModel().getColumn(7).setPreferredWidth(110);
        tabelaDemandas.getColumnModel().getColumn(8).setPreferredWidth(110);
        tabelaDemandas.getColumnModel().getColumn(9).setPreferredWidth(110);

        JScrollPane scroll = new JScrollPane(tabelaDemandas);
        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(new Color(36, 50, 64));

        JButton btnDetalhar = criarBotao("Detalhar Demanda", new Color(52, 152, 219));
        JButton btnConcluir = criarBotao("Concluir", new Color(52, 152, 219));
        JButton btnVoltar   = criarBotao("Voltar", new Color(52, 152, 219));

        painelBotoes.add(btnDetalhar);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnVoltar);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);
        carregarTabela();

        btnDetalhar.addActionListener(e -> {
            int linha = tabelaDemandas.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma demanda!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloTabela.getValueAt(linha, 0);
            for (Tarefa t : controller.listar()) {
                if (t.getId() == id) { abrirDialogDetalhe(t); break; }
            }
        });

        btnConcluir.addActionListener(e -> {
            int linha = tabelaDemandas.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma demanda!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nome = (String) modeloTabela.getValueAt(linha, 1);
            int confirmar = JOptionPane.showConfirmDialog(this,
                "Deseja marcar como CONCLUIDA: " + nome + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                for (Tarefa t : controller.listar()) {
                    if (t.getId() == id) {
                        controller.atualizarDetalhes(id,
                            t.getDetalhes() != null ? t.getDetalhes() : "",
                            t.getObservacao() != null ? t.getObservacao() : "",
                            t.getDocumento() != null ? t.getDocumento() : "",
                            "CONCLUIDA");
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Demanda concluida com sucesso!");
                carregarTabela();
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void abrirDialogDetalhe(Tarefa tarefa) {
        JDialog dialog = new JDialog(this, "Detalhar Demanda #" + tarefa.getId(), true);
        dialog.setSize(720, 680);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(new Color(45, 62, 80));

        // Titulo
        JLabel lTitulo = new JLabel("ID: " + tarefa.getId() + "  |  Titulo: " + tarefa.getNome());
        lTitulo.setForeground(Color.WHITE);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lTitulo.setBounds(15, 10, 680, 25);
        p.add(lTitulo);

        // Datas — exibidas em DD-MM-AAAA HH:mm (mantém hora se houver, formata só a parte de data)
        JPanel painelDatas = new JPanel(new GridLayout(2, 2, 10, 5));
        painelDatas.setBackground(new Color(36, 50, 64));
        painelDatas.setBounds(15, 40, 680, 55);
        addLabel(painelDatas, "Inicio: "              + fmtExibicao(tarefa.getDataInicio()));
        addLabel(painelDatas, "Atribuicao: "          + fmtExibicao(tarefa.getDataAtribuicao()));
        addLabel(painelDatas, "Ultima Atualizacao: "  + fmtExibicao(tarefa.getDataUltimaAtualizacao()));
        addLabel(painelDatas, "Data Fim: "            + fmtExibicao(tarefa.getDataFim()));
        p.add(painelDatas);

        // Status
        JLabel lStatus = new JLabel("Status atual: " + tarefa.getStatus());
        lStatus.setForeground(new Color(46, 204, 113));
        lStatus.setFont(new Font("Arial", Font.BOLD, 12));
        lStatus.setBounds(15, 105, 200, 20);
        p.add(lStatus);

        JLabel lAlterar = new JLabel("Alterar Status:");
        lAlterar.setForeground(Color.WHITE);
        lAlterar.setFont(new Font("Arial", Font.PLAIN, 12));
        lAlterar.setBounds(220, 102, 100, 25);
        p.add(lAlterar);

        JComboBox<String> comboStatus = new JComboBox<>(new String[]{"PENDENTE", "EM_ANDAMENTO", "CONCLUIDA"});
        comboStatus.setSelectedItem(tarefa.getStatus());
        comboStatus.setBounds(330, 102, 160, 25);
        p.add(comboStatus);

        // Descricao
        JLabel lDesc = new JLabel("Descricao:");
        lDesc.setForeground(new Color(189, 195, 199));
        lDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lDesc.setBounds(15, 135, 100, 20);
        p.add(lDesc);

        JTextArea areaDesc = new JTextArea(tarefa.getDescricao() != null ? tarefa.getDescricao() : "");
        areaDesc.setEditable(false);
        areaDesc.setBackground(new Color(52, 73, 94));
        areaDesc.setForeground(Color.WHITE);
        areaDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDesc.setLineWrap(true);
        areaDesc.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(areaDesc);
        scrollDesc.setBounds(15, 158, 330, 65);
        p.add(scrollDesc);

        // Observacao
        JLabel lObs = new JLabel("Observacao Interna (opcional):");
        lObs.setForeground(Color.WHITE);
        lObs.setFont(new Font("Arial", Font.PLAIN, 12));
        lObs.setBounds(360, 135, 230, 20);
        p.add(lObs);

        JTextArea areaObs = new JTextArea(tarefa.getObservacao() != null ? tarefa.getObservacao() : "");
        areaObs.setBackground(Color.WHITE);
        areaObs.setForeground(new Color(30, 30, 30));
        areaObs.setFont(new Font("Arial", Font.PLAIN, 12));
        areaObs.setLineWrap(true);
        areaObs.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(areaObs);
        scrollObs.setBounds(360, 158, 340, 65);
        p.add(scrollObs);

        // Resolucao
        JLabel lRes = new JLabel("Resolucao:");
        lRes.setForeground(Color.WHITE);
        lRes.setFont(new Font("Arial", Font.BOLD, 12));
        lRes.setBounds(15, 233, 150, 20);
        p.add(lRes);

        JTextArea areaRes = new JTextArea(tarefa.getDetalhes() != null ? tarefa.getDetalhes() : "");
        areaRes.setBackground(Color.WHITE);
        areaRes.setForeground(new Color(30, 30, 30));
        areaRes.setFont(new Font("Arial", Font.PLAIN, 12));
        areaRes.setLineWrap(true);
        areaRes.setWrapStyleWord(true);
        JScrollPane scrollRes = new JScrollPane(areaRes);
        scrollRes.setBounds(15, 255, 680, 100);
        p.add(scrollRes);

        // Arquivos — titulo
        JLabel lArqs = new JLabel("Arquivos Anexados:");
        lArqs.setForeground(Color.WHITE);
        lArqs.setFont(new Font("Arial", Font.BOLD, 12));
        lArqs.setBounds(15, 365, 200, 20);
        p.add(lArqs);

        // Tabela de arquivos
        String[] colsArq = {"Nome do Arquivo", "Data de Envio", "Caminho"};
        DefaultTableModel modeloArq = new DefaultTableModel(colsArq, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelaArq = new JTable(modeloArq);
        tabelaArq.setRowHeight(22);
        tabelaArq.getTableHeader().setBackground(new Color(52, 152, 219));
        tabelaArq.getTableHeader().setForeground(Color.WHITE);
        tabelaArq.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tabelaArq.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabelaArq.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelaArq.getColumnModel().getColumn(2).setPreferredWidth(350);

        JScrollPane scrollArq = new JScrollPane(tabelaArq);
        scrollArq.setBounds(15, 388, 680, 100);
        p.add(scrollArq);

        // Carrega arquivos existentes
        List<String[]> arquivos = arquivoDAO.listarPorTarefa(tarefa.getId());
        for (String[] arq : arquivos) {
            // arq[2] é a data de envio — exibe em DD-MM-AAAA HH:mm
            modeloArq.addRow(new Object[]{arq[0], fmtExibicao(arq[2]), arq[1]});
        }

        // Botoes de arquivo
        JButton btnIncluir = new JButton("Incluir Arquivo");
        btnIncluir.setBounds(15, 498, 150, 30);
        btnIncluir.setBackground(new Color(52, 152, 219));
        btnIncluir.setForeground(Color.WHITE);
        btnIncluir.setFont(new Font("Arial", Font.BOLD, 11));
        p.add(btnIncluir);

        JButton btnVisualizar = new JButton("Visualizar Arquivos");
        btnVisualizar.setBounds(175, 498, 160, 30);
        btnVisualizar.setBackground(new Color(52, 152, 219));
        btnVisualizar.setForeground(Color.WHITE);
        btnVisualizar.setFont(new Font("Arial", Font.BOLD, 11));
        p.add(btnVisualizar);

        btnIncluir.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecione um arquivo");
            if (chooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File arquivo = chooser.getSelectedFile();
                arquivoDAO.salvar(tarefa.getId(), arquivo.getName(), arquivo.getAbsolutePath());
                modeloArq.setRowCount(0);
                for (String[] arq : arquivoDAO.listarPorTarefa(tarefa.getId())) {
                    modeloArq.addRow(new Object[]{arq[0], fmtExibicao(arq[2]), arq[1]});
                }
                JOptionPane.showMessageDialog(dialog, "Arquivo incluido com sucesso!");
            }
        });

        btnVisualizar.addActionListener(e -> {
            int linhaSel = tabelaArq.getSelectedRow();
            if (linhaSel == -1) {
                JOptionPane.showMessageDialog(dialog, "Selecione um arquivo para visualizar!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String caminho = (String) modeloArq.getValueAt(linhaSel, 2);
            try {
                Desktop.getDesktop().open(new File(caminho));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Nao foi possivel abrir o arquivo:\n" + caminho, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botao Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(280, 600, 150, 35);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(btnSalvar);

        dialog.add(p);

        btnSalvar.addActionListener(e -> {
            String resolucao  = areaRes.getText().trim();
            String observacao = areaObs.getText().trim();
            String novoStatus = (String) comboStatus.getSelectedItem();
            controller.atualizarDetalhes(tarefa.getId(), resolucao, observacao, "", novoStatus);
            JOptionPane.showMessageDialog(dialog, "Demanda salva com sucesso!");
            dialog.dispose();
            carregarTabela();
        });

        dialog.setVisible(true);
    }

    private void addLabel(JPanel p, String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(new Color(189, 195, 199));
        l.setFont(new Font("Arial", Font.PLAIN, 11));
        p.add(l);
    }

    /**
     * Converte datas do banco (AAAA-MM-DD ou AAAA-MM-DD HH:mm) para exibição
     * no formato DD-MM-AAAA ou DD-MM-AAAA HH:mm.
     * Retorna "---" se a data for nula ou vazia.
     */
    private String fmtExibicao(String data) {
        if (data == null || data.trim().isEmpty()) return "---";
        try {
            // Formato completo com hora: "2026-05-12 16:30"
            if (data.length() >= 16 && data.charAt(4) == '-') {
                String[] partes = data.split(" ");
                String[] d = partes[0].split("-");
                String hora = partes.length > 1 ? " " + partes[1] : "";
                return d[2] + "-" + d[1] + "-" + d[0] + hora;
            }
            // Formato só data: "2026-05-12"
            if (data.length() == 10 && data.charAt(4) == '-') {
                String[] d = data.split("-");
                return d[2] + "-" + d[1] + "-" + d[0];
            }
        } catch (Exception e) {
            // Retorna o valor original se não conseguir converter
        }
        return data;
    }

    private String fmt(String data) {
        return fmtExibicao(data);
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Tarefa> tarefas = usuarioLogado.getPerfil().equals("ADMIN") ?
            controller.listar() : controller.listarPorResponsavel(usuarioLogado.getId());
        for (Tarefa t : tarefas) {
            modeloTabela.addRow(new Object[]{
                t.getId(), t.getNome(), t.getDescricao(), t.getStatus(),
                t.getProjetoId(),
                t.getDetalhes() != null ? t.getDetalhes() : "",
                fmt(t.getDataInicio()), fmt(t.getDataAtribuicao()),
                fmt(t.getDataUltimaAtualizacao()), fmt(t.getDataFim())
            });
        }
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(160, 35));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }
}
