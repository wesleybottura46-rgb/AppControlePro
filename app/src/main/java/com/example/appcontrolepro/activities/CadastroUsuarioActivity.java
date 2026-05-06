package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// Tela padrão do Android
import androidx.appcompat.app.AppCompatActivity;

// Importações Android
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Arquivos do projeto
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

// ======================================================
// TELA DE CADASTRO DE USUÁRIO
// ======================================================

// Essa tela cria uma conta no Firebase Authentication.
//
// Depois do cadastro:
// - O usuário vai para a tela de cadastro de time.
// - Assim ele já pode criar o primeiro time.

public class CadastroUsuarioActivity extends AppCompatActivity {

    // ======================================================
    // CAMPOS DA TELA
    // ======================================================

    // Campo do email
    private EditText edtEmail;

    // Campo da senha
    private EditText edtSenha;

    // Botão cadastrar
    private Button btnCadastrar;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Liga o XML nessa tela
        setContentView(R.layout.activity_cadastro_usuario);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        edtEmail =
                findViewById(R.id.edtEmailCadastro);

        edtSenha =
                findViewById(R.id.edtSenhaCadastro);

        btnCadastrar =
                findViewById(R.id.btnCadastrarUsuario);

        // ======================================================
        // CLIQUE DO BOTÃO CADASTRAR
        // ======================================================

        // Quando clicar no botão
        btnCadastrar.setOnClickListener(v -> cadastrar());
    }

    // ======================================================
    // FUNÇÃO CADASTRAR
    // ======================================================

    private void cadastrar() {

        // ======================================================
        // PEGA O TEXTO DIGITADO
        // ======================================================

        String email =

                edtEmail
                        .getText()
                        .toString()
                        .trim();

        String senha =

                edtSenha
                        .getText()
                        .toString()
                        .trim();

        // ======================================================
        // VERIFICA SE ALGUM CAMPO ESTÁ VAZIO
        // ======================================================

        if (email.isEmpty() || senha.isEmpty()) {

            // Mostra mensagem
            Toast.makeText(

                    this,

                    "Preencha todos os campos",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // VERIFICA O TAMANHO DA SENHA
        // ======================================================

        if (senha.length() < 6) {

            // Mostra mensagem
            Toast.makeText(

                    this,

                    "A senha precisa ter pelo menos 6 caracteres",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // FIREBASE CRIA O USUÁRIO
        // ======================================================

        FirebaseHelper.getAuth()

                // Cria conta com email e senha
                .createUserWithEmailAndPassword(
                        email,
                        senha
                )

                // Se deu certo
                .addOnSuccessListener(authResult -> {

                    // ==========================================
                    // MENSAGEM DE SUCESSO
                    // ==========================================

                    Toast.makeText(

                            this,

                            "Cadastro realizado",

                            Toast.LENGTH_SHORT

                    ).show();

                    // ==========================================
                    // ABRE TELA DE CADASTRO DE TIME
                    // ==========================================

                    startActivity(

                            new Intent(

                                    this,

                                    CadastroTimeActivity.class
                            )
                    );

                    // Fecha essa tela
                    finish();
                })

                // Se deu erro
                .addOnFailureListener(e ->

                        Toast.makeText(

                                this,

                                "Erro: " + e.getMessage(),

                                Toast.LENGTH_SHORT

                        ).show()
                );
    }

    // ======================================================
    // VOLTAR PARA LOGIN
    // ======================================================

    public void voltarLogin(View view) {

        // Fecha a tela atual
        finish();
    }
}