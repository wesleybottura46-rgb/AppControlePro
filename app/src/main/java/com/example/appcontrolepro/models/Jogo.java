package com.example.appcontrolepro.models;

// Classe que representa um jogo
public class Jogo {

    // Atributos do jogo
    private int id;
    private String adversario;
    private String data;
    private String local;

    // Construtor vazio
    public Jogo() {
    }

    // Construtor com parâmetros
    public Jogo(String adversario, String data, String local) {
        this.adversario = adversario;
        this.data = data;
        this.local = local;
    }

    // Retorna o id do jogo
    public int getId() {
        return id;
    }

    // Define o id do jogo
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o adversário
    public String getAdversario() {
        return adversario;
    }

    // Define o adversário
    public void setAdversario(String adversario) {
        this.adversario = adversario;
    }

    // Retorna a data do jogo
    public String getData() {
        return data;
    }

    // Define a data do jogo
    public void setData(String data) {
        this.data = data;
    }

    // Retorna o local do jogo
    public String getLocal() {
        return local;
    }

    // Define o local do jogo
    public void setLocal(String local) {
        this.local = local;
    }
}