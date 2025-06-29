package com.lpn3.licitamatch.model;

import java.io.File;
import java.time.LocalDateTime;

public class Licitacao {

    private int id;
    private int idUsuarioFk;
    private String nomeArquivo;
    private byte[] arquivoPdf;
    private File filePdf;
    private LocalDateTime dataEnvio;
    
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getIdUsuarioFk() {return idUsuarioFk;}
    public void setIdUsuarioFk(int idUsuarioFk) {this.idUsuarioFk = idUsuarioFk;}
    public String getNomeArquivo() {return nomeArquivo;}
    public void setNomeArquivo(String nomeArquivo) {this.nomeArquivo = nomeArquivo;}
    public byte[] getArquivoPdf() {return arquivoPdf;}
    public void setArquivoPdf(byte[] arquivoPdf) {this.arquivoPdf = arquivoPdf;}  
    public File getFilePdf() {return filePdf;}
    public void setFilePdf(File filePdf) {this.filePdf = filePdf;}
    public LocalDateTime getDataEnvio() {return dataEnvio;}
    public void setDataEnvio(LocalDateTime dataEnvio) {this.dataEnvio = dataEnvio;}

}
