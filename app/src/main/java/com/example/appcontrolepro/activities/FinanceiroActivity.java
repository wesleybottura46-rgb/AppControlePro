package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class FinanceiroActivity extends AppCompatActivity {

    // campos da tela
    EditText edtDescricao, edtValor, edtTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);

        // conecta os campos do XML
        edtDescricao = findViewById(R.id.edtDescricao);
        edtValor = findViewById(R.id.edtValor);
        edtTipo = findViewById(R.id.edtTipo);
    }

    // salva lançamento financeiro
    public void salvarLancamento(View view){

        String descricao = edtDescricao.getText().toString().trim();
        String valorTexto = edtValor.getText().toString().trim();
        String tipo = edtTipo.getText().toString().trim();

        if(descricao.isEmpty() || valorTexto.isEmpty() || tipo.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorTexto);

        DatabaseHelper db = new DatabaseHelper(this);

        boolean sucesso = db.salvarLancamento(descricao, valor, tipo);

        if(sucesso){
            Toast.makeText(this, "Lançamento salvo com sucesso", Toast.LENGTH_SHORT).show();

            edtDescricao.setText("");
            edtValor.setText("");
            edtTipo.setText("");
        }else{
            Toast.makeText(this, "Erro ao salvar lançamento", Toast.LENGTH_SHORT).show();
        }
    }
}