package com.lpn3.licitamatch.service;

import com.lpn3.licitamatch.dao.LicitacaoDAO;
import com.lpn3.licitamatch.dao.LicitacaoDAOImpl;
import com.lpn3.licitamatch.model.Licitacao;
import com.lpn3.licitamatch.model.Usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class LicitacaoService {

    private final LicitacaoDAO licitacaoDAO;

    public LicitacaoService() {
        // A camada de serviço decide qual implementação concreta do DAO usar.
        this.licitacaoDAO = new LicitacaoDAOImpl();
    }

    public void salvarNovaLicitacao(Usuario usuarioLogado, File arquivoSelecionado) throws IOException {
        // 1. REGRA DE NEGÓCIO: Validar se o usuário está logado.
        if (usuarioLogado == null) {
            throw new RuntimeException("Nenhum usuário logado. Ação de upload não permitida.");
        }

        // 2. LÓGICA DE NEGÓCIO: Ler o conteúdo do arquivo para um array de bytes.
        // O método Files.readAllBytes faz isso de forma eficiente.
        byte[] conteudoPdf = Files.readAllBytes(arquivoSelecionado.toPath());

        // 3. Preparar o objeto Modelo (DTO) para ser salvo.
        Licitacao novaLicitacao = new Licitacao();
        novaLicitacao.setIdUsuarioFk(usuarioLogado.getId());
        novaLicitacao.setNomeArquivo(arquivoSelecionado.getName());
        novaLicitacao.setArquivoPdf(conteudoPdf);

        // 4. Chamar a camada DAO para persistir o objeto no banco de dados.
        licitacaoDAO.salvar(novaLicitacao);
    }

}
