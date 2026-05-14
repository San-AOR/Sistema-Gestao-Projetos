package service;

import dao.ProjetoDAO;
import model.Projeto;
import java.time.LocalDate;
import java.util.List;

public class ProjetoService {

    private ProjetoDAO projetoDAO = new ProjetoDAO();

    public void cadastrar(String nome, String descricao, LocalDate dataInicio,
                          LocalDate dataFimPrevista, String status, int gerenteId) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Erro: Nome obrigatorio."); return;
        }
        if (dataFimPrevista.isBefore(dataInicio)) {
            System.out.println("Erro: Data de fim nao pode ser anterior ao inicio."); return;
        }
        if (gerenteId <= 0) {
            System.out.println("Erro: ID do gerente invalido."); return;
        }
        Projeto p = new Projeto();
        p.setNome(nome); p.setDescricao(descricao); p.setDataInicio(dataInicio);
        p.setDataFimPrevista(dataFimPrevista); p.setStatus(status); p.setGerenteId(gerenteId);
        projetoDAO.cadastrar(p);
    }

    public List<Projeto> listar() { return projetoDAO.listar(); }

    public void atualizar(int id, String nome, String descricao, LocalDate dataInicio,
                          LocalDate dataFimPrevista, String status, int gerenteId) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        Projeto p = new Projeto();
        p.setId(id); p.setNome(nome); p.setDescricao(descricao);
        p.setDataInicio(dataInicio); p.setDataFimPrevista(dataFimPrevista);
        p.setStatus(status); p.setGerenteId(gerenteId);
        projetoDAO.atualizar(p);
    }

    public void excluir(int id) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        projetoDAO.excluir(id);
    }
}