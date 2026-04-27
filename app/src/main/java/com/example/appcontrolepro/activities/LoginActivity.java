package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    // Campos da tela
    EditText edtEmail, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela
        setContentView(R.layout.activity_login);

        // Liga os campos do XML às variáveis Java
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
    }

    // Método chamado ao clicar no botão "Entrar"
    public void fazerLogin(View view){

        // Captura os dados digitados
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        // Verifica se os campos estão vazios
        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // Verifica se o login existe
        boolean loginValido = db.validarLogin(email, senha);

        // Fecha o banco
        db.close();

        if(loginValido){
            // Abre a próxima tela
            Intent tela = new Intent(this, MainActivity.class);
            startActivity(tela);

            // Fecha a tela de login
            finish();
        }else{
            Toast.makeText(this, "Login inválido", Toast.LENGTH_SHORT).show();
        }
    }

    // Método chamado ao clicar no botão "Criar conta"
    public void abrirCadastro(View view){
        Intent tela = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(tela);
    }
}