package com.example.appcontrolepro.models;

// =========================================================
// MODEL TIME
// =========================================================

// ESTA CLASSE REPRESENTA UM TIME DO APP.
//
// CADA DOCUMENTO DA COLEÇÃO "times"
// DO FIREBASE USA ESSE FORMATO.
//
// AGORA O TIME POSSUI:
//
// ✔ NOME
// ✔ CIDADE
// ✔ ESTADO
// ✔ DONO DO TIME
// ✔ EMBLEMA (URL DA IMAGEM)

public class Time {

    // =========================================================
    // ID DO DOCUMENTO FIREBASE
    // =========================================================

    private String id;

    // =========================================================
    // NOME DO TIME
    // =========================================================

    private String nome;

    // =========================================================
    // CIDADE
    // =========================================================

    private String cidade;

    // =========================================================
    // ESTADO
    // =========================================================

    private String estado;

    // =========================================================
    // ID DO USUÁRIO DONO
    // =========================================================

    private String userId;

    // =========================================================
    // URL DO EMBLEMA
    // =========================================================

    // AQUI FICA SALVA A URL DA IMAGEM
    // ENVIADA PARA O FIREBASE STORAGE.

    private String emblema;

    // =========================================================
    // CONSTRUTOR VAZIO
    // =========================================================

    // O FIREBASE PRECISA DELE
    // PARA CONSEGUIR LER OS DADOS.

    public Time(){

    }

    // =========================================================
    // CONSTRUTOR COMPLETO
    // =========================================================

    public Time(

            String nome,

            String cidade,

            String estado,

            String userId,

            String emblema
    ){

        // SALVA NOME
        this.nome = nome;

        // SALVA CIDADE
        this.cidade = cidade;

        // SALVA ESTADO
        this.estado = estado;

        // SALVA DONO
        this.userId = userId;

        // SALVA EMBLEMA
        this.emblema = emblema;
    }

    // =========================================================
    // GET ID
    // =========================================================

    public String getId() {

        return id;
    }

    // =========================================================
    // SET ID
    // =========================================================

    public void setId(String id) {

        this.id = id;
    }

    // =========================================================
    // GET NOME
    // =========================================================

    public String getNome() {

        return nome;
    }

    // =========================================================
    // SET NOME
    // =========================================================

    public void setNome(String nome) {

        this.nome = nome;
    }

    // =========================================================
    // GET CIDADE
    // =========================================================

    public String getCidade() {

        return cidade;
    }

    // =========================================================
    // SET CIDADE
    // =========================================================

    public void setCidade(String cidade) {

        this.cidade = cidade;
    }

    // =========================================================
    // GET ESTADO
    // =========================================================

    public String getEstado() {

        return estado;
    }

    // =========================================================
    // SET ESTADO
    // =========================================================

    public void setEstado(String estado) {

        this.estado = estado;
    }

    // =========================================================
    // GET USER ID
    // =========================================================

    public String getUserId() {

        return userId;
    }

    // =========================================================
    // SET USER ID
    // =========================================================

    public void setUserId(String userId) {

        this.userId = userId;
    }

    // =========================================================
    // GET EMBLEMA
    // =========================================================

    public String getEmblema() {

        return emblema;
    }

    // =========================================================
    // SET EMBLEMA
    // =========================================================

    public void setEmblema(String emblema) {

        this.emblema = emblema;
    }
}