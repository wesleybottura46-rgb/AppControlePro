package com.example.appcontrolepro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.models.Movimentacao;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CaixaActivity
        extends AppCompatActivity {

    // TEXTOS
    TextView txtSaldo;
    TextView txtEntradas;
    TextView txtSaidas;

    // BOTÃO
    Button btnNovaMovimentacao;

    // RECYCLER
    RecyclerView recyclerMovimentacoes;

    // LISTA
    List<Movimentacao> listaMovimentacoes =
            new ArrayList<>();

    // ADAPTER
    MovimentacaoAdapter adapter;

    // FIREBASE
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_caixa);

        // FIREBASE
        db = FirebaseFirestore.getInstance();

        // TEXTOS
        txtSaldo =
                findViewById(R.id.txtSaldo);

        txtEntradas =
                findViewById(R.id.txtEntradas);

        txtSaidas =
                findViewById(R.id.txtSaidas);

        // BOTÃO
        btnNovaMovimentacao =
                findViewById(R.id.btnNovaMovimentacao);

        // RECYCLER
        recyclerMovimentacoes =
                findViewById(R.id.recyclerMovimentacoes);

        recyclerMovimentacoes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // ADAPTER
        adapter = new MovimentacaoAdapter(
                listaMovimentacoes
        );

        recyclerMovimentacoes.setAdapter(adapter);

        // BOTÃO NOVA MOVIMENTAÇÃO
        btnNovaMovimentacao.setOnClickListener(v -> {

            startActivity(

                    new Intent(

                            CaixaActivity.this,

                            NovaMovimentacaoActivity.class
                    )
            );
        });

        // ATUALIZA FINANCEIRO
        atualizarFinanceiro();

        // CARREGA MOVIMENTAÇÕES
        carregarMovimentacoes();
    }

    // ==========================================
    // ATUALIZA FINANCEIRO
    // ==========================================

    private void atualizarFinanceiro(){

        db.collection("caixa")

                .document("financeiro")

                .addSnapshotListener((value, error) -> {

                    if(value == null) return;

                    Double saldo =
                            value.getDouble("saldo");

                    Double entradas =
                            value.getDouble("entradas");

                    Double saidas =
                            value.getDouble("saidas");

                    if(saldo == null){
                        saldo = 0.0;
                    }

                    if(entradas == null){
                        entradas = 0.0;
                    }

                    if(saidas == null){
                        saidas = 0.0;
                    }

                    txtSaldo.setText(
                            "R$ " + saldo
                    );

                    txtEntradas.setText(
                            "R$ " + entradas
                    );

                    txtSaidas.setText(
                            "R$ " + saidas
                    );
                });
    }

    // ==========================================
    // CARREGAR MOVIMENTAÇÕES
    // ==========================================

    private void carregarMovimentacoes(){

        db.collection("movimentacoes")

                .addSnapshotListener((value, error) -> {

                    if(value == null) return;

                    listaMovimentacoes.clear();

                    for(var doc : value){

                        String descricao =
                                doc.getString("descricao");

                        String tipo =
                                doc.getString("tipo");

                        Double valor =
                                doc.getDouble("valor");

                        if(valor == null){
                            valor = 0.0;
                        }

                        listaMovimentacoes.add(

                                new Movimentacao(

                                        doc.getId(),

                                        descricao,

                                        valor,

                                        tipo
                                )
                        );
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}
