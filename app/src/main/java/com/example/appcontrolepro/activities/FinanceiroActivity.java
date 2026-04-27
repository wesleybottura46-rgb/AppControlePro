package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.DatabaseHelper;

public class FinanceiroActivity extends AppCompatActivity {

    // Campos da tela:
    // descrição e valor são EditText
    // tipo é Spinner
    EditText edtDescricao, edtValor;
    Spinner edtTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela
        setContentView(R.layout.activity_financeiro);

        // Liga os componentes do XML às variáveis Java
        edtDescricao = findViewById(R.id.edtDescricao);
        edtValor = findViewById(R.id.edtValor);
        edtTipo = findViewById(R.id.edtTipo);
    }

    // Método chamado ao clicar no botão salvar
    public void salvarLancamento(View view){

        // Captura os valores digitados nos campos
        String descricao = edtDescricao.getText().toString().trim();
        String valorTexto = edtValor.getText().toString().trim();

        // Captura o item selecionado no Spinner
        String tipo = edtTipo.getSelectedItem().toString();

        // Verifica se descrição ou valor estão vazios
        if(descricao.isEmpty() || valorTexto.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;

        try {
            // Converte o valor digitado para número decimal
            valor = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria conexão com o banco
        DatabaseHelper db = new DatabaseHelper(this);

        // Salva o lançamento financeiro no banco
        boolean sucesso = db.salvarLancamento(descricao, valor, tipo);

        // Fecha o banco após salvar
        db.close();

        // Verifica se salvou com sucesso
        if(sucesso){
            Toast.makeText(this, "Lançamento salvo com sucesso", Toast.LENGTH_SHORT).show();

            // Limpa os campos
            edtDescricao.setText("");
            edtValor.setText("");

        }else{
            Toast.makeText(this, "Erro ao salvar lançamento", Toast.LENGTH_SHORT).show();
        }
    }
}