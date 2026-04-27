package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditarJogoActivity extends AppCompatActivity {

    EditText edtAdversario, edtData, edtHora, edtLocal;

    String jogoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_jogo);

        // ==========================
        // LIGA XML
        // ==========================
        edtAdversario = findViewById(R.id.edtAdversario);
        edtData = findViewById(R.id.edtData);
        edtHora = findViewById(R.id.edtHora);
        edtLocal = findViewById(R.id.edtLocal);

        // ==========================
        // PEGA ID DO JOGO
        // ==========================
        jogoId = getIntent().getStringExtra("jogoId");

        if(jogoId == null){
            finish();
            return;
        }

        // CARREGA DADOS DO JOGO
        carregarJogo();

        // CALENDÁRIO
        edtData.setOnClickListener(v -> abrirCalendario());

        // RELÓGIO
        edtHora.setOnClickListener(v -> abrirRelogio());
    }

    // ==========================
    // CARREGAR JOGO
    // ==========================
    private void carregarJogo(){

        FirebaseHelper.getFirestore()
                .collection("jogos")
                .document(jogoId)
                .get()
                .addOnSuccessListener(doc -> {

                    if(doc != null){

                        edtAdversario.setText(doc.getString("adversario"));
                        edtData.setText(doc.getString("data"));
                        edtHora.setText(doc.getString("hora"));
                        edtLocal.setText(doc.getString("local"));
                    }
                });
    }

    // ==========================
    // CALENDÁRIO
    // ==========================
    private void abrirCalendario(){

        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this,
                (view, y, m, d) ->
                        edtData.setText(d + "/" + (m+1) + "/" + y),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    // ==========================
    // RELÓGIO
    // ==========================
    private void abrirRelogio(){

        Calendar c = Calendar.getInstance();

        new TimePickerDialog(this,
                (view, h, min) ->
                        edtHora.setText(String.format("%02d:%02d", h, min)),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
        ).show();
    }

    // ==========================
    // SALVAR ALTERAÇÃO
    // ==========================
    public void salvarEdicao(View view){

        Map<String,Object> dados = new HashMap<>();

        dados.put("adversario", edtAdversario.getText().toString());
        dados.put("data", edtData.getText().toString());
        dados.put("hora", edtHora.getText().toString());
        dados.put("local", edtLocal.getText().toString());

        FirebaseHelper.getFirestore()
                .collection("jogos")
                .document(jogoId)
                .update(dados)
                .addOnSuccessListener(v -> {

                    Toast.makeText(this,"Jogo atualizado",Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}