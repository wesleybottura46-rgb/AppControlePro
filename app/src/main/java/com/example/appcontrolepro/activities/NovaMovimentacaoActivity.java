package com.example.appcontrolepro.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcontrolepro.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class NovaMovimentacaoActivity
        extends AppCompatActivity {

    EditText edtDescricao;
    EditText edtValor;

    RadioButton radioEntrada;
    RadioButton radioSaida;

    Button btnSalvarMovimentacao;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_nova_movimentacao
        );

        db = FirebaseFirestore.getInstance();

        edtDescricao =
                findViewById(R.id.edtDescricao);

        edtValor =
                findViewById(R.id.edtValor);

        radioEntrada =
                findViewById(R.id.radioEntrada);

        radioSaida =
                findViewById(R.id.radioSaida);

        btnSalvarMovimentacao =
                findViewById(R.id.btnSalvarMovimentacao);

        btnSalvarMovimentacao
                .setOnClickListener(v -> {

                    salvarMovimentacao();
                });
    }

    // SALVAR MOVIMENTAÇÃO
    private void salvarMovimentacao(){

        String descricao =
                edtDescricao
                        .getText()
                        .toString()
                        .trim();

        String valorTexto =
                edtValor
                        .getText()
                        .toString()
                        .trim();

        if(descricao.isEmpty()){

            Toast.makeText(
                    this,
                    "Digite descrição",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if(valorTexto.isEmpty()){

            Toast.makeText(
                    this,
                    "Digite valor",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double valor =
                Double.parseDouble(valorTexto);

        db.collection("caixa")

                .document("financeiro")

                .get()

                .addOnSuccessListener(document -> {

                    double saldo = 0;
                    double entradas = 0;
                    double saidas = 0;

                    if(document.exists()){

                        Double saldoDb =
                                document.getDouble("saldo");

                        Double entradasDb =
                                document.getDouble("entradas");

                        Double saidasDb =
                                document.getDouble("saidas");

                        if(saldoDb != null){
                            saldo = saldoDb;
                        }

                        if(entradasDb != null){
                            entradas = entradasDb;
                        }

                        if(saidasDb != null){
                            saidas = saidasDb;
                        }
                    }

                    // ENTRADA
                    if(radioEntrada.isChecked()){

                        saldo += valor;
                        entradas += valor;
                    }

                    // SAÍDA
                    else if(radioSaida.isChecked()){

                        saldo -= valor;
                        saidas += valor;
                    }

                    HashMap<String,Object> dados =
                            new HashMap<>();

                    dados.put("saldo", saldo);
                    dados.put("entradas", entradas);
                    dados.put("saidas", saidas);

                    db.collection("caixa")

                            .document("financeiro")

                            .set(
                                    dados,
                                    SetOptions.merge()
                            )

                            .addOnSuccessListener(unused -> {

                                // SALVA HISTÓRICO
                                HashMap<String,Object> movimentacao =
                                        new HashMap<>();

                                movimentacao.put(
                                        "descricao",
                                        descricao
                                );

                                movimentacao.put(
                                        "valor",
                                        valor
                                );

                                movimentacao.put(
                                        "tipo",
                                        radioEntrada.isChecked()
                                                ? "Entrada"
                                                : "Saída"
                                );

                                db.collection("movimentacoes")
                                        .add(movimentacao);

                                Toast.makeText(
                                        this,
                                        "Movimentação salva",
                                        Toast.LENGTH_SHORT
                                ).show();

                                finish();
                            });
                });
    }
}
