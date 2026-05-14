package service;

import dao.EquipeDAO;
import model.Equipe;
import java.util.List;

public class EquipeService {

    private EquipeDAO equipeDAO = new EquipeDAO();

    public void cadastrar(String nome, String descricao) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Erro: Nome obrigatorio."); return;
        }
        Equipe e = new Equipe();
        e.setNome(nome); e.setDescricao(descricao);
        equipeDAO.cadastrar(e);
    }

    public List<Equipe> listar() { return equipeDAO.listar(); }

    public void atualizar(int id, String nome, String descricao) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        Equipe e = new Equipe();
        e.setId(id); e.setNome(nome); e.setDescricao(descricao);
        equipeDAO.atualizar(e);
    }

    public void excluir(int id) {
        if (id <= 0) { System.out.println("Erro: ID invalido."); return; }
        equipeDAO.excluir(id);
    }
}