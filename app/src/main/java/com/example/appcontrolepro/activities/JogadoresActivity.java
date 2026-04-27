package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class JogadoresActivity extends AppCompatActivity {

    // Campos da tela
    EditText edtNomeJogador, edtNumeroJogador, edtPosicaoJogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela
        setContentView(R.layout.activity_jogadores);

        // Liga os campos do XML às variáveis Java
        edtNomeJogador = findViewById(R.id.edtNomeJogador);
        edtNumeroJogador = findViewById(R.id.edtNumeroJogador);
        edtPosicaoJogador = findViewById(R.id.edtPosicaoJogador);
    }

    // Método chamado ao clicar no botão salvar
    public void salvarJogador(View view){

        // Captura os valores digitados
        String nome = edtNomeJogador.getText().toString().trim();
        String numeroTexto = edtNumeroJogador.getText().toString().trim();
        String posicao = edtPosicaoJogador.getText().toString().trim();

        // Verifica se os campos foram preenchidos
        if(nome.isEmpty() || numeroTexto.isEmpty() || posicao.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int numero;

        try {
            // Converte o número digitado para inteiro
            numero = Integer.parseInt(numeroTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // Tenta cadastrar o jogador
        boolean sucesso = db.cadastrarJogador(nome, numero, posicao);

        // Fecha o banco
        db.close();

        // Verifica se salvou com sucesso
        if(sucesso){
            Toast.makeText(this, "Jogador cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            // Limpa os campos após salvar
            edtNomeJogador.setText("");
            edtNumeroJogador.setText("");
            edtPosicaoJogador.setText("");
        }else{
            Toast.makeText(this, "Erro ao cadastrar jogador", Toast.LENGTH_SHORT).show();
        }
    }
}