package com.lpn3.licitamatch.service;

import com.lpn3.licitamatch.controller.APIConnection;
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
    
    public Comparacao realizarComparacao(Proposta proposta, Licitacao licitacao) {
        // Aqui você chamaria sua APIConnection
        Comparacao temp = APIConnection.compararDocumentos(proposta, licitacao);
        if (temp == null) {
            throw new NullPointerException("Comparação returnada como nulo, erro de processamento.");
        }
        
        String semelhancaSimulada = temp.getTxtSemelhanca();
        String diferencaSimulada = temp.getTxtDiferenca();
        int notaSimulada = temp.getNota(); 
        
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