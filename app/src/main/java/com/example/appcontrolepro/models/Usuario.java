package com.example.appcontrolepro.models;

// Classe que representa um usuário
public class Usuario {

    // Atributos do usuário
    private int id;
    private String nome;
    private String email;
    private String senha;

    // Construtor vazio
    public Usuario() {
    }

    // Construtor com parâmetros
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Retorna o id do usuário
    public int getId() {
        return id;
    }

    // Define o id do usuário
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o nome do usuário
    public String getNome() {
        return nome;
    }

    // Define o nome do usuário
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o email do usuário
    public String getEmail() {
        return email;
    }

    // Define o email do usuário
    public void setEmail(String email) {
        this.email = email;
    }

    // Retorna a senha do usuário
    public String getSenha() {
        return senha;
    }

    // Define a senha do usuário
    public void setSenha(String senha) {
        this.senha = senha;
    }
}