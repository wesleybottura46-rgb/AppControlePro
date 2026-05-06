package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

// ANDROID
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.net.Uri;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

// GLIDE
import com.bumptech.glide.Glide;

// PROJETO
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

// JAVA
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// ======================================================
// ACTIVITY CADASTRO JOGO
// ======================================================

public class CadastroJogoActivity
        extends AppCompatActivity {

    // ======================================================
    // CAMPOS
    // ======================================================

    EditText edtAdversario;

    EditText edtData;

    EditText edtHora;

    EditText edtLocal;

    // ======================================================
    // IMAGEM ADVERSÁRIO
    // ======================================================

    ImageView imgAdv;

    // ======================================================
    // URL EMBLEMA ADVERSÁRIO
    // ======================================================

    String emblemaAdv = "";

    // ======================================================
    // TIME ID
    // ======================================================

    String timeId;

    // ======================================================
    // SELECTOR IMAGEM
    // ======================================================

    ActivityResultLauncher<String>
            selecionarImagem;

    // ======================================================
    // ON CREATE
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // ======================================================
        // XML
        // ======================================================

        setContentView(
                R.layout.activity_cadastro_jogo
        );

        // ======================================================
        // XML → JAVA
        // ======================================================

        edtAdversario =
                findViewById(
                        R.id.edtAdversario
                );

        edtData =
                findViewById(
                        R.id.edtData
                );

        edtHora =
                findViewById(
                        R.id.edtHora
                );

        edtLocal =
                findViewById(
                        R.id.edtLocal
                );

        imgAdv =
                findViewById(
                        R.id.imgAdv
                );

        // ======================================================
        // VERIFICA IMAGEVIEW
        // ======================================================

        if(imgAdv == null){

            Toast.makeText(

                    this,

                    "Erro imgAdv NULL",

                    Toast.LENGTH_LONG

            ).show();

            finish();

            return;
        }

        // ======================================================
        // TIME ID
        // ======================================================

        timeId =
                SessionManager.getTimeId(this);

        // ======================================================
        // VERIFICA TIME
        // ======================================================

        if(timeId == null
                || timeId.isEmpty()){

            Toast.makeText(

                    this,

                    "Erro ao carregar time",

                    Toast.LENGTH_LONG

            ).show();

            finish();

            return;
        }

        // ======================================================
        // REGISTRA SELECTOR IMAGEM
        // ======================================================

        selecionarImagem =

                registerForActivityResult(

                        new ActivityResultContracts.GetContent(),

                        uri -> {

                            // ======================================================
                            // SE ESCOLHEU
                            // ======================================================

                            if(uri != null){

                                // ======================================================
                                // SALVA URL
                                // ======================================================

                                emblemaAdv =
                                        uri.toString();

                                // ======================================================
                                // MOSTRA IMAGEM
                                // ======================================================

                                Glide.with(this)

                                        .load(uri)

                                        .into(imgAdv);
                            }
                        }
                );

        // ======================================================
        // CLICK ESCUDO
        // ======================================================

        imgAdv.setOnClickListener(v -> {

            selecionarImagem.launch(
                    "image/*"
            );
        });

        // ======================================================
        // CONFIGURA DATA
        // ======================================================

        configurarData();

        // ======================================================
        // CONFIGURA HORA
        // ======================================================

        configurarHora();
    }

    // ======================================================
    // CONFIGURAR DATA
    // ======================================================

    private void configurarData(){

        edtData.setOnClickListener(v -> {

            // ======================================================
            // CALENDÁRIO
            // ======================================================

            Calendar c =
                    Calendar.getInstance();

            // ======================================================
            // DATE PICKER
            // ======================================================

            new DatePickerDialog(

                    this,

                    (view, ano, mes, dia) -> {

                        edtData.setText(

                                dia + "/"
                                        + (mes + 1)
                                        + "/"
                                        + ano
                        );
                    },

                    c.get(Calendar.YEAR),

                    c.get(Calendar.MONTH),

                    c.get(Calendar.DAY_OF_MONTH)

            ).show();
        });
    }

    // ======================================================
    // CONFIGURAR HORA
    // ======================================================

    private void configurarHora(){

        edtHora.setOnClickListener(v -> {

            // ======================================================
            // CALENDÁRIO
            // ======================================================

            Calendar c =
                    Calendar.getInstance();

            // ======================================================
            // TIME PICKER
            // ======================================================

            new TimePickerDialog(

                    this,

                    (view, hora, minuto) -> {

                        edtHora.setText(

                                String.format(

                                        "%02d:%02d",

                                        hora,

                                        minuto
                                )
                        );
                    },

                    c.get(Calendar.HOUR_OF_DAY),

                    c.get(Calendar.MINUTE),

                    true

            ).show();
        });
    }

    // ======================================================
    // CADASTRAR JOGO
    // ======================================================

    public void cadastrarJogo(View view){

        // ======================================================
        // TEXTOS
        // ======================================================

        String adversario =

                edtAdversario
                        .getText()
                        .toString()
                        .trim();

        String data =

                edtData
                        .getText()
                        .toString()
                        .trim();

        String hora =

                edtHora
                        .getText()
                        .toString()
                        .trim();

        String local =

                edtLocal
                        .getText()
                        .toString()
                        .trim();

        // ======================================================
        // VALIDAÇÃO
        // ======================================================

        if(adversario.isEmpty()
                || data.isEmpty()
                || hora.isEmpty()){

            Toast.makeText(

                    this,

                    "Preencha os campos",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // MAPA
        // ======================================================

        Map<String,Object> dados =
                new HashMap<>();

        // ADVERSÁRIO
        dados.put(
                "adversario",
                adversario
        );

        // DATA
        dados.put(
                "data",
                data
        );

        // HORA
        dados.put(
                "hora",
                hora
        );

        // LOCAL
        dados.put(
                "local",
                local
        );

        // TIME ID
        dados.put(
                "timeId",
                timeId
        );

        // EMBLEMA ADVERSÁRIO
        dados.put(
                "emblemaAdv",
                emblemaAdv
        );

        // ======================================================
        // SALVAR FIREBASE
        // ======================================================

        FirebaseHelper.getFirestore()

                .collection("jogos")

                .add(dados)

                .addOnSuccessListener(doc -> {

                    // ======================================================
                    // SUCESSO
                    // ======================================================

                    Toast.makeText(

                            this,

                            "Jogo cadastrado",

                            Toast.LENGTH_SHORT

                    ).show();

                    // ======================================================
                    // FECHA
                    // ======================================================

                    finish();
                })

                .addOnFailureListener(e -> {

                    Toast.makeText(

                            this,

                            e.getMessage(),

                            Toast.LENGTH_LONG

                    ).show();
                });
    }
}