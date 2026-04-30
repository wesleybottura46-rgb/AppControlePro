package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA DE CADASTRO DE JOGO
// ======================================================
public class CadastroJogoActivity extends AppCompatActivity {

    EditText edtAdversario, edtData, edtHora;

    String timeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogo);

        // LIGA XML
        edtAdversario = findViewById(R.id.edtAdversario);
        edtData = findViewById(R.id.edtData);
        edtHora = findViewById(R.id.edtHora);

        timeId = SessionManager.getTimeId(this);

        configurarData();
        configurarHora();
    }

    // DATA
    private void configurarData(){
        edtData.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();

            new DatePickerDialog(this,
                    (view, y, m, d) -> edtData.setText(d + "/" + (m+1) + "/" + y),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    // HORA
    private void configurarHora(){
        edtHora.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();

            new TimePickerDialog(this,
                    (view, h, min) -> edtHora.setText(String.format("%02d:%02d", h, min)),
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
            ).show();
        });
    }

    // ======================================================
    // CADASTRAR JOGO
    // ======================================================
    public void cadastrarJogo(View view){

        String adversario = edtAdversario.getText().toString().trim();
        String data = edtData.getText().toString().trim();
        String hora = edtHora.getText().toString().trim();

        if(adversario.isEmpty() || data.isEmpty() || hora.isEmpty()){
            Toast.makeText(this,"Preencha todos os campos",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,Object> dados = new HashMap<>();
        dados.put("adversario", adversario);
        dados.put("data", data);
        dados.put("hora", hora);
        dados.put("timeId", timeId);

        FirebaseHelper.getFirestore()
                .collection("jogos")
                .add(dados)
                .addOnSuccessListener(doc -> {

                    // LIMPA CAMPOS
                    edtAdversario.setText("");
                    edtData.setText("");
                    edtHora.setText("");

                    // MENSAGEM
                    Toast.makeText(this,"Jogo cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this,"Erro ao cadastrar jogo",Toast.LENGTH_SHORT).show();
                });
    }
}
