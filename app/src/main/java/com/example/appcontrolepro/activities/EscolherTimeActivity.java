package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

import java.util.ArrayList;

public class EscolherTimeActivity extends AppCompatActivity {

    // Spinner que exibirá a lista de times
    Spinner spinnerTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela
        setContentView(R.layout.activity_escolher_time);

        // Liga o spinner do XML à variável Java
        spinnerTimes = findViewById(R.id.spinnerTimes);

        // Carrega os times cadastrados no banco
        carregarTimes();
    }

    // Método para buscar os times no banco e mostrar no spinner
    private void carregarTimes(){

        // Abre conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // Busca todos os times cadastrados
        Cursor cursor = db.listarTimes();

        // Lista que armazenará os nomes dos times
        ArrayList<String> listaTimes = new ArrayList<>();

        // Percorre o resultado da consulta
        while(cursor.moveToNext()){
            listaTimes.add(cursor.getString(1));
        }

        // Fecha o cursor após uso
        cursor.close();

        // Fecha o banco após uso
        db.close();

        // Adapter para mostrar os dados no spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaTimes
        );

        // Layout do dropdown do spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Define o adapter no spinner
        spinnerTimes.setAdapter(adapter);
    }
}