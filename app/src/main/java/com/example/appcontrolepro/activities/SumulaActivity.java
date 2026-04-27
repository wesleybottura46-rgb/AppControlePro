package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumulaActivity extends AppCompatActivity {

    // TEXTO PLACAR
    TextView txtPlacar;

    // GOLS NOSSO TIME
    TextView txtNosso;

    // GOLS ADVERSÁRIO
    TextView txtAdv;

    // RECYCLER VIEW
    RecyclerView recyclerJogadores;

    // GOLS NOSSO TIME
    public int golsNosso = 0;

    // GOLS ADVERSÁRIO
    public int golsAdv = 0;

    // LISTA DOS JOGADORES
    List<Map<String,Object>> listaJogadores =
            new ArrayList<>();

    // ADAPTER
    JogadorSumulaAdapter adapter;

    // ID DO JOGO
    String jogoId;

    // ID DO TIME
    String timeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ABRE XML
        setContentView(R.layout.activity_sumula);

        // LIGA COMPONENTES
        txtPlacar =
                findViewById(R.id.txtPlacar);

        txtNosso =
                findViewById(R.id.txtNosso);

        txtAdv =
                findViewById(R.id.txtAdv);

        recyclerJogadores =
                findViewById(R.id.recyclerJogadores);

        // PEGA ID DO JOGO
        jogoId =
                getIntent().getStringExtra("jogoId");

        // PEGA ID DO TIME
        timeId =
                SessionManager.getTimeId(this);

        // CONFIGURA RECYCLER
        recyclerJogadores.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // CARREGA JOGADORES
        carregarJogadores();

        // ATUALIZA PLACAR
        atualizarPlacar();
    }

    // ==================================================
    // MAIS GOL NOSSO TIME
    // ==================================================
    public void maisNosso(View view){

        golsNosso++;

        atualizarPlacar();
    }

    // ==================================================
    // MENOS GOL NOSSO TIME
    // ==================================================
    public void menosNosso(View view){

        if(golsNosso > 0){

            golsNosso--;
        }

        atualizarPlacar();
    }

    // ==================================================
    // MAIS GOL ADVERSÁRIO
    // ==================================================
    public void maisAdv(View view){

        golsAdv++;

        atualizarPlacar();
    }

    // ==================================================
    // MENOS GOL ADVERSÁRIO
    // ==================================================
    public void menosAdv(View view){

        if(golsAdv > 0){

            golsAdv--;
        }

        atualizarPlacar();
    }

    // ==================================================
    // ATUALIZA PLACAR
    // ==================================================
    private void atualizarPlacar(){

        txtNosso.setText(
                String.valueOf(golsNosso)
        );

        txtAdv.setText(
                String.valueOf(golsAdv)
        );

        txtPlacar.setText(
                golsNosso + " x " + golsAdv
        );
    }

    // ==================================================
    // CARREGA JOGADORES
    // ==================================================
    private void carregarJogadores(){

        FirebaseHelper.getFirestore()
                .collection("jogadores")
                .whereEqualTo("timeId", timeId)
                .get()
                .addOnSuccessListener(query -> {

                    // LIMPA LISTA
                    listaJogadores.clear();

                    // PERCORRE JOGADORES
                    for(var doc : query){

                        // MAPA DO JOGADOR
                        Map<String,Object> jogador =
                                new HashMap<>();

                        // ID
                        jogador.put(
                                "id",
                                doc.getId()
                        );

                        // NOME
                        jogador.put(
                                "nome",
                                doc.getString("nome")
                        );

                        // GOLS PARTIDA
                        jogador.put(
                                "golsPartida",
                                0
                        );

                        // ASSIST PARTIDA
                        jogador.put(
                                "assistPartida",
                                0
                        );

                        // JOGOU
                        jogador.put(
                                "jogou",
                                false
                        );

                        // ADICIONA
                        listaJogadores.add(jogador);
                    }

                    // CRIA ADAPTER
                    adapter =
                            new JogadorSumulaAdapter(
                                    this,
                                    listaJogadores
                            );

                    // DEFINE ADAPTER
                    recyclerJogadores.setAdapter(
                            adapter
                    );
                });
    }

    // ==================================================
    // SALVAR SÚMULA
    // ==================================================
    public void salvar(View view){

        // EVENTOS
        List<Map<String,Object>> eventos =
                new ArrayList<>();

        // PERCORRE JOGADORES
        for(Map<String,Object> jogador : listaJogadores){

            // VERIFICA SE JOGOU
            boolean jogou =
                    jogador.containsKey("jogou")
                            && (boolean) jogador.get("jogou");

            // SE NÃO JOGOU
            if(!jogou){
                continue;
            }

            // PEGA ID
            String jogadorId =
                    (String) jogador.get("id");

            // PEGA GOLS
            int gols =
                    jogador.containsKey("golsPartida")
                            ? ((Number) jogador.get("golsPartida")).intValue()
                            : 0;

            // PEGA ASSISTÊNCIAS
            int assist =
                    jogador.containsKey("assistPartida")
                            ? ((Number) jogador.get("assistPartida")).intValue()
                            : 0;

            // ==========================================
            // SOMA JOGOS
            // ==========================================
            FirebaseHelper.getFirestore()
                    .collection("jogadores")
                    .document(jogadorId)
                    .update(
                            "jogos",
                            FieldValue.increment(1)
                    );

            // ==========================================
            // SOMA GOLS
            // ==========================================
            if(gols > 0){

                FirebaseHelper.getFirestore()
                        .collection("jogadores")
                        .document(jogadorId)
                        .update(
                                "gols",
                                FieldValue.increment(gols)
                        );
            }

            // ==========================================
            // SOMA ASSISTÊNCIAS
            // ==========================================
            if(assist > 0){

                FirebaseHelper.getFirestore()
                        .collection("jogadores")
                        .document(jogadorId)
                        .update(
                                "assistencias",
                                FieldValue.increment(assist)
                        );
            }

            // ==========================================
            // EVENTOS DE GOL
            // ==========================================
            for(int i = 0; i < gols; i++){

                Map<String,Object> ev =
                        new HashMap<>();

                ev.put("gol", jogadorId);

                eventos.add(ev);
            }

            // ==========================================
            // EVENTOS DE ASSIST
            // ==========================================
            for(int i = 0; i < assist; i++){

                Map<String,Object> ev =
                        new HashMap<>();

                ev.put("assist", jogadorId);

                eventos.add(ev);
            }
        }

        // DADOS
        Map<String,Object> dados =
                new HashMap<>();

        // PLACAR NOSSO
        dados.put(
                "placarNosso",
                golsNosso
        );

        // PLACAR ADV
        dados.put(
                "placarAdversario",
                golsAdv
        );

        // EVENTOS
        dados.put(
                "eventos",
                eventos
        );

        // SALVA JOGO
        FirebaseHelper.getFirestore()
                .collection("jogos")
                .document(jogoId)
                .update(dados)
                .addOnSuccessListener(unused -> {

                    // MENSAGEM
                    Toast.makeText(
                            this,
                            "Súmula salva!",
                            Toast.LENGTH_SHORT
                    ).show();

                    // FECHA TELA
                    finish();
                });
    }
}