package com.lpn3.licitamatch.model;

import java.time.LocalDateTime;

public class Comparacao {

    private int idLicitacaoFk;
    private int idPropostaFk;
    private String txtSemelhanca;
    private String txtDiferenca;
    private int nota;
    private LocalDateTime dataComparacao;

    public int getIdLicitacaoFk() { return idLicitacaoFk; }
    public void setIdLicitacaoFk(int idLicitacaoFk) { this.idLicitacaoFk = idLicitacaoFk; }
    public int getIdPropostaFk() { return idPropostaFk; }
    public void setIdPropostaFk(int idPropostaFk) { this.idPropostaFk = idPropostaFk; }
    public String getTxtSemelhanca() { return txtSemelhanca; }
    public void setTxtSemelhanca(String txtSemelhanca) { this.txtSemelhanca = txtSemelhanca; }
    public String getTxtDiferenca() { return txtDiferenca; }
    public void setTxtDiferenca(String txtDiferenca) { this.txtDiferenca = txtDiferenca; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }
    public LocalDateTime getDataComparacao() { return dataComparacao; }
    public void setDataComparacao(LocalDateTime dataComparacao) { this.dataComparacao = dataComparacao; }
}