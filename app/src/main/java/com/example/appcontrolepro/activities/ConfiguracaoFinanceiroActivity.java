package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// Importações Android
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Tela padrão do Android
import androidx.appcompat.app.AppCompatActivity;

// Arquivos do projeto
import com.example.appcontrolepro.R;

// Firebase
import com.google.firebase.firestore.FirebaseFirestore;

// Importações Java
import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA CONFIGURAÇÃO FINANCEIRA
// ======================================================

public class ConfiguracaoFinanceiroActivity
        extends AppCompatActivity {

    // ======================================================
    // CAMPO DE TEXTO
    // ======================================================

    // Campo onde digita o valor da mensalidade
    EditText edtValorMensalidade;

    // ======================================================
    // BOTÃO
    // ======================================================

    // Botão salvar
    Button btnSalvarValor;

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

        // ======================================================
        // LIGA XML NESSA TELA
        // ======================================================

        setContentView(
                R.layout.activity_configuracao_financeiro
        );

        // ======================================================
        // INICIA FIREBASE
        // ======================================================

        db =
                FirebaseFirestore.getInstance();

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        edtValorMensalidade =
                findViewById(
                        R.id.edtValorMensalidade
                );

        btnSalvarValor =
                findViewById(
                        R.id.btnSalvarValor
                );

        // ======================================================
        // BOTÃO SALVAR
        // ======================================================

        // Quando clicar no botão
        btnSalvarValor.setOnClickListener(v -> {

            // ======================================================
            // PEGA O TEXTO DIGITADO
            // ======================================================

            String valor =

                    edtValorMensalidade
                            .getText()
                            .toString()
                            .trim();

            // ======================================================
            // VERIFICA SE ESTÁ VAZIO
            // ======================================================

            if(valor.isEmpty()){

                // Mostra mensagem
                Toast.makeText(

                        this,

                        "Digite um valor",

                        Toast.LENGTH_SHORT

                ).show();

                return;
            }

            // ======================================================
            // CRIA MAPA DE DADOS
            // ======================================================

            Map<String,Object> dados =
                    new HashMap<>();

            // ======================================================
            // SALVA O VALOR DA MENSALIDADE
            // ======================================================

            dados.put(

                    "valorMensalidadeAtual",

                    Integer.parseInt(valor)
            );

            // ======================================================
            // SALVA NO FIREBASE
            // ======================================================

            db.collection("configuracoes")

                    // Documento financeiro
                    .document("financeiro")

                    // Salva os dados
                    .set(dados)

                    // Se deu certo
                    .addOnSuccessListener(unused -> {

                        // ==========================================
                        // MENSAGEM DE SUCESSO
                        // ==========================================

                        Toast.makeText(

                                this,

                                "Valor atualizado",

                                Toast.LENGTH_SHORT

                        ).show();
                    });
        });
    }
}