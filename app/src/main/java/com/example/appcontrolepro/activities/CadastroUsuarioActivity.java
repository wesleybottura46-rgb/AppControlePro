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

    // Campos da tela de cadastro
    EditText edtNomeCadastro, edtEmailCadastro, edtSenhaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela
        setContentView(R.layout.activity_cadastro_usuario);

        // Liga os campos do XML às variáveis Java
        edtNomeCadastro = findViewById(R.id.edtNomeCadastro);
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro);
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro);
    }

    // Método chamado ao clicar no botão "Continuar"
    public void continuarCadastro(View view){

        // Captura os dados digitados pelo usuário
        String nome = edtNomeCadastro.getText().toString().trim();
        String email = edtEmailCadastro.getText().toString().trim();
        String senha = edtSenhaCadastro.getText().toString().trim();

        // Verifica se algum campo está vazio
        if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // Tenta cadastrar o usuário
        boolean sucesso = db.cadastrarUsuario(nome, email, senha);

        // Fecha a conexão com o banco
        db.close();

        // Se cadastrar com sucesso, abre a próxima tela
        if(sucesso){
            Intent tela = new Intent(this, TipoUsuarioActivity.class);
            startActivity(tela);

            // Fecha a tela atual
            finish();
        }else{
            Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
        }
    }
}