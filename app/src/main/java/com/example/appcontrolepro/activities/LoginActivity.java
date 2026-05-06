// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA TELA PADRÃO
import androidx.appcompat.app.AppCompatActivity;

// IMPORTA TROCA DE TELAS
import android.content.Intent;

// IMPORTA SALVAMENTO LOCAL
import android.content.SharedPreferences;

// IMPORTA CICLO DA TELA
import android.os.Bundle;

// IMPORTA VIEW
import android.view.View;

// IMPORTA COMPONENTES VISUAIS
import android.widget.*;

// IMPORTA R DO PROJETO
import com.example.appcontrolepro.R;

// IMPORTA FIREBASE HELPER
import com.example.appcontrolepro.database.FirebaseHelper;

// ======================================================
// TELA LOGIN
// ======================================================
//
// ESSA TELA:
//
// ✔ FAZ LOGIN
// ✔ RECUPERA SENHA
// ✔ ABRE CADASTRO
// ✔ MANTÉM LOGIN SALVO
// ✔ ENTRA AUTOMATICAMENTE
//
// ======================================================

// CRIA CLASSE LOGIN
public class LoginActivity extends AppCompatActivity {

    // ======================================================
    // CAMPOS DA TELA
    // ======================================================

    // CAMPO EMAIL
    private EditText edtEmail;

    // CAMPO SENHA
    private EditText edtSenha;

    // CHECKBOX MANTER LOGADO
    private CheckBox checkManterLogado;

    // ======================================================
    // SHARED PREFERENCES
    // ======================================================

    // SALVA DADOS NO CELULAR
    private SharedPreferences prefs;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // CHAMA MÉTODO PAI
        super.onCreate(savedInstanceState);

        // LIGA XML NESSA TELA
        setContentView(R.layout.activity_login);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        // CAMPO EMAIL
        edtEmail =
                findViewById(R.id.edtEmailLogin);

        // CAMPO SENHA
        edtSenha =
                findViewById(R.id.edtSenhaLogin);

        // CHECKBOX
        checkManterLogado =
                findViewById(R.id.checkManterLogado);

        // ======================================================
        // CRIA SHARED PREFERENCES
        // ======================================================

        prefs = getSharedPreferences(

                // NOME DO ARQUIVO
                "login",

                // MODO PRIVADO
                MODE_PRIVATE
        );

        // ======================================================
        // PEGA VALOR SALVO
        // ======================================================

        // VERIFICA SE O USUÁRIO MARCOU
        boolean manter =
                prefs.getBoolean(

                        // NOME DA CHAVE
                        "manter",

                        // VALOR PADRÃO
                        false
                );

        // ======================================================
        // VERIFICA SE NÃO QUER MANTER LOGADO
        // ======================================================

        if (!manter) {

            // DESLOGA DO FIREBASE
            FirebaseHelper
                    .getAuth()
                    .signOut();
        }

        // ======================================================
        // LOGIN AUTOMÁTICO
        // ======================================================

        // SE MARCOU MANTER LOGADO
        // E EXISTE USUÁRIO
        if (

                manter

                        &&

                        FirebaseHelper
                                .getAuth()
                                .getCurrentUser() != null
        ) {

            // ABRE ESCOLHER TIME
            abrirEscolherTime();
        }
    }

    // ======================================================
    // FAZER LOGIN
    // ======================================================

    public void fazerLogin(View view) {

        // ======================================================
        // PEGA EMAIL
        // ======================================================

        String email =

                edtEmail
                        .getText()
                        .toString()
                        .trim();

        // ======================================================
        // PEGA SENHA
        // ======================================================

        String senha =

                edtSenha
                        .getText()
                        .toString()
                        .trim();

        // ======================================================
        // VERIFICA CAMPOS VAZIOS
        // ======================================================

        if (

                email.isEmpty()

                        ||

                        senha.isEmpty()
        ) {

            // MOSTRA MENSAGEM
            Toast.makeText(

                    this,

                    "Preencha email e senha",

                    Toast.LENGTH_SHORT

            ).show();

            // PARA EXECUÇÃO
            return;
        }

        // ======================================================
        // LOGIN FIREBASE
        // ======================================================

        FirebaseHelper.getAuth()

                // FAZ LOGIN
                .signInWithEmailAndPassword(

                        email,

                        senha
                )

                // SE DEU CERTO
                .addOnSuccessListener(auth -> {

                    // ==========================================
                    // SALVA ESCOLHA DO CHECKBOX
                    // ==========================================

                    prefs.edit()

                            // SALVA TRUE OU FALSE
                            .putBoolean(

                                    "manter",

                                    checkManterLogado.isChecked()
                            )

                            // SALVA
                            .apply();

                    // ==========================================
                    // ABRE ESCOLHER TIME
                    // ==========================================

                    abrirEscolherTime();
                })

                // SE DEU ERRO
                .addOnFailureListener(e ->

                        // MOSTRA ERRO
                        Toast.makeText(

                                this,

                                "Erro: " + e.getMessage(),

                                Toast.LENGTH_SHORT

                        ).show()
                );
    }

    // ======================================================
    // RECUPERAR SENHA
    // ======================================================

    public void recuperarSenha(View view) {

        // ======================================================
        // PEGA EMAIL
        // ======================================================

        String email =

                edtEmail
                        .getText()
                        .toString()
                        .trim();

        // ======================================================
        // VERIFICA EMAIL VAZIO
        // ======================================================

        if (email.isEmpty()) {

            // MOSTRA MENSAGEM
            Toast.makeText(

                    this,

                    "Digite seu email",

                    Toast.LENGTH_SHORT

            ).show();

            // PARA EXECUÇÃO
            return;
        }

        // ======================================================
        // ENVIA EMAIL DE RECUPERAÇÃO
        // ======================================================

        FirebaseHelper.getAuth()

                // ENVIA EMAIL
                .sendPasswordResetEmail(email)

                // SE DEU CERTO
                .addOnSuccessListener(v ->

                        // MOSTRA MENSAGEM
                        Toast.makeText(

                                this,

                                "Email enviado!",

                                Toast.LENGTH_LONG

                        ).show()
                )

                // SE DEU ERRO
                .addOnFailureListener(e ->

                        // MOSTRA ERRO
                        Toast.makeText(

                                this,

                                "Erro: " + e.getMessage(),

                                Toast.LENGTH_SHORT

                        ).show()
                );
    }

    // ======================================================
    // ABRIR CADASTRO
    // ======================================================

    public void abrirCadastro(View view) {

        // ABRE TELA CADASTRO
        startActivity(

                new Intent(

                        this,

                        CadastroUsuarioActivity.class
                )
        );
    }

    // ======================================================
    // ABRIR ESCOLHER TIME
    // ======================================================

    private void abrirEscolherTime() {

        // ABRE TELA
        startActivity(

                new Intent(

                        this,

                        EscolherTimeActivity.class
                )
        );

        // FECHA LOGIN
        finish();
    }
}