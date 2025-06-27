package com.lpn3.licitamatch.model;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private LocalDate dataCriacao;

    public Usuario() {
    }
    
    // Construtor para criar um novo usuário para salvar (ID vai serauto incrementado)
    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.dataCriacao = LocalDate.now();
    }
    
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome='" + nome + '\'' + ", email='" + email + '\'' + '}';
    }

 
}