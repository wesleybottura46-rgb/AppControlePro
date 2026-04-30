package com.example.appcontrolepro.models;

public class Jogador {

    // ID do documento no Firestore.
    private String id;

    // Nome do jogador.
    private String nome;

    // Posicao em campo.
    private String posicao;

    // ID do time ao qual esse jogador pertence.
    private String timeId;

    // Estatisticas acumuladas.
    private int gols;
    private int assistencias;
    private int jogos;

    // Construtor vazio obrigatorio para o Firebase.
    public Jogador() {
    }

    // Construtor usado quando queremos criar um jogador novo no codigo.
    public Jogador(String nome, String posicao, String timeId) {
        this.nome = nome;
        this.posicao = posicao;
        this.timeId = timeId;
        this.gols = 0;
        this.assistencias = 0;
        this.jogos = 0;
    }

    // Getters e setters permitem acessar/alterar campos privados.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPosicao() { return posicao; }
    public void setPosicao(String posicao) { this.posicao = posicao; }

    public String getTimeId() { return timeId; }
    public void setTimeId(String timeId) { this.timeId = timeId; }

    public int getGols() { return gols; }
    public void setGols(int gols) { this.gols = gols; }

    public int getAssistencias() { return assistencias; }
    public void setAssistencias(int assistencias) { this.assistencias = assistencias; }

    public int getJogos() { return jogos; }
    public void setJogos(int jogos) { this.jogos = jogos; }
}
