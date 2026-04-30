package com.example.appcontrolepro.models;

public class Jogo {

    // ID do documento no Firestore.
    private String id;

    // ID do time dono desse jogo.
    private String timeId;

    // Dados basicos do jogo.
    private String adversario;
    private String data;
    private String hora;
    private String local;

    // Placar salvo pela SumulaActivity.
    private Long placarNosso;
    private Long placarAdversario;

    // Construtor vazio obrigatorio para o Firebase.
    public Jogo() {
    }

    // Construtor para criar jogo novo antes da sumula existir.
    public Jogo(String timeId, String adversario, String data, String hora, String local) {
        this.timeId = timeId;
        this.adversario = adversario;
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.placarNosso = null;
        this.placarAdversario = null;
    }

    // Getters e setters.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTimeId() { return timeId; }
    public void setTimeId(String timeId) { this.timeId = timeId; }

    public String getAdversario() { return adversario; }
    public void setAdversario(String adversario) { this.adversario = adversario; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public Long getPlacarNosso() { return placarNosso; }
    public void setPlacarNosso(Long placarNosso) { this.placarNosso = placarNosso; }

    public Long getPlacarAdversario() { return placarAdversario; }
    public void setPlacarAdversario(Long placarAdversario) { this.placarAdversario = placarAdversario; }
}
