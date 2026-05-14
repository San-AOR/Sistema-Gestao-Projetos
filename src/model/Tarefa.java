package model;

public class Tarefa {

    private int id;

    private String nome;

    private String descricao;

    private String status;

    private int projetoId;

    private int responsavelId;

    // =========================================
    // NOVOS CAMPOS
    // =========================================

    private String resolucao;

    private String observacaoInterna;

    // =========================================
    // GETTERS E SETTERS
    // =========================================
    private String detalhes;
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }

    public int getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(int responsavelId) {
        this.responsavelId = responsavelId;
    }

    // =========================================
    // RESOLUÇÃO
    // =========================================

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    // =========================================
    // OBSERVAÇÃO INTERNA
    // =========================================

    public String getObservacaoInterna() {
        return observacaoInterna;
    }

    public void setObservacaoInterna(
            String observacaoInterna
    ) {
        this.observacaoInterna =
                observacaoInterna;
    }
    private String observacao;
private String dataInicio;
private String dataAtribuicao;
private String dataUltimaAtualizacao;
private String dataFim;
private String documento;

public String getObservacao() { return observacao; }
public void setObservacao(String observacao) { this.observacao = observacao; }
public String getDataInicio() { return dataInicio; }
public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
public String getDataAtribuicao() { return dataAtribuicao; }
public void setDataAtribuicao(String dataAtribuicao) { this.dataAtribuicao = dataAtribuicao; }
public String getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }
public String getDataFim() { return dataFim; }
public void setDataFim(String dataFim) { this.dataFim = dataFim; }
public String getDocumento() { return documento; }
public void setDocumento(String documento) { this.documento = documento; }
}