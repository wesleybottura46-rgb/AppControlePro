package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// Importa troca de telas
import android.content.Intent;

// Importações Android
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

// Tela padrão do Android
import androidx.appcompat.app.AppCompatActivity;

// Arquivos do projeto
import com.example.appcontrolepro.R;

// Firebase
import com.google.firebase.firestore.FirebaseFirestore;

// ======================================================
// TELA FINANCEIRO
// ======================================================

public class FinanceiroActivity
        extends AppCompatActivity {

    // ======================================================
    // TEXTOS DA TELA
    // ======================================================

    // Texto do valor arrecadado
    TextView txtArrecadado;

    // Texto das pendências
    TextView txtPendentes;

    // Texto do caixa atual
    TextView txtCaixa;

    // ======================================================
    // BOTÕES
    // ======================================================

    // Botão mensalidades
    Button btnMensalidades;

    // Botão caixa do time
    Button btnCaixa;

    // Botão relatórios
    Button btnRelatorios;

    // Botão configurações
    Button btnConfiguracoes;

    // ======================================================
    // FIREBASE
    // ======================================================

    // Conexão com Firestore
    FirebaseFirestore db;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Liga XML nessa tela
        setContentView(R.layout.activity_financeiro);

        // ======================================================
        // INICIA FIREBASE
        // ======================================================

        db =
                FirebaseFirestore.getInstance();

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        txtArrecadado =
                findViewById(R.id.txtArrecadado);

        txtPendentes =
                findViewById(R.id.txtPendentes);

        txtCaixa =
                findViewById(R.id.txtCaixa);

        btnMensalidades =
                findViewById(R.id.btnMensalidades);

        btnCaixa =
                findViewById(R.id.btnCaixa);

        btnRelatorios =
                findViewById(R.id.btnRelatorios);

        btnConfiguracoes =
                findViewById(R.id.btnConfiguracoes);

        // ======================================================
        // BOTÃO MENSALIDADES
        // ======================================================

        // Quando clicar no botão
        btnMensalidades.setOnClickListener(v -> {

            // Abre a tela de mensalidades
            startActivity(

                    new Intent(

                            FinanceiroActivity.this,

                            MensalidadesActivity.class
                    )
            );
        });

        // ======================================================
        // BOTÃO CAIXA
        // ======================================================

        // Quando clicar no botão
        btnCaixa.setOnClickListener(v -> {

            // Abre a tela caixa
            startActivity(

                    new Intent(

                            FinanceiroActivity.this,

                            CaixaActivity.class
                    )
            );
        });

        // ======================================================
        // BOTÃO RELATÓRIOS
        // ======================================================

        // Quando clicar no botão
        btnRelatorios.setOnClickListener(v -> {

            // Abre a tela relatórios
            startActivity(

                    new Intent(

                            FinanceiroActivity.this,

                            RelatoriosActivity.class
                    )
            );
        });

        // ======================================================
        // BOTÃO CONFIGURAÇÕES
        // ======================================================

        // Quando clicar no botão
        btnConfiguracoes.setOnClickListener(v -> {

            // Abre a tela de configurações financeiras
            startActivity(

                    new Intent(

                            FinanceiroActivity.this,

                            ConfiguracaoFinanceiroActivity.class
                    )
            );
        });

        // ======================================================
        // ATUALIZA OS DADOS FINANCEIROS
        // ======================================================

        atualizarFinanceiro();
    }

    // ======================================================
    // ATUALIZAR FINANCEIRO
    // ======================================================

    private void atualizarFinanceiro(){

        // Escuta mudanças no Firestore
        db.collection("caixa")

                // Documento financeiro
                .document("financeiro")

                // Atualização em tempo real
                .addSnapshotListener((value, error) -> {

                    // ======================================================
                    // SE O DOCUMENTO FOR NULO
                    // ======================================================

                    if(value == null) return;

                    // ======================================================
                    // PEGA O SALDO
                    // ======================================================

                    Double saldo =
                            value.getDouble("saldo");

                    // ======================================================
                    // PEGA AS ENTRADAS
                    // ======================================================

                    Double entradas =
                            value.getDouble("entradas");

                    // ======================================================
                    // PEGA AS SAÍDAS
                    // ======================================================

                    Double saidas =
                            value.getDouble("saidas");

                    // ======================================================
                    // SE ALGUM VALOR FOR NULO
                    // ======================================================

                    // Se saldo estiver vazio
                    if(saldo == null){

                        saldo = 0.0;
                    }

                    // Se entradas estiver vazio
                    if(entradas == null){

                        entradas = 0.0;
                    }

                    // Se saídas estiver vazio
                    if(saidas == null){

                        saidas = 0.0;
                    }

                    // ======================================================
                    // MOSTRA VALOR ARRECADADO
                    // ======================================================

                    txtArrecadado.setText(
                            "R$ " + entradas
                    );

                    // ======================================================
                    // MOSTRA VALOR DO CAIXA
                    // ======================================================

                    txtCaixa.setText(
                            "R$ " + saldo
                    );

                    // ======================================================
                    // MOSTRA SAÍDAS
                    // ======================================================

                    txtPendentes.setText(
                            "Saídas: R$ " + saidas
                    );
                });
    }
}