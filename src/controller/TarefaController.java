package controller;

import dao.TarefaDAO;
import model.Tarefa;
import java.util.List;

public class TarefaController {

    private TarefaDAO dao = new TarefaDAO();

    public void cadastrar(String nome, String descricao, String status, int projetoId) {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(nome);
        tarefa.setDescricao(descricao);
        tarefa.setStatus(status);
        tarefa.setProjetoId(projetoId);
        dao.cadastrar(tarefa);
    }

    public List<Tarefa> listar() {
        return dao.listar();
    }

    public List<Tarefa> listarPorProjeto(int projetoId) {
        return dao.listarPorProjeto(projetoId);
    }

    public List<Tarefa> listarPorResponsavel(int responsavelId) {
        return dao.listarPorResponsavel(responsavelId);
    }

    public void atualizar(int id, String nome, String descricao, String status, int projetoId) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setNome(nome);
        tarefa.setDescricao(descricao);
        tarefa.setStatus(status);
        tarefa.setProjetoId(projetoId);
        dao.atualizar(tarefa);
    }

    public void excluir(int id) {
        dao.excluir(id);
    }

    public void atribuirResponsavel(int tarefaId, int responsavelId) {
        dao.atribuirResponsavel(tarefaId, responsavelId);
    }

    public void atualizarDetalhes(int id, String detalhes, String observacao, String documento, String status) {
        dao.atualizarDetalhes(id, detalhes, observacao, documento, status);
    }
}