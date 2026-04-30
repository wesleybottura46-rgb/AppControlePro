package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;
import com.google.firebase.auth.FirebaseUser;

// ==============================
// PERFIL / CONFIGURAÇÕES
// ==============================
public class PerfilActivity extends AppCompatActivity {

    private EditText edtNovaSenha;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        edtNovaSenha = findViewById(R.id.edtNovaSenha);

        // PEGA USUÁRIO LOGADO
        user = FirebaseHelper.getAuth().getCurrentUser();

        if(user == null){
            abrirLogin();
        }
    }

    // ==========================
    // ALTERAR SENHA
    // ==========================
    public void alterarSenha(View view) {

        String novaSenha = edtNovaSenha.getText().toString().trim();

        if (novaSenha.length() < 6) {
            Toast.makeText(this, "Senha mínima 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        user.updatePassword(novaSenha)
                .addOnSuccessListener(v ->
                        Toast.makeText(this, "Senha alterada!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro: faça login novamente", Toast.LENGTH_SHORT).show()
                );
    }

    // ==========================
    // RECUPERAR SENHA
    // ==========================
    public void recuperarSenha(View view) {

        if (user == null || user.getEmail() == null) return;

        FirebaseHelper.getAuth()
                .sendPasswordResetEmail(user.getEmail())
                .addOnSuccessListener(v ->
                        Toast.makeText(this, "Email enviado!", Toast.LENGTH_SHORT).show()
                );
    }

    // ==========================
    // EXCLUIR CONTA
    // ==========================
    public void excluirConta(View view) {

        if(user == null) return;

        user.delete()
                .addOnSuccessListener(v -> {

                    // LIMPA SESSÃO CORRETAMENTE
                    SessionManager.limparSessao(this);

                    Toast.makeText(this, "Conta excluída", Toast.LENGTH_SHORT).show();

                    abrirLogin();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Erro: faça login novamente", Toast.LENGTH_SHORT).show()
                );
    }

    // ==========================
    // LOGOUT
    // =========================
    public void logout(View view) {

        FirebaseHelper.getAuth().signOut();

        // LIMPA DADOS LOCAIS
        SessionManager.limparSessao(this);

        abrirLogin();
    }

    // ==========================
    // VOLTAR LOGIN
    // ==========================
    private void abrirLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
