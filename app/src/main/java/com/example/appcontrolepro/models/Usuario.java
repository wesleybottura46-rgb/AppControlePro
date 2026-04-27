package com.example.appcontrolepro.models;

// =========================================================
// MODEL: USUARIO
// =========================================================
// Representa dados basicos do usuario.
//
// Importante:
// A senha nao deve ser salva no Firestore.
// O Firebase Authentication ja cuida da senha com seguranca.
public class Usuario {

    // UID do Firebase Auth.
    private String id;

    // Nome e email do usuario.
    private String nome;
    private String email;

    // Construtor vazio obrigatorio para o Firebase.
    public Usuario() {
    }

    // Construtor para criar um objeto Usuario manualmente.
    public Usuario(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Getters e setters.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
