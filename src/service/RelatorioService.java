package service;

import dao.RelatorioDAO;

public class RelatorioService {

    private final RelatorioDAO relatorioDAO = new RelatorioDAO();

    public void tarefasPorStatus() {
        relatorioDAO.tarefasPorStatus();
    }

    public void projetosAtrasados() {
        relatorioDAO.projetosAtrasados();
    }

    public void projetosEmAndamento() {
        relatorioDAO.projetosEmAndamento();
    }

    public void tarefasPorProjeto() {
        relatorioDAO.tarefasPorProjeto();
    }

    public Object[][] getTarefasPorStatus() {
        return relatorioDAO.getTarefasPorStatus();
    }

    public Object[][] getProjetosAtrasados() {
        return relatorioDAO.getProjetosAtrasados();
    }

    public Object[][] getProjetosEmAndamento() {
        return relatorioDAO.getProjetosEmAndamento();
    }

    public Object[][] getTarefasPorProjeto() {
        return relatorioDAO.getTarefasPorProjeto();
    }
}