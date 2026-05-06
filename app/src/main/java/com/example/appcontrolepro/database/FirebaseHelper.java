// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.database;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA FIREBASE AUTH
import com.google.firebase.auth.FirebaseAuth;

// IMPORTA FIRESTORE
import com.google.firebase.firestore.FirebaseFirestore;

// IMPORTA STORAGE
import com.google.firebase.storage.FirebaseStorage;

// ======================================================
// FIREBASE HELPER
// ======================================================
//
// ESSA CLASSE É UM "ATALHO"
//
// ELA SERVE PARA:
//
// ✔ LOGIN
// ✔ CADASTRO
// ✔ BANCO DE DADOS
// ✔ STORAGE
// ✔ PEGAR ID DO USUÁRIO
//
// ASSIM NÃO PRECISA REPETIR
// FIREBASEAUTH.GETINSTANCE()
// EM TODAS AS TELAS.
//
// ======================================================

// CRIA CLASSE
public class FirebaseHelper {

    // ======================================================
    // FIREBASE AUTH
    // ======================================================
    //
    // RESPONSÁVEL POR:
    //
    // ✔ LOGIN
    // ✔ CADASTRO
    // ✔ LOGOUT
    // ✔ RECUPERAR SENHA
    //
    // ======================================================

    // MÉTODO PÚBLICO
    public static FirebaseAuth getAuth() {

        // RETORNA FIREBASE AUTH
        return FirebaseAuth.getInstance();
    }

    // ======================================================
    // FIRESTORE
    // ======================================================
    //
    // RESPONSÁVEL PELO:
    //
    // ✔ BANCO DE DADOS
    // ✔ JOGADORES
    // ✔ JOGOS
    // ✔ FINANCEIRO
    // ✔ TIMES
    //
    // ======================================================

    // MÉTODO PÚBLICO
    public static FirebaseFirestore getFirestore() {

        // RETORNA FIRESTORE
        return FirebaseFirestore.getInstance();
    }

    // ======================================================
    // STORAGE
    // ======================================================
    //
    // RESPONSÁVEL POR:
    //
    // ✔ SALVAR IMAGENS
    // ✔ EMBLEMAS
    // ✔ FOTOS
    //
    // ======================================================

    // MÉTODO PÚBLICO
    public static FirebaseStorage getStorage() {

        // RETORNA STORAGE
        return FirebaseStorage.getInstance();
    }

    // ======================================================
    // PEGAR ID DO USUÁRIO
    // ======================================================
    //
    // ESSE MÉTODO:
    //
    // ✔ PEGA O UID
    // ✔ IDENTIFICA USUÁRIO
    // ✔ RETORNA NULL SE NÃO TIVER LOGIN
    //
    // ======================================================

    // MÉTODO PÚBLICO
    public static String getUsuarioIdAtual() {

        // ==================================================
        // VERIFICA SE TEM USUÁRIO
        // ==================================================

        if (

                getAuth()
                        .getCurrentUser()

                        == null
        ) {

            // RETORNA NULO
            return null;
        }

        // ==================================================
        // RETORNA UID
        // ==================================================

        return getAuth()

                .getCurrentUser()

                .getUid();
    }
}