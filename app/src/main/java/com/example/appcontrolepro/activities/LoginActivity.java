package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

// ==============================
// LOGIN
// ==============================
public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private CheckBox checkManterLogado;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmailLogin);
        edtSenha = findViewById(R.id.edtSenhaLogin);
        checkManterLogado = findViewById(R.id.checkManterLogado);

        prefs = getSharedPreferences("login", MODE_PRIVATE);

        boolean manter = prefs.getBoolean("manter", false);

        // 🔥 CORREÇÃO PRINCIPAL
        if (!manter) {
            //se NÃO quer manter logado → desloga do Firebase
            FirebaseHelper.getAuth().signOut();
        }

        // só entra automático se marcou manter logado
        if (manter && FirebaseHelper.getAuth().getCurrentUser() != null) {
            abrirEscolherTime();
        }
    }

    // ==========================
    // LOGIN
    // ==========================
    public void fazerLogin(View view) {

        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseHelper.getAuth()
                .signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener(auth -> {

                    // salva escolha do usuário
                    prefs.edit()
                            .putBoolean("manter", checkManterLogado.isChecked())
                            .apply();

                    abrirEscolherTime();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    // ==========================
    // RECUPERAR SENHA
    // ==========================
    public void recuperarSenha(View view) {

        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Digite seu email", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseHelper.getAuth()
                .sendPasswordResetEmail(email)
                .addOnSuccessListener(v ->
                        Toast.makeText(this, "Email enviado!", Toast.LENGTH_LONG).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    // ==========================
    // ABRIR CADASTRO
    // ==========================
    public void abrirCadastro(View view) {
        startActivity(new Intent(this, CadastroUsuarioActivity.class));
    }

    // ==========================
    // IR PARA ESCOLHER TIME
    // ==========================
    private void abrirEscolherTime() {
        startActivity(new Intent(this, EscolherTimeActivity.class));
        finish();
    }
}
