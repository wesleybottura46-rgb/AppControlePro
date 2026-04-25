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

    // campos da tela
    EditText edtEmail, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // conecta os campos do XML com o Java
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
    }

    // método chamado ao clicar no botão entrar
    public void fazerLogin(View view){

        // pega o texto digitado
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        // cria conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // verifica se o login existe
        boolean loginValido = db.validarLogin(email, senha);

        if(loginValido){
            // abre a próxima tela
            Intent tela = new Intent(this, MainActivity.class);
            startActivity(tela);
        }else{
            Toast.makeText(this, "Login inválido", Toast.LENGTH_SHORT).show();
        }
    }

    // abre tela de cadastro
    public void abrirCadastro(View view){
        Intent tela = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(tela);
    }
}