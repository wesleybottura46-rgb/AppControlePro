package com.example.appcontrolepro.models;

// Classe que representa um time
public class Time {

    // Atributos do time
    private int id;
    private String nome;
    private String cidade;
    private String estado;

    // Construtor vazio
    public Time() {
    }

    // Construtor com parâmetros
    public Time(String nome, String cidade, String estado) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Retorna o id do time
    public int getId() {
        return id;
    }

    // Define o id do time
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o nome do time
    public String getNome() {
        return nome;
    }

    // Define o nome do time
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna a cidade do time
    public String getCidade() {
        return cidade;
    }

    // Define a cidade do time
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    // Retorna o estado do time
    public String getEstado() {
        return estado;
    }

    // Define o estado do time
    public void setEstado(String estado) {
        this.estado = estado;
    }
}