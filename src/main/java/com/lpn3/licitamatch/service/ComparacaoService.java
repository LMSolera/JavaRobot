package com.lpn3.licitamatch.service;

import com.lpn3.licitamatch.dao.ComparacaoDAO;
import com.lpn3.licitamatch.dao.ComparacaoDAOImpl;
import com.lpn3.licitamatch.model.Comparacao;
import com.lpn3.licitamatch.model.Licitacao;
import com.lpn3.licitamatch.model.Proposta;

public class ComparacaoService {

    private final ComparacaoDAO comparacaoDAO;

    public ComparacaoService() {
        this.comparacaoDAO = new ComparacaoDAOImpl();
    }
    
    public Comparacao realizarComparacao(Licitacao licitacao, Proposta proposta) {
        // Aqui você chamaria sua APIConnection
        String semelhancaSimulada = "Coloca o resultado";
        String diferencaSimulada = "Coloca o resultado";
        int notaSimulada = 85; 

        Comparacao resultado = new Comparacao();
        resultado.setIdLicitacaoFk(licitacao.getId());
        resultado.setIdPropostaFk(proposta.getId());
        resultado.setTxtSemelhanca(semelhancaSimulada);
        resultado.setTxtDiferenca(diferencaSimulada);
        resultado.setNota(notaSimulada);

        // Salva o resultado no banco de dados.
        comparacaoDAO.salvar(resultado);

        return resultado;
    }
}