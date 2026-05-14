package controller;

import service.RelatorioService;

public class RelatorioController {

    private RelatorioService relatorioService = new RelatorioService();

    public void tarefasPorStatus() {
        relatorioService.tarefasPorStatus();
    }

    public void projetosAtrasados() {
        relatorioService.projetosAtrasados();
    }

    public void projetosEmAndamento() {
        relatorioService.projetosEmAndamento();
    }

    public void tarefasPorProjeto() {
        relatorioService.tarefasPorProjeto();
    }

    public Object[][] getTarefasPorStatus() {
        return relatorioService.getTarefasPorStatus();
    }

    public Object[][] getProjetosAtrasados() {
        return relatorioService.getProjetosAtrasados();
    }

    public Object[][] getProjetosEmAndamento() {
        return relatorioService.getProjetosEmAndamento();
    }

    public Object[][] getTarefasPorProjeto() {
        return relatorioService.getTarefasPorProjeto();
    }
}