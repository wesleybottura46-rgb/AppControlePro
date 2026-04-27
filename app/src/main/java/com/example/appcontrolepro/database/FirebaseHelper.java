package com.example.appcontrolepro.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

// =========================================================
// FIREBASE HELPER
// =========================================================
// Esta classe e um "atalho" para acessar os servicos do Firebase.
//
// Por que ela existe?
// - Para evitar repetir FirebaseAuth.getInstance() em varias telas.
// - Para deixar o codigo das Activities mais limpo.
// - Para centralizar Auth, Firestore e Storage em um unico lugar.
//
// Servicos usados:
// - FirebaseAuth: login, cadastro, logout e senha.
// - FirebaseFirestore: banco de dados do app.
// - FirebaseStorage: upload do emblema do time.
public class FirebaseHelper {

    // Retorna o servico de autenticacao do Firebase.
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    // Retorna o banco Firestore.
    public static FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }

    // Retorna o Storage, usado para salvar imagens.
    public static FirebaseStorage getStorage() {
        return FirebaseStorage.getInstance();
    }

    // Retorna o UID do usuario logado.
    // Se nao tiver usuario logado, retorna null.
    public static String getUsuarioIdAtual() {
        if (getAuth().getCurrentUser() == null) {
            return null;
        }
        return getAuth().getCurrentUser().getUid();
    }
}
