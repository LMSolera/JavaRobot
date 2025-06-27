package com.lpn3.licitamatch.service;

import com.lpn3.licitamatch.dao.PropostaDAO;
import com.lpn3.licitamatch.dao.PropostaDAOImpl;
import com.lpn3.licitamatch.model.Proposta;
import com.lpn3.licitamatch.model.Usuario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PropostaService {
    private final PropostaDAO propostaDAO;

    public PropostaService() {
        this.propostaDAO = new PropostaDAOImpl();
    }

    public void salvarNovaProposta(Usuario usuarioLogado, File arquivoSelecionado) throws IOException {
        if (usuarioLogado == null) {
            throw new RuntimeException("Nenhum usuário logado. Ação não permitida.");
        }
        byte[] conteudoPdf = Files.readAllBytes(arquivoSelecionado.toPath());

        Proposta novaProposta = new Proposta();
        novaProposta.setIdUsuarioFk(usuarioLogado.getId());
        novaProposta.setNomeArquivo(arquivoSelecionado.getName());
        novaProposta.setArquivoPdf(conteudoPdf);

        propostaDAO.salvar(novaProposta);
    }
}