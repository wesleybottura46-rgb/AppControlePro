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

    Spinner spinnerTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_time);

        spinnerTimes = findViewById(R.id.spinnerTimes);

        carregarTimes();
    }

    private void carregarTimes(){

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.listarTimes();

        ArrayList<String> listaTimes = new ArrayList<>();

        while(cursor.moveToNext()){
            listaTimes.add(cursor.getString(1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaTimes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimes.setAdapter(adapter);
    }
}