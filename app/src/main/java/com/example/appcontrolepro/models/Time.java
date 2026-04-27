package com.example.appcontrolepro.models;

// =========================================================
// MODEL: TIME
// =========================================================
// Representa um time cadastrado pelo usuario.
//
// Colecao no Firestore:
// times
public class Time {

    // ID do documento no Firestore.
    private String id;

    // Dados principais do time.
    private String nome;
    private String cidade;
    private String estado;

    // UID do usuario dono do time.
    private String userId;

    // URL da imagem enviada para o Firebase Storage.
    private String emblema;

    // Construtor vazio obrigatorio para o Firebase.
    public Time() {
    }

    // Construtor para criar time novo.
    public Time(String nome, String cidade, String estado, String userId, String emblema) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.userId = userId;
        this.emblema = emblema;
    }

    // Getters e setters.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmblema() { return emblema; }
    public void setEmblema(String emblema) { this.emblema = emblema; }
}
