package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class JogadoresActivity extends AppCompatActivity {

    // campos da tela
    EditText edtNomeJogador, edtNumeroJogador, edtPosicaoJogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadores);

        // conecta os campos do XML
        edtNomeJogador = findViewById(R.id.edtNomeJogador);
        edtNumeroJogador = findViewById(R.id.edtNumeroJogador);
        edtPosicaoJogador = findViewById(R.id.edtPosicaoJogador);
    }

    // salva jogador no banco
    public void salvarJogador(View view){

        String nome = edtNomeJogador.getText().toString().trim();
        String numeroTexto = edtNumeroJogador.getText().toString().trim();
        String posicao = edtPosicaoJogador.getText().toString().trim();

        if(nome.isEmpty() || numeroTexto.isEmpty() || posicao.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int numero = Integer.parseInt(numeroTexto);

        DatabaseHelper db = new DatabaseHelper(this);

        boolean sucesso = db.cadastrarJogador(nome, numero, posicao);

        if(sucesso){
            Toast.makeText(this, "Jogador cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            edtNomeJogador.setText("");
            edtNumeroJogador.setText("");
            edtPosicaoJogador.setText("");
        }else{
            Toast.makeText(this, "Erro ao cadastrar jogador", Toast.LENGTH_SHORT).show();
        }
    }
}