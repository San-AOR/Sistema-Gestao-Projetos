package controller;

import service.EquipeService;
import model.Equipe;
import java.util.List;

public class EquipeController {

    private EquipeService equipeService = new EquipeService();

    public void cadastrar(String nome, String descricao) {
        equipeService.cadastrar(nome, descricao);
    }

    public List<Equipe> listar() {
        return equipeService.listar();
    }

    public void atualizar(int id, String nome, String descricao) {
        equipeService.atualizar(id, nome, descricao);
    }

    public void excluir(int id) {
        equipeService.excluir(id);
    }
}