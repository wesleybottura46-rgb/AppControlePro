package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class CadastroUsuarioActivity extends AppCompatActivity {

    EditText edtNomeCadastro, edtEmailCadastro, edtSenhaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        // conecta os campos
        edtNomeCadastro = findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro);
    }

    // botão continuar
    public void continuarCadastro(View view){

        String nome = edtNomeCadastro.getText().toString().trim();
        String email = edtEmailCadastro.getText().toString().trim();
        String senha = edtSenhaCadastro.getText().toString().trim();

        if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper db = new DatabaseHelper(this);

        boolean sucesso = db.cadastrarUsuario(nome, email, senha);

        if(sucesso){
            Intent tela = new Intent(this, TipoUsuarioActivity.class);
            startActivity(tela);
        }else{
            Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
        }
    }
}