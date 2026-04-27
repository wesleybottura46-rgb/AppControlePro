package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA DE CADASTRO DE JOGADOR
// ======================================================
public class CadastroJogadorActivity extends AppCompatActivity {

    EditText edtNome;
    Spinner spinnerPosicao;

    String timeId;

    // POSIÇÕES
    String[] posicoes = {
            "Goleiro",
            "Zagueiro",
            "Lateral",
            "Meio Campo",
            "Atacante"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);

        // LIGA XML
        edtNome = findViewById(R.id.edtNomeJogador);
        spinnerPosicao = findViewById(R.id.spinnerPosicao);

        // CONFIGURA SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                posicoes
        );
        spinnerPosicao.setAdapter(adapter);

        // PEGA TIME
        timeId = SessionManager.getTimeId(this);
    }

    // ======================================================
    // CADASTRAR JOGADOR
    // ======================================================
    public void cadastrarJogador(View view){

        String nome = edtNome.getText().toString().trim();
        String posicao = spinnerPosicao.getSelectedItem().toString();

        if(nome.isEmpty()){
            Toast.makeText(this,"Digite o nome",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,Object> dados = new HashMap<>();
        dados.put("nome", nome);
        dados.put("posicao", posicao);
        dados.put("timeId", timeId);
        dados.put("gols", 0);
        dados.put("assistencias", 0);

        FirebaseHelper.getFirestore()
                .collection("jogadores")
                .add(dados)
                .addOnSuccessListener(doc -> {

                    // 🔥 LIMPA CAMPOS
                    edtNome.setText("");
                    spinnerPosicao.setSelection(0);

                    // 🔥 MENSAGEM
                    Toast.makeText(this,"Jogador cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this,"Erro ao cadastrar jogador",Toast.LENGTH_SHORT).show();
                });
    }
}