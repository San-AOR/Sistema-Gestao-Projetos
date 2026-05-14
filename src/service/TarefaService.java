package service;

import dao.TarefaDAO;
import model.Tarefa;
import java.util.List;

public class TarefaService {
    public List<Tarefa> listarPorResponsavel(int responsavelId) {
    return tarefaDAO.listarPorResponsavel(responsavelId);
}

public void atribuirResponsavel(int tarefaId, int responsavelId) {
    if (tarefaId <= 0 || responsavelId <= 0) {
        System.out.println("Erro: IDs invalidos.");
        return;
    }
    tarefaDAO.atribuirResponsavel(tarefaId, responsavelId);
}

    private TarefaDAO tarefaDAO = new TarefaDAO();

    public void cadastrar(String nome, String descricao, String status, int projetoId) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Erro: Nome obrigatorio."); return;
        }
        if (projetoId <= 0) {
            System.out.println("Erro: ID do projeto invalido."); return;
        }
        Tarefa t = new Tarefa();
        t.setNome(nome); t.setDescricao(descricao);
        t.setStatus(status); t.setProjetoId(projetoId);
        tarefaDAO.cadastrar(t);
    }

    public List<Tarefa> listar() { return tarefaDAO.listar(); }

    public List<Tarefa> listarPorProjeto(int projetoId) {
        if (projetoId <= 0) { System.out.println("Erro: ID invalido."); return null; }
        return tarefaDAO.listarPorProjeto(projetoId);
    }

    public void atualizar(int id, String nome, String descricao, String status, int projetoId) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        Tarefa t = new Tarefa();
        t.setId(id); t.setNome(nome); t.setDescricao(descricao);
        t.setStatus(status); t.setProjetoId(projetoId);
        tarefaDAO.atualizar(t);
    }

    public void excluir(int id) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        tarefaDAO.excluir(id);
         }
       public void atualizarDetalhes(int id, String detalhes, String observacao, String documento, String status) {
    tarefaDAO.atualizarDetalhes(id, detalhes, observacao, documento, status);
}
}
