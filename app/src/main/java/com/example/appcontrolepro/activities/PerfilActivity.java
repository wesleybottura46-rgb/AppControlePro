// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA TELA PADRÃO
import androidx.appcompat.app.AppCompatActivity;

// IMPORTA TROCA DE TELAS
import android.content.Intent;

// IMPORTA CICLO DA TELA
import android.os.Bundle;

// IMPORTA VIEW
import android.view.View;

// IMPORTA COMPONENTES VISUAIS
import android.widget.*;

// IMPORTA R
import com.example.appcontrolepro.R;

// IMPORTA FIREBASE HELPER
import com.example.appcontrolepro.database.FirebaseHelper;

// IMPORTA SESSION MANAGER
import com.example.appcontrolepro.utils.SessionManager;

// IMPORTA USUÁRIO FIREBASE
import com.google.firebase.auth.FirebaseUser;

// ======================================================
// TELA PERFIL / CONFIGURAÇÕES
// ======================================================
//
// ESSA TELA:
//
// ✔ ALTERA SENHA
// ✔ RECUPERA SENHA
// ✔ EXCLUI CONTA
// ✔ FAZ LOGOUT
// ✔ VOLTA PARA LOGIN
//
// ======================================================

// CRIA CLASSE
public class PerfilActivity extends AppCompatActivity {

    // ======================================================
    // CAMPO SENHA
    // ======================================================

    // CAMPO NOVA SENHA
    private EditText edtNovaSenha;

    // ======================================================
    // USUÁRIO FIREBASE
    // ======================================================

    // GUARDA USUÁRIO LOGADO
    private FirebaseUser user;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // CHAMA MÉTODO PAI
        super.onCreate(savedInstanceState);

        // ======================================================
        // DEFINE XML
        // ======================================================

        setContentView(R.layout.activity_perfil);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        // CAMPO NOVA SENHA
        edtNovaSenha =
                findViewById(R.id.edtNovaSenha);

        // ======================================================
        // PEGA USUÁRIO LOGADO
        // ======================================================

        user =
                FirebaseHelper
                        .getAuth()
                        .getCurrentUser();

        // ======================================================
        // SE NÃO EXISTIR USUÁRIO
        // ======================================================

        if(user == null){

            // ABRE LOGIN
            abrirLogin();
        }
    }

    // ======================================================
    // ALTERAR SENHA
    // ======================================================

    public void alterarSenha(View view) {

        // ======================================================
        // PEGA NOVA SENHA
        // ======================================================

        String novaSenha =

                edtNovaSenha

                        .getText()

                        .toString()

                        .trim();

        // ======================================================
        // VERIFICA TAMANHO
        // ======================================================

        if (novaSenha.length() < 6) {

            // MOSTRA MENSAGEM
            Toast.makeText(

                    this,

                    "Senha mínima 6 caracteres",

                    Toast.LENGTH_SHORT

            ).show();

            // PARA EXECUÇÃO
            return;
        }

        // ======================================================
        // ALTERA SENHA FIREBASE
        // ======================================================

        user.updatePassword(novaSenha)

                // SE DER CERTO
                .addOnSuccessListener(v ->

                        // MOSTRA SUCESSO
                        Toast.makeText(

                                this,

                                "Senha alterada!",

                                Toast.LENGTH_SHORT

                        ).show()
                )

                // SE DER ERRO
                .addOnFailureListener(e ->

                        // MOSTRA ERRO
                        Toast.makeText(

                                this,

                                "Erro: faça login novamente",

                                Toast.LENGTH_SHORT

                        ).show()
                );
    }

    // ======================================================
    // RECUPERAR SENHA
    // ======================================================

    public void recuperarSenha(View view) {

        // ======================================================
        // VERIFICA USUÁRIO
        // ======================================================

        if (

                user == null

                        ||

                        user.getEmail() == null
        ) return;

        // ======================================================
        // ENVIA EMAIL
        // ======================================================

        FirebaseHelper.getAuth()

                // ENVIA EMAIL RECUPERAÇÃO
                .sendPasswordResetEmail(

                        user.getEmail()
                )

                // SE DER CERTO
                .addOnSuccessListener(v ->

                        // MOSTRA MENSAGEM
                        Toast.makeText(

                                this,

                                "Email enviado!",

                                Toast.LENGTH_SHORT

                        ).show()
                );
    }

    // ======================================================
    // EXCLUIR CONTA
    // ======================================================

    public void excluirConta(View view) {

        // ======================================================
        // VERIFICA USUÁRIO
        // ======================================================

        if(user == null) return;

        // ======================================================
        // EXCLUI USUÁRIO
        // ======================================================

        user.delete()

                // SE DER CERTO
                .addOnSuccessListener(v -> {

                    // ==============================================
                    // LIMPA SESSÃO
                    // ==============================================

                    SessionManager.limparSessao(this);

                    // ==============================================
                    // MOSTRA MENSAGEM
                    // ==============================================

                    Toast.makeText(

                            this,

                            "Conta excluída",

                            Toast.LENGTH_SHORT

                    ).show();

                    // ==============================================
                    // VOLTA LOGIN
                    // ==============================================

                    abrirLogin();
                })

                // SE DER ERRO
                .addOnFailureListener(e ->

                        // MOSTRA ERRO
                        Toast.makeText(

                                this,

                                "Erro: faça login novamente",

                                Toast.LENGTH_SHORT

                        ).show()
                );
    }

    // ======================================================
    // LOGOUT
    // ======================================================

    public void logout(View view) {

        // ======================================================
        // DESLOGA FIREBASE
        // ======================================================

        FirebaseHelper.getAuth().signOut();

        // ======================================================
        // LIMPA DADOS LOCAIS
        // ======================================================

        SessionManager.limparSessao(this);

        // ======================================================
        // ABRE LOGIN
        // ======================================================

        abrirLogin();
    }

    // ======================================================
    // ABRIR LOGIN
    // ======================================================

    private void abrirLogin() {

        // ABRE LOGIN
        startActivity(

                new Intent(

                        this,

                        LoginActivity.class
                )
        );

        // FECHA TELA
        finish();
    }
}