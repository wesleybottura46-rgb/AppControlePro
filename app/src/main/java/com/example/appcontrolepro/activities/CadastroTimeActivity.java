package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA DE CADASTRO DE TIME
// ======================================================
public class CadastroTimeActivity extends AppCompatActivity {

    // CAMPO DE TEXTO
    EditText edtNomeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_time);

        // LIGA O XML
        edtNomeTime = findViewById(R.id.edtNomeTime);
    }

    // ======================================================
    // BOTÃO SALVAR TIME
    // ======================================================
    public void cadastrarTime(View view){

        String nome = edtNomeTime.getText().toString().trim();

        // VALIDAÇÃO
        if(nome.isEmpty()){
            Toast.makeText(this,"Digite o nome do time",Toast.LENGTH_SHORT).show();
            return;
        }

        // CRIA DADOS
        Map<String,Object> dados = new HashMap<>();
        dados.put("nome", nome);

        // SALVA NO FIREBASE
        FirebaseHelper.getFirestore()
                .collection("times")
                .add(dados)
                .addOnSuccessListener(doc -> {

                    //LIMPA CAMPO
                    edtNomeTime.setText("");

                    // MENSAGEM DE SUCESSO
                    Toast.makeText(this,"Time cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this,"Erro ao cadastrar time",Toast.LENGTH_SHORT).show();
                });
    }
}
