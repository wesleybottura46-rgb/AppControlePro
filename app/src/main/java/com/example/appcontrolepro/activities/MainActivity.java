// DEFINE O PACOTE
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ======================================================
// MAIN ACTIVITY
// ======================================================

public class MainActivity extends AppCompatActivity {

    // ======================================================
    // IMAGEM TIME
    // ======================================================

    ImageView imgTime;

    // ======================================================
    // URL EMBLEMA
    // ======================================================

    String emblemaTime = "";

    // ======================================================
    // SELECTOR IMAGEM
    // ======================================================

    ActivityResultLauncher<String>
            selecionarImagem;

    // ======================================================
    // TEXTO NOME TIME
    // ======================================================

    TextView txtNomeTime;

    // ======================================================
    // RECYCLERS
    // ======================================================

    RecyclerView recyclerJogos;

    RecyclerView recyclerJogadores;

    // ======================================================
    // TIME ID
    // ======================================================

    String timeId;

    // ======================================================
    // LISTA JOGOS
    // ======================================================

    List<Map<String, Object>> listaJogos =
            new ArrayList<>();

    // ======================================================
    // LISTA JOGADORES
    // ======================================================

    List<Map<String, Object>> listaJogadores =
            new ArrayList<>();

    // ======================================================
    // ADAPTERS
    // ======================================================

    JogosAdapter jogosAdapter;

    JogadoresAdapter jogadoresAdapter;

    // ======================================================
    // ON CREATE
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // ======================================================
        // XML
        // ======================================================

        setContentView(R.layout.activity_main);

        // ======================================================
        // XML → JAVA
        // ======================================================

        txtNomeTime =
                findViewById(R.id.txtNomeTime);

        imgTime =
                findViewById(R.id.imgTime);

        recyclerJogos =
                findViewById(R.id.recyclerJogos);

        recyclerJogadores =
                findViewById(R.id.recyclerJogadores);

        // ======================================================
        // RECYCLERS
        // ======================================================

        recyclerJogos.setLayoutManager(

                new LinearLayoutManager(this)
        );

        recyclerJogadores.setLayoutManager(

                new LinearLayoutManager(this)
        );

        // ======================================================
        // ADAPTER JOGADORES
        // ======================================================

        jogadoresAdapter =
                new JogadoresAdapter(

                        this,

                        listaJogadores
                );

        recyclerJogadores.setAdapter(
                jogadoresAdapter
        );

        // ======================================================
        // TIME ID
        // ======================================================

        timeId =
                SessionManager.getTimeId(this);

        // ======================================================
        // REGISTRA SELECTOR
        // ======================================================

        selecionarImagem =

                registerForActivityResult(

                        new ActivityResultContracts.GetContent(),

                        uri -> {

                            // ======================================================
                            // ESCOLHEU
                            // ======================================================

                            if(uri != null){

                                // ======================================================
                                // URL
                                // ======================================================

                                emblemaTime =
                                        uri.toString();

                                // ======================================================
                                // MOSTRA IMAGEM
                                // ======================================================

                                Glide.with(this)

                                        .load(uri)

                                        .into(imgTime);

                                // ======================================================
                                // SALVA FIREBASE
                                // ======================================================

                                FirebaseHelper.getFirestore()

                                        .collection("times")

                                        .document(timeId)

                                        .update(

                                                "emblema",

                                                emblemaTime
                                        )

                                        .addOnSuccessListener(unused -> {

                                            // ======================================================
                                            // RECRIA ADAPTER
                                            // ======================================================

                                            jogosAdapter =
                                                    new JogosAdapter(

                                                            MainActivity.this,

                                                            listaJogos,

                                                            txtNomeTime
                                                                    .getText()
                                                                    .toString(),

                                                            emblemaTime
                                                    );

                                            recyclerJogos.setAdapter(
                                                    jogosAdapter
                                            );

                                            jogosAdapter.notifyDataSetChanged();
                                        });
                            }
                        }
                );

        // ======================================================
        // CLICK IMAGEM
        // ======================================================

        imgTime.setOnClickListener(v -> {

            selecionarImagem.launch(
                    "image/*"
            );
        });

        // ======================================================
        // CARREGA
        // ======================================================

        carregarTime();

        carregarJogos();

        carregarJogadores();
    }

    // ======================================================
    // CARREGAR TIME
    // ======================================================

    private void carregarTime() {

        FirebaseHelper.getFirestore()

                .collection("times")

                .document(timeId)

                .get()

                .addOnSuccessListener(doc -> {

                    // ======================================================
                    // SE EXISTE
                    // ======================================================

                    if(doc.exists()){

                        // ======================================================
                        // NOME
                        // ======================================================

                        String nomeTime =
                                doc.getString("nome");

                        // ======================================================
                        // EMBLEMA
                        // ======================================================

                        emblemaTime =
                                doc.getString("emblema");

                        // ======================================================
                        // TEXTO
                        // ======================================================

                        txtNomeTime.setText(
                                nomeTime
                        );

                        // ======================================================
                        // IMAGEM
                        // ======================================================

                        if(emblemaTime != null
                                &&
                                !emblemaTime.isEmpty()){

                            Glide.with(this)

                                    .load(emblemaTime)

                                    .into(imgTime);
                        }

                        // ======================================================
                        // ADAPTER
                        // ======================================================

                        jogosAdapter =
                                new JogosAdapter(

                                        MainActivity.this,

                                        listaJogos,

                                        nomeTime,

                                        emblemaTime
                                );

                        recyclerJogos.setAdapter(
                                jogosAdapter
                        );
                    }
                });
    }

    // ======================================================
    // CARREGAR JOGOS
    // ======================================================

    private void carregarJogos() {

        FirebaseHelper.getFirestore()

                .collection("jogos")

                .whereEqualTo(
                        "timeId",
                        timeId
                )

                .addSnapshotListener((q, e) -> {

                    if(q == null) return;

                    listaJogos.clear();

                    for(DocumentSnapshot doc
                            : q.getDocuments()){

                        Map<String,Object> mapa =
                                doc.getData();

                        mapa.put(
                                "id",
                                doc.getId()
                        );

                        listaJogos.add(mapa);
                    }

                    if(jogosAdapter != null){

                        jogosAdapter.notifyDataSetChanged();
                    }
                });
    }

    // ======================================================
    // CARREGAR JOGADORES
    // ======================================================

    private void carregarJogadores() {

        FirebaseHelper.getFirestore()

                .collection("jogadores")

                .whereEqualTo(
                        "timeId",
                        timeId
                )

                .addSnapshotListener((q, e) -> {

                    if(q == null) return;

                    listaJogadores.clear();

                    for(DocumentSnapshot doc
                            : q.getDocuments()){

                        Map<String,Object> jogador =
                                new HashMap<>(
                                        doc.getData()
                                );

                        jogador.put(
                                "id",
                                doc.getId()
                        );

                        listaJogadores.add(jogador);
                    }

                    listaJogadores.sort((a, b) ->

                            Long.compare(

                                    ((Number)

                                            (b.get("gols") == null

                                                    ? 0

                                                    : b.get("gols")))

                                            .longValue(),

                                    ((Number)

                                            (a.get("gols") == null

                                                    ? 0

                                                    : a.get("gols")))

                                            .longValue()
                            )
                    );

                    jogadoresAdapter.notifyDataSetChanged();
                });
    }

    // ======================================================
    // NOVO JOGO
    // ======================================================

    public void abrirCadastroJogo(View v) {

        startActivity(

                new Intent(

                        this,

                        CadastroJogoActivity.class
                )
        );
    }

    // ======================================================
    // NOVO JOGADOR
    // ======================================================

    public void abrirCadastroJogador(View v) {

        startActivity(

                new Intent(

                        this,

                        CadastroJogadorActivity.class
                )
        );
    }

    // ======================================================
    // TROCAR TIME
    // ======================================================

    public void trocarTime(View v) {

        SessionManager.limparSessao(this);

        startActivity(

                new Intent(

                        this,

                        EscolherTimeActivity.class
                )
        );

        finish();
    }

    // ======================================================
    // SAIR
    // ======================================================

    public void sair(View view){

        SessionManager.limparSessao(this);

        FirebaseHelper
                .getAuth()
                .signOut();

        Intent intent =
                new Intent(

                        this,

                        LoginActivity.class
                );

        intent.setFlags(

                Intent.FLAG_ACTIVITY_NEW_TASK

                        |

                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
    }

    // ======================================================
    // FINANCEIRO
    // ======================================================

    public void abriFinanceiro(View view){

        startActivity(

                new Intent(

                        this,

                        FinanceiroActivity.class
                )
        );
    }
}