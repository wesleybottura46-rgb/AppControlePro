// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA CICLO DA TELA
import android.os.Bundle;

// IMPORTA BOTÃO
import android.widget.Button;

// IMPORTA TEXTO
import android.widget.TextView;

// IMPORTA TOAST
import android.widget.Toast;

// IMPORTA TELA PADRÃO
import androidx.appcompat.app.AppCompatActivity;

// IMPORTA R
import com.example.appcontrolepro.R;

// IMPORTA FIREBASE
import com.google.firebase.firestore.FirebaseFirestore;

// ======================================================
// TELA RELATÓRIOS
// ======================================================
//
// ESSA TELA:
//
// ✔ MOSTRA ARRECADAÇÃO
// ✔ MOSTRA DESPESAS
// ✔ MOSTRA SALDO
// ✔ ATUALIZA EM TEMPO REAL
// ✔ BOTÃO EXPORTAR PDF
//
// ======================================================

// CRIA CLASSE
public class RelatoriosActivity

        // HERDA DA ACTIVITY
        extends AppCompatActivity {

    // ======================================================
    // TEXTOS
    // ======================================================

    // TEXTO ARRECADAÇÃO
    TextView txtArrecadacaoMes;

    // TEXTO DESPESAS
    TextView txtDespesasMes;

    // TEXTO SALDO
    TextView txtSaldoAtual;

    // ======================================================
    // BOTÃO
    // ======================================================

    // BOTÃO EXPORTAR
    Button btnExportar;

    // ======================================================
    // FIREBASE
    // ======================================================

    // CONEXÃO FIREBASE
    FirebaseFirestore db;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // CHAMA MÉTODO PAI
        super.onCreate(savedInstanceState);

        // ======================================================
        // DEFINE XML
        // ======================================================

        setContentView(R.layout.activity_relatorios);

        // ======================================================
        // INICIA FIREBASE
        // ======================================================

        db =
                FirebaseFirestore.getInstance();

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        // TEXTO ARRECADAÇÃO
        txtArrecadacaoMes =
                findViewById(R.id.txtArrecadacaoMes);

        // TEXTO DESPESAS
        txtDespesasMes =
                findViewById(R.id.txtDespesasMes);

        // TEXTO SALDO
        txtSaldoAtual =
                findViewById(R.id.txtSaldoAtual);

        // BOTÃO EXPORTAR
        btnExportar =
                findViewById(R.id.btnExportar);

        // ======================================================
        // CLIQUE BOTÃO EXPORTAR
        // ======================================================

        btnExportar.setOnClickListener(v -> {

            // MOSTRA MENSAGEM
            Toast.makeText(

                    this,

                    "PDF em desenvolvimento",

                    Toast.LENGTH_SHORT

            ).show();
        });

        // ======================================================
        // ATUALIZA EM TEMPO REAL
        // ======================================================

        db.collection("caixa")

                // DOCUMENTO FINANCEIRO
                .document("financeiro")

                // ESCUTA ALTERAÇÕES
                .addSnapshotListener((value, error) -> {

                    // ==============================================
                    // SE FOR NULO
                    // ==============================================

                    if(value == null) return;

                    // ==============================================
                    // PEGA SALDO
                    // ==============================================

                    Double saldo =
                            value.getDouble("saldo");

                    // ==============================================
                    // PEGA ENTRADAS
                    // ==============================================

                    Double entradas =
                            value.getDouble("entradas");

                    // ==============================================
                    // PEGA SAÍDAS
                    // ==============================================

                    Double saidas =
                            value.getDouble("saidas");

                    // ==============================================
                    // SE SALDO FOR NULO
                    // ==============================================

                    if(saldo == null){

                        saldo = 0.0;
                    }

                    // ==============================================
                    // SE ENTRADAS FOR NULO
                    // ==============================================

                    if(entradas == null){

                        entradas = 0.0;
                    }

                    // ==============================================
                    // SE SAÍDAS FOR NULO
                    // ==============================================

                    if(saidas == null){

                        saidas = 0.0;
                    }

                    // ==============================================
                    // MOSTRA ARRECADAÇÃO
                    // ==============================================

                    txtArrecadacaoMes.setText(

                            "R$ " + entradas
                    );

                    // ==============================================
                    // MOSTRA DESPESAS
                    // ==============================================

                    txtDespesasMes.setText(

                            "R$ " + saidas
                    );

                    // ==============================================
                    // MOSTRA SALDO
                    // ==============================================

                    txtSaldoAtual.setText(

                            "R$ " + saldo
                    );
                });
    }
}