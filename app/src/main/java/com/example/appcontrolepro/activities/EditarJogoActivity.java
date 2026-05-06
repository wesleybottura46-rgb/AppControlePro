package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// Tela padrão do Android
import androidx.appcompat.app.AppCompatActivity;

// Importações Android
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// Arquivos do projeto
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

// Importações Java
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA EDITAR JOGO
// ======================================================

public class EditarJogoActivity extends AppCompatActivity {

    // ======================================================
    // CAMPOS DA TELA
    // ======================================================

    // Campo do adversário
    EditText edtAdversario;

    // Campo da data
    EditText edtData;

    // Campo da hora
    EditText edtHora;

    // Campo do local
    EditText edtLocal;

    // ======================================================
    // ID DO JOGO
    // ======================================================

    // Guarda o ID do jogo que será editado
    String jogoId;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Liga XML nessa tela
        setContentView(R.layout.activity_editar_jogo);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        edtAdversario =
                findViewById(R.id.edtAdversario);

        edtData =
                findViewById(R.id.edtData);

        edtHora =
                findViewById(R.id.edtHora);

        edtLocal =
                findViewById(R.id.edtLocal);

        // ======================================================
        // PEGA O ID DO JOGO
        // ======================================================

        jogoId =
                getIntent().getStringExtra("jogoId");

        // ======================================================
        // VERIFICA SE O ID EXISTE
        // ======================================================

        if(jogoId == null){

            // Fecha a tela
            finish();

            return;
        }

        // ======================================================
        // CARREGA OS DADOS DO JOGO
        // ======================================================

        carregarJogo();

        // ======================================================
        // ABRIR CALENDÁRIO
        // ======================================================

        edtData.setOnClickListener(v -> abrirCalendario());

        // ======================================================
        // ABRIR RELÓGIO
        // ======================================================

        edtHora.setOnClickListener(v -> abrirRelogio());
    }

    // ======================================================
    // CARREGAR JOGO
    // ======================================================

    private void carregarJogo(){

        // Busca jogo no Firestore
        FirebaseHelper.getFirestore()

                // Coleção jogos
                .collection("jogos")

                // Documento do jogo
                .document(jogoId)

                // Busca os dados
                .get()

                // Se deu certo
                .addOnSuccessListener(doc -> {

                    // ======================================================
                    // VERIFICA SE EXISTE
                    // ======================================================

                    if(doc != null){

                        // ==========================================
                        // COLOCA DADOS NOS CAMPOS
                        // ==========================================

                        edtAdversario.setText(
                                doc.getString("adversario")
                        );

                        edtData.setText(
                                doc.getString("data")
                        );

                        edtHora.setText(
                                doc.getString("hora")
                        );

                        edtLocal.setText(
                                doc.getString("local")
                        );
                    }
                });
    }

    // ======================================================
    // ABRIR CALENDÁRIO
    // ======================================================

    private void abrirCalendario(){

        // Pega data atual
        Calendar c =
                Calendar.getInstance();

        // Abre calendário
        new DatePickerDialog(

                this,

                // Quando escolher a data
                (view, y, m, d) ->

                        edtData.setText(
                                d + "/" + (m + 1) + "/" + y
                        ),

                // Ano atual
                c.get(Calendar.YEAR),

                // Mês atual
                c.get(Calendar.MONTH),

                // Dia atual
                c.get(Calendar.DAY_OF_MONTH)

        ).show();
    }

    // ======================================================
    // ABRIR RELÓGIO
    // ======================================================

    private void abrirRelogio(){

        // Pega horário atual
        Calendar c =
                Calendar.getInstance();

        // Abre relógio
        new TimePickerDialog(

                this,

                // Quando escolher a hora
                (view, h, min) ->

                        edtHora.setText(

                                String.format(
                                        "%02d:%02d",
                                        h,
                                        min
                                )
                        ),

                // Hora atual
                c.get(Calendar.HOUR_OF_DAY),

                // Minuto atual
                c.get(Calendar.MINUTE),

                // Formato 24 horas
                true

        ).show();
    }

    // ======================================================
    // SALVAR ALTERAÇÃO
    // ======================================================

    public void salvarEdicao(View view){

        // ======================================================
        // CRIA MAPA DE DADOS
        // ======================================================

        Map<String,Object> dados =
                new HashMap<>();

        // Nome do adversário
        dados.put(
                "adversario",
                edtAdversario.getText().toString()
        );

        // Data do jogo
        dados.put(
                "data",
                edtData.getText().toString()
        );

        // Hora do jogo
        dados.put(
                "hora",
                edtHora.getText().toString()
        );

        // Local do jogo
        dados.put(
                "local",
                edtLocal.getText().toString()
        );

        // ======================================================
        // ATUALIZA NO FIREBASE
        // ======================================================

        FirebaseHelper.getFirestore()

                // Coleção jogos
                .collection("jogos")

                // Documento do jogo
                .document(jogoId)

                // Atualiza os dados
                .update(dados)

                // Se deu certo
                .addOnSuccessListener(v -> {

                    // ==========================================
                    // MENSAGEM DE SUCESSO
                    // ==========================================

                    Toast.makeText(

                            this,

                            "Jogo atualizado",

                            Toast.LENGTH_SHORT

                    ).show();

                    // Fecha a tela
                    finish();
                });
    }
}