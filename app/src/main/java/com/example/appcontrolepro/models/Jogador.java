package com.example.appcontrolepro.models;

// Classe que representa um jogador
public class Jogador {

    // Atributos do jogador
    private int id;
    private String nome;
    private int numero;
    private String posicao;

    // Construtor vazio
    public Jogador() {
    }

    // Construtor com parâmetros
    public Jogador(String nome, int numero, String posicao) {
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
    }

    // Retorna o id do jogador
    public int getId() {
        return id;
    }

    // Define o id do jogador
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o nome do jogador
    public String getNome() {
        return nome;
    }

    // Define o nome do jogador
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o número do jogador
    public int getNumero() {
        return numero;
    }

    // Define o número do jogador
    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Retorna a posição do jogador
    public String getPosicao() {
        return posicao;
    }

    // Define a posição do jogador
    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}