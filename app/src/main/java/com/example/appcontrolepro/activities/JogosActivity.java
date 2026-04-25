package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class JogosActivity extends AppCompatActivity {

    // campos da tela
    EditText edtAdversario, edtDataJogo, edtLocalJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);

        // conecta os campos do XML
        edtAdversario = findViewById(R.id.edtAdversario);
        edtDataJogo = findViewById(R.id.edtData);
        edtLocalJogo = findViewById(R.id.edtLocal);
    }

    // salva jogo
    public void salvarJogo(View view){

        String adversario = edtAdversario.getText().toString().trim();
        String data = edtDataJogo.getText().toString().trim();
        String local = edtLocalJogo.getText().toString().trim();

        DatabaseHelper db = new DatabaseHelper(this);

        boolean sucesso = db.cadastrarJogo(adversario, data, local);

        if(sucesso){
            Toast.makeText(this, "Jogo cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            edtAdversario.setText("");
            edtDataJogo.setText("");
            edtLocalJogo.setText("");
        }else{
            Toast.makeText(this, "Erro ao cadastrar jogo", Toast.LENGTH_SHORT).show();
        }
    }
}