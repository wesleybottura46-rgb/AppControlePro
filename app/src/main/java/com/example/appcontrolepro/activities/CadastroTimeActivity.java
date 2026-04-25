package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class CadastroTimeActivity extends AppCompatActivity {

    EditText edtNomeTime, edtCidadeTime, edtEstadoTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_time);

        edtNomeTime = findViewById(R.id.edtNomeTime);
        edtCidadeTime = findViewById(R.id.edtCidadeTime);
        edtEstadoTime = findViewById(R.id.edtEstadoTime);
    }

    public void salvarTime(View view){

        String nome = edtNomeTime.getText().toString().trim();
        String cidade = edtCidadeTime.getText().toString().trim();
        String estado = edtEstadoTime.getText().toString().trim();

        DatabaseHelper db = new DatabaseHelper(this);

        boolean sucesso = db.cadastrarTime(nome, cidade, estado);

        if(sucesso){
            Toast.makeText(this, "Time cadastrado com sucesso", Toast.LENGTH_SHORT).show();

            Intent tela = new Intent(this, MainActivity.class);
            startActivity(tela);
        }else{
            Toast.makeText(this, "Erro ao cadastrar time", Toast.LENGTH_SHORT).show();
        }
    }
}