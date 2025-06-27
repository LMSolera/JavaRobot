CREATE TABLE `Usuario` (
  `id_usuario` INT PRIMARY KEY AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `senha_hash` VARCHAR(255) NOT NULL,
  
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Licitacao` (
  `id_licitacao` INT  PRIMARY KEY AUTO_INCREMENT, 
  `id_usuario_fk` INT  NOT NULL,                 
  `nome_arquivo` VARCHAR(255) NOT NULL,
  `arquivo_pdf` LONGBLOB NULL,
  `data_envio` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT `fk_licitacao_usuario`
    FOREIGN KEY (`id_usuario_fk`) REFERENCES `Usuario`(`id_usuario`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Proposta` (
  `id_proposta` INT  PRIMARY KEY AUTO_INCREMENT,
  `id_usuario_fk` INT  NOT NULL,                 
  `nome_arquivo` VARCHAR(255) NOT NULL,
  `arquivo_pdf` LONGBLOB NULL,
  `data_envio` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT `fk_proposta_usuario`
    FOREIGN KEY (`id_usuario_fk`) REFERENCES `Usuario`(`id_usuario`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Comparacao` (
  `id_licitacao_fk` INT  NOT NULL, 
  `id_proposta_fk` INT  NOT NULL,  
  
  `txt_semelhanca` TEXT NULL,
  `txt_diferenca` TEXT NULL,
  `nota` TINYINT  NOT NULL CHECK (`nota` BETWEEN 0 AND 100),
  `data_comparacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id_licitacao_fk`, `id_proposta_fk`),

  CONSTRAINT `fk_comparacao_licitacao`
    FOREIGN KEY (`id_licitacao_fk`) REFERENCES `Licitacao`(`id_licitacao`)
    ON DELETE CASCADE,

  CONSTRAINT `fk_comparacao_proposta`
    FOREIGN KEY (`id_proposta_fk`) REFERENCES `Proposta`(`id_proposta`)
    ON DELETE CASCADE
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Historico` (
  `id_historico` int  PRIMARY KEY AUTO_INCREMENT, -- CORREÇÃO: Adicionado UNSIGNED
  `id_usuario_fk` INT  NOT NULL,                    -- CORREÇÃO: Adicionado UNSIGNED
  `operacao` VARCHAR(50) NOT NULL COMMENT 'Ex: "DELETE_PROPOSTA", "UPDATE_USUARIO"',
  `tabela_afetada` VARCHAR(50) NOT NULL COMMENT 'Ex: "Proposta", "Usuario"',
  `id_registro_afetado` INT UNSIGNED NOT NULL,
  `dados_anteriores` TEXT NULL COMMENT 'Dados do registro antes da alteração (em formato JSON)',
  `motivo` VARCHAR(255) NULL,
  `data_operacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT `fk_historico_usuario`
    FOREIGN KEY (`id_usuario_fk`) REFERENCES `Usuario`(`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
