package model;

import java.time.LocalDate;

public class Projeto {

    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFimPrevista;
    private String status;
    private int gerenteId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFimPrevista() { return dataFimPrevista; }
    public void setDataFimPrevista(LocalDate dataFimPrevista) { this.dataFimPrevista = dataFimPrevista; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getGerenteId() { return gerenteId; }
    public void setGerenteId(int gerenteId) { this.gerenteId = gerenteId; }
}