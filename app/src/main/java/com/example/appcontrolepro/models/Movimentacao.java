package com.example.appcontrolepro.models;

// ==========================================
// MODEL MOVIMENTAÇÃO
// ==========================================

public class Movimentacao {

    // ID DOCUMENTO
    private String id;

    // DESCRIÇÃO
    private String descricao;

    // VALOR
    private double valor;

    // TIPO
    private String tipo;

    // CONSTRUTOR VAZIO
    public Movimentacao(){

    }

    // CONSTRUTOR
    public Movimentacao(
            String id,
            String descricao,
            double valor,
            String tipo
    ){

        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    // GET ID
    public String getId(){
        return id;
    }

    // GET DESCRIÇÃO
    public String getDescricao(){
        return descricao;
    }

    // GET VALOR
    public double getValor(){
        return valor;
    }

    // GET TIPO
    public String getTipo(){
        return tipo;
    }
}