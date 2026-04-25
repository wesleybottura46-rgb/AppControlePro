package com.example.appcontrolepro.models;

// classe que representa um jogo
public class Jogo {

    private int id;
    private String adversario;
    private String data;
    private String local;

    public Jogo() {
    }

    public Jogo(String adversario, String data, String local) {
        this.adversario = adversario;
        this.data = data;
        this.local = local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdversario() {
        return adversario;
    }

    public void setAdversario(String adversario) {
        this.adversario = adversario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}