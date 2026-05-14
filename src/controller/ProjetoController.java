package controller;

import service.ProjetoService;
import model.Projeto;
import java.time.LocalDate;
import java.util.List;

public class ProjetoController {

    private ProjetoService projetoService = new ProjetoService();

    public void cadastrar(String nome, String descricao, LocalDate dataInicio,
                          LocalDate dataFimPrevista, String status, int gerenteId) {
        projetoService.cadastrar(nome, descricao, dataInicio, dataFimPrevista, status, gerenteId);
    }

    public List<Projeto> listar() {
        return projetoService.listar();
    }

    public void atualizar(int id, String nome, String descricao, LocalDate dataInicio,
                          LocalDate dataFimPrevista, String status, int gerenteId) {
        projetoService.atualizar(id, nome, descricao, dataInicio, dataFimPrevista, status, gerenteId);
    }

    public void excluir(int id) {
        projetoService.excluir(id);
    }
}