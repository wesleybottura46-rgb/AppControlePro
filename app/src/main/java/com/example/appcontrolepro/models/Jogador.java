package com.example.appcontrolepro.models;

// classe que representa um jogador
public class Jogador {

    private int id;
    private String nome;
    private int numero;
    private String posicao;

    public Jogador() {
    }

    public Jogador(String nome, int numero, String posicao) {
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}