package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class JogosActivity extends AppCompatActivity {

    // Campos da tela
    EditText edtAdversario, edtDataJogo, edtLocalJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela
        setContentView(R.layout.activity_jogos);

        // Liga os campos do XML às variáveis Java
        edtAdversario = findViewById(R.id.edtAdversario);
        edtDataJogo = findViewById(R.id.edtData);
        edtLocalJogo = findViewById(R.id.edtLocal);
    }

    // Método chamado ao clicar no botão salvar
    public void salvarJogo(View view){

        // Captura os dados digitados
        String adversario = edtAdversario.getText().toString().trim();
        String data = edtDataJogo.getText().toString().trim();
        String local = edtLocalJogo.getText().toString().trim();

        // Verifica se todos os campos foram preenchidos
        if(adversario.isEmpty() || data.isEmpty() || local.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // Salva o jogo no banco
        boolean sucesso = db.cadastrarJogo(adversario, data, local);

        // Fecha o banco
        db.close();

        // Verifica se salvou com sucesso
        if(sucesso){
            Toast.makeText(this, "Jogo cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            // Limpa os campos após salvar
            edtAdversario.setText("");
            edtDataJogo.setText("");
            edtLocalJogo.setText("");
        }else{
            Toast.makeText(this, "Erro ao cadastrar jogo", Toast.LENGTH_SHORT).show();
        }
    }
}