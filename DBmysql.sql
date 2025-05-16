CREATE TABLE Usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    PRIMARY KEY(id_usuario)
    -- CHECKs ignorados em MySQL, use lógica na aplicação
);

CREATE TABLE Licitacao (
    id_licitacao INT NOT NULL AUTO_INCREMENT,
    id_usuario_fk INT NOT NULL,
    nome_arquivo VARCHAR(255) NOT NULL,
    caminho_arquivo VARCHAR(255) NOT NULL,
    arquivo_pdf BLOB NOT NULL,
    data_envio DATE NOT NULL,
    PRIMARY KEY(id_licitacao),
    FOREIGN KEY (id_usuario_fk) REFERENCES Usuario (id_usuario)
);

CREATE TABLE Proposta (
    id_proposta INT NOT NULL AUTO_INCREMENT,
    id_usuario_fk INT NOT NULL,
    nome_arquivo VARCHAR(25) NOT NULL,
    caminho_arquivo VARCHAR(55) NOT NULL,
    arquivo_pdf BLOB NOT NULL,
    data_envio DATE NOT NULL,
    PRIMARY KEY(id_proposta),
    FOREIGN KEY (id_usuario_fk) REFERENCES Usuario (id_usuario)
);

CREATE TABLE Resultado(
    id_resultado INT NOT NULL AUTO_INCREMENT,
    txtsemelhanca TEXT,
    txtdiferenca TEXT,
    nota INT NOT NULL,
    PRIMARY KEY(id_resultado)
    -- CHECKs ignorados em MySQL, use lógica na aplicação
);

CREATE TABLE Comparacao (
    id_licitacao_fk INT NOT NULL,
    id_proposta_fk INT NOT NULL,
    id_resultado_fk INT NOT NULL,
    PRIMARY KEY(id_licitacao_fk, id_proposta_fk),
    FOREIGN KEY(id_licitacao_fk) REFERENCES Licitacao(id_licitacao),
    FOREIGN KEY(id_proposta_fk) REFERENCES Proposta(id_proposta),
    FOREIGN KEY(id_resultado_fk) REFERENCES Resultado(id_resultado)
);

CREATE TABLE Historico_usuario (
    id_historico INT NOT NULL AUTO_INCREMENT,
    data_hora TIMESTAMP NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    operacao VARCHAR(20) NOT NULL,
    dados_removidos VARCHAR(1000) NOT NULL,
    motivo VARCHAR(100) NOT NULL,
    PRIMARY KEY(id_historico)
);

CREATE TABLE Historico_proposta (
    id_historico INT NOT NULL AUTO_INCREMENT,
    data_hora TIMESTAMP NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    operacao VARCHAR(20) NOT NULL,
    dados_removidos VARCHAR(1000) NOT NULL,
    motivo VARCHAR(100) NOT NULL,
    PRIMARY KEY(id_historico)
);

CREATE TABLE Historico_licitacao (
    id_historico INT NOT NULL AUTO_INCREMENT,
    data_hora TIMESTAMP NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    operacao VARCHAR(20) NOT NULL,
    dados_removidos VARCHAR(1000) NOT NULL,
    motivo VARCHAR(100) NOT NULL,
    PRIMARY KEY(id_historico)
);
