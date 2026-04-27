package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

// =========================================================
// CADASTRO USUARIO ACTIVITY
// =========================================================
// Tela responsavel por criar uma conta no Firebase Authentication.
//
// Depois do cadastro:
// - O usuario e enviado para CadastroTimeActivity.
// - Assim ele ja cria o primeiro time.
public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtEmail = findViewById(R.id.edtEmailCadastro);
        edtSenha = findViewById(R.id.edtSenhaCadastro);
        btnCadastrar = findViewById(R.id.btnCadastrarUsuario);

        // Aqui usamos setOnClickListener em vez de android:onClick no XML.
        btnCadastrar.setOnClickListener(v -> cadastrar());
    }

    // Valida email/senha e cria a conta no Firebase.
    private void cadastrar() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.length() < 6) {
            Toast.makeText(this, "A senha precisa ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase cria o usuario com email e senha.
        FirebaseHelper.getAuth()
                .createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Cadastro realizado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, CadastroTimeActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    // Botao para voltar para a tela de login.
    public void voltarLogin(View view) {
        finish();
    }
}
