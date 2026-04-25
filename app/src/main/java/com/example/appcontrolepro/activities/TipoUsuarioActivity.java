package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appcontrolepro.R;

public class TipoUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_usuario);
    }

    // abre cadastro de time
    public void abrirCadastroTime(View view){
        Intent tela = new Intent(this, CadastroTimeActivity.class);
        startActivity(tela);
    }

    // abre escolher time
    public void abrirEscolherTime(View view){
        Intent tela = new Intent(this, EscolherTimeActivity.class);
        startActivity(tela);
    }
}