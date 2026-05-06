// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.models;

// ======================================================
// MODEL JOGO
// ======================================================
//
// UM MODEL É O "MOLDE" DOS DADOS.
//
// ESSA CLASSE REPRESENTA:
//
// ✔ UM JOGO
// ✔ DATA DO JOGO
// ✔ ADVERSÁRIO
// ✔ LOCAL
// ✔ PLACAR
//
// COLEÇÃO FIRESTORE:
//
// jogos
//
// OBSERVAÇÃO:
//
// O PLACAR PODE COMEÇAR NULO,
// PORQUE A SÚMULA AINDA NÃO FOI PREENCHIDA.
//
// ======================================================

// CRIA CLASSE
public class Jogo {

    // ======================================================
    // ID
    // ======================================================
    //
    // ID DO DOCUMENTO FIRESTORE
    //
    // ======================================================

    // ID DO JOGO
    private String id;

    // ======================================================
    // TIME ID
    // ======================================================
    //
    // ID DO TIME DONO DO JOGO
    //
    // ======================================================

    // ID DO TIME
    private String timeId;

    // ======================================================
    // DADOS DO JOGO
    // ======================================================
    //
    // GUARDA:
    //
    // ✔ ADVERSÁRIO
    // ✔ DATA
    // ✔ HORA
    // ✔ LOCAL
    //
    // ======================================================

    // NOME DO ADVERSÁRIO
    private String adversario;

    // DATA DO JOGO
    private String data;

    // HORA DO JOGO
    private String hora;

    // LOCAL DO JOGO
    private String local;

    // ======================================================
    // PLACAR
    // ======================================================
    //
    // PLACAR SALVO PELA SÚMULA
    //
    // ======================================================

    // GOLS DO NOSSO TIME
    private Long placarNosso;

    // GOLS DO ADVERSÁRIO
    private Long placarAdversario;

    // ======================================================
    // CONSTRUTOR VAZIO
    // ======================================================
    //
    // O FIREBASE PRECISA DESSE CONSTRUTOR
    //
    // ======================================================

    // CONSTRUTOR VAZIO
    public Jogo() {

    }

    // ======================================================
    // CONSTRUTOR COMPLETO
    // ======================================================
    //
    // USADO PARA CRIAR NOVO JOGO
    //
    // ======================================================

    // CONSTRUTOR
    public Jogo(

            String timeId,

            String adversario,

            String data,

            String hora,

            String local
    ) {

        // SALVA ID DO TIME
        this.timeId = timeId;

        // SALVA ADVERSÁRIO
        this.adversario = adversario;

        // SALVA DATA
        this.data = data;

        // SALVA HORA
        this.hora = hora;

        // SALVA LOCAL
        this.local = local;

        // PLACAR COMEÇA NULO
        this.placarNosso = null;

        // PLACAR COMEÇA NULO
        this.placarAdversario = null;
    }

    // ======================================================
    // GET ID
    // ======================================================
    //
    // RETORNA ID
    //
    // ======================================================

    public String getId() {

        return id;
    }

    // ======================================================
    // SET ID
    // ======================================================
    //
    // ALTERA ID
    //
    // ======================================================

    public void setId(String id) {

        this.id = id;
    }

    // ======================================================
    // GET TIME ID
    // ======================================================
    //
    // RETORNA ID DO TIME
    //
    // ======================================================

    public String getTimeId() {

        return timeId;
    }

    // ======================================================
    // SET TIME ID
    // ======================================================
    //
    // ALTERA ID DO TIME
    //
    // ======================================================

    public void setTimeId(String timeId) {

        this.timeId = timeId;
    }

    // ======================================================
    // GET ADVERSÁRIO
    // ======================================================
    //
    // RETORNA ADVERSÁRIO
    //
    // ======================================================

    public String getAdversario() {

        return adversario;
    }

    // ======================================================
    // SET ADVERSÁRIO
    // ======================================================
    //
    // ALTERA ADVERSÁRIO
    //
    // ======================================================

    public void setAdversario(String adversario) {

        this.adversario = adversario;
    }

    // ======================================================
    // GET DATA
    // ======================================================
    //
    // RETORNA DATA
    //
    // ======================================================

    public String getData() {

        return data;
    }

    // ======================================================
    // SET DATA
    // ======================================================
    //
    // ALTERA DATA
    //
    // ======================================================

    public void setData(String data) {

        this.data = data;
    }

    // ======================================================
    // GET HORA
    // ======================================================
    //
    // RETORNA HORA
    //
    // ======================================================

    public String getHora() {

        return hora;
    }

    // ======================================================
    // SET HORA
    // ======================================================
    //
    // ALTERA HORA
    //
    // ======================================================

    public void setHora(String hora) {

        this.hora = hora;
    }

    // ======================================================
    // GET LOCAL
    // ======================================================
    //
    // RETORNA LOCAL
    //
    // ======================================================

    public String getLocal() {

        return local;
    }

    // ======================================================
    // SET LOCAL
    // ======================================================
    //
    // ALTERA LOCAL
    //
    // ======================================================

    public void setLocal(String local) {

        this.local = local;
    }

    // ======================================================
    // GET PLACAR NOSSO
    // ======================================================
    //
    // RETORNA GOLS NOSSO TIME
    //
    // ======================================================

    public Long getPlacarNosso() {

        return placarNosso;
    }

    // ======================================================
    // SET PLACAR NOSSO
    // ======================================================
    //
    // ALTERA GOLS NOSSO TIME
    //
    // ======================================================

    public void setPlacarNosso(Long placarNosso) {

        this.placarNosso = placarNosso;
    }

    // ======================================================
    // GET PLACAR ADVERSÁRIO
    // ======================================================
    //
    // RETORNA GOLS ADVERSÁRIO
    //
    // ======================================================

    public Long getPlacarAdversario() {

        return placarAdversario;
    }

    // ======================================================
    // SET PLACAR ADVERSÁRIO
    // ======================================================
    //
    // ALTERA GOLS ADVERSÁRIO
    //
    // ======================================================

    public void setPlacarAdversario(Long placarAdversario) {

        this.placarAdversario = placarAdversario;
    }
}