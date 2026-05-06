// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA TELA PADRÃO
import androidx.appcompat.app.AppCompatActivity;

// IMPORTA RECYCLER VIEW VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager;

// IMPORTA RECYCLER VIEW
import androidx.recyclerview.widget.RecyclerView;

// IMPORTA CICLO DA TELA
import android.os.Bundle;

// IMPORTA VIEW
import android.view.View;

// IMPORTA TEXTO
import android.widget.TextView;

// IMPORTA TOAST
import android.widget.Toast;

// IMPORTA R
import com.example.appcontrolepro.R;

// IMPORTA FIREBASE HELPER
import com.example.appcontrolepro.database.FirebaseHelper;

// IMPORTA SESSION MANAGER
import com.example.appcontrolepro.utils.SessionManager;

// IMPORTA FIELD VALUE
import com.google.firebase.firestore.FieldValue;

// IMPORTA ARRAY LIST
import java.util.ArrayList;

// IMPORTA HASH MAP
import java.util.HashMap;

// IMPORTA LISTA
import java.util.List;

// IMPORTA MAPA
import java.util.Map;

// ======================================================
// TELA SÚMULA
// ======================================================
//
// ESSA TELA:
//
// ✔ CONTROLA O PLACAR
// ✔ ESCOLHE QUEM JOGOU
// ✔ SALVA GOLS
// ✔ SALVA ASSISTÊNCIAS
// ✔ SOMA ESTATÍSTICAS
// ✔ SALVA EVENTOS
//
// ======================================================

// CRIA CLASSE
public class SumulaActivity

        // HERDA DA ACTIVITY
        extends AppCompatActivity {

    // ======================================================
    // TEXTOS
    // ======================================================

    // TEXTO DO PLACAR
    TextView txtPlacar;

    // TEXTO GOLS NOSSO TIME
    TextView txtNosso;

    // TEXTO GOLS ADVERSÁRIO
    TextView txtAdv;

    // ======================================================
    // RECYCLER VIEW
    // ======================================================

    // LISTA DOS JOGADORES
    RecyclerView recyclerJogadores;

    // ======================================================
    // PLACAR
    // ======================================================

    // GOLS NOSSO TIME
    public int golsNosso = 0;

    // GOLS ADVERSÁRIO
    public int golsAdv = 0;

    // ======================================================
    // LISTA DOS JOGADORES
    // ======================================================

    // LISTA QUE GUARDA JOGADORES
    List<Map<String,Object>> listaJogadores =
            new ArrayList<>();

    // ======================================================
    // ADAPTER
    // ======================================================

    // ADAPTER DA SÚMULA
    JogadorSumulaAdapter adapter;

    // ======================================================
    // IDS
    // ======================================================

    // ID DO JOGO
    String jogoId;

    // ID DO TIME
    String timeId;

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

        setContentView(R.layout.activity_sumula);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        // TEXTO PLACAR
        txtPlacar =
                findViewById(R.id.txtPlacar);

        // TEXTO NOSSO TIME
        txtNosso =
                findViewById(R.id.txtNosso);

        // TEXTO ADVERSÁRIO
        txtAdv =
                findViewById(R.id.txtAdv);

        // RECYCLER VIEW
        recyclerJogadores =
                findViewById(R.id.recyclerJogadores);

        // ======================================================
        // PEGA ID DO JOGO
        // ======================================================

        jogoId =
                getIntent().getStringExtra("jogoId");

        // ======================================================
        // PEGA ID DO TIME
        // ======================================================

        timeId =
                SessionManager.getTimeId(this);

        // ======================================================
        // CONFIGURA RECYCLER VIEW
        // ======================================================

        recyclerJogadores.setLayoutManager(

                new LinearLayoutManager(this)
        );

        // ======================================================
        // CARREGA JOGADORES
        // ======================================================

        carregarJogadores();

        // ======================================================
        // ATUALIZA PLACAR
        // ======================================================

        atualizarPlacar();
    }

    // ======================================================
    // MAIS GOL NOSSO TIME
    // ======================================================

    public void maisNosso(View view){

        // SOMA 1 GOL
        golsNosso++;

        // ATUALIZA PLACAR
        atualizarPlacar();
    }

    // ======================================================
    // MENOS GOL NOSSO TIME
    // ======================================================

    public void menosNosso(View view){

        // SE MAIOR QUE ZERO
        if(golsNosso > 0){

            // REMOVE 1 GOL
            golsNosso--;
        }

        // ATUALIZA PLACAR
        atualizarPlacar();
    }

    // ======================================================
    // MAIS GOL ADVERSÁRIO
    // ======================================================

    public void maisAdv(View view){

        // SOMA 1 GOL
        golsAdv++;

        // ATUALIZA PLACAR
        atualizarPlacar();
    }

    // ======================================================
    // MENOS GOL ADVERSÁRIO
    // ======================================================

    public void menosAdv(View view){

        // SE MAIOR QUE ZERO
        if(golsAdv > 0){

            // REMOVE 1 GOL
            golsAdv--;
        }

        // ATUALIZA PLACAR
        atualizarPlacar();
    }

    // ======================================================
    // ATUALIZA PLACAR
    // ======================================================

    private void atualizarPlacar(){

        // MOSTRA GOLS NOSSO TIME
        txtNosso.setText(

                String.valueOf(golsNosso)
        );

        // MOSTRA GOLS ADVERSÁRIO
        txtAdv.setText(

                String.valueOf(golsAdv)
        );

        // MOSTRA PLACAR COMPLETO
        txtPlacar.setText(

                golsNosso + " x " + golsAdv
        );
    }

    // ======================================================
    // CARREGAR JOGADORES
    // ======================================================

    private void carregarJogadores(){

        // BUSCA JOGADORES
        FirebaseHelper.getFirestore()

                // COLEÇÃO
                .collection("jogadores")

                // FILTRA PELO TIME
                .whereEqualTo("timeId", timeId)

                // BUSCA DADOS
                .get()

                // SE DER CERTO
                .addOnSuccessListener(query -> {

                    // ==============================================
                    // LIMPA LISTA
                    // ==============================================

                    listaJogadores.clear();

                    // ==============================================
                    // PERCORRE JOGADORES
                    // ==============================================

                    for(var doc : query){

                        // ==========================================
                        // CRIA MAPA DO JOGADOR
                        // ==========================================

                        Map<String,Object> jogador =
                                new HashMap<>();

                        // ==========================================
                        // SALVA ID
                        // ==========================================

                        jogador.put(

                                "id",

                                doc.getId()
                        );

                        // ==========================================
                        // SALVA NOME
                        // ==========================================

                        jogador.put(

                                "nome",

                                doc.getString("nome")
                        );

                        // ==========================================
                        // GOLS DA PARTIDA
                        // ==========================================

                        jogador.put(

                                "golsPartida",

                                0
                        );

                        // ==========================================
                        // ASSISTÊNCIAS DA PARTIDA
                        // ==========================================

                        jogador.put(

                                "assistPartida",

                                0
                        );

                        // ==========================================
                        // SE JOGOU
                        // ==========================================

                        jogador.put(

                                "jogou",

                                false
                        );

                        // ==========================================
                        // ADICIONA NA LISTA
                        // ==========================================

                        listaJogadores.add(jogador);
                    }

                    // ==============================================
                    // CRIA ADAPTER
                    // ==============================================

                    adapter =
                            new JogadorSumulaAdapter(

                                    this,

                                    listaJogadores
                            );

                    // ==============================================
                    // DEFINE ADAPTER
                    // ==============================================

                    recyclerJogadores.setAdapter(
                            adapter
                    );
                });
    }

    // ======================================================
    // SALVAR SÚMULA
    // ======================================================

    public void salvar(View view){

        // ======================================================
        // LISTA DE EVENTOS
        // ======================================================

        List<Map<String,Object>> eventos =
                new ArrayList<>();

        // ======================================================
        // PERCORRE JOGADORES
        // ======================================================

        for(Map<String,Object> jogador : listaJogadores){

            // ==============================================
            // VERIFICA SE JOGOU
            // ==============================================

            boolean jogou =

                    jogador.containsKey("jogou")

                            &&

                            (boolean) jogador.get("jogou");

            // ==============================================
            // SE NÃO JOGOU
            // ==============================================

            if(!jogou){

                continue;
            }

            // ==============================================
            // PEGA ID DO JOGADOR
            // ==============================================

            String jogadorId =
                    (String) jogador.get("id");

            // ==============================================
            // PEGA GOLS
            // ==============================================

            int gols =

                    jogador.containsKey("golsPartida")

                            ?

                            ((Number)

                                    jogador.get("golsPartida")).intValue()

                            : 0;

            // ==============================================
            // PEGA ASSISTÊNCIAS
            // ==============================================

            int assist =

                    jogador.containsKey("assistPartida")

                            ?

                            ((Number)

                                    jogador.get("assistPartida")).intValue()

                            : 0;

            // ==============================================
            // SOMA JOGOS
            // ==============================================

            FirebaseHelper.getFirestore()

                    .collection("jogadores")

                    .document(jogadorId)

                    .update(

                            "jogos",

                            FieldValue.increment(1)
                    );

            // ==============================================
            // SOMA GOLS
            // ==============================================

            if(gols > 0){

                FirebaseHelper.getFirestore()

                        .collection("jogadores")

                        .document(jogadorId)

                        .update(

                                "gols",

                                FieldValue.increment(gols)
                        );
            }

            // ==============================================
            // SOMA ASSISTÊNCIAS
            // ==============================================

            if(assist > 0){

                FirebaseHelper.getFirestore()

                        .collection("jogadores")

                        .document(jogadorId)

                        .update(

                                "assistencias",

                                FieldValue.increment(assist)
                        );
            }

            // ==============================================
            // EVENTOS DE GOL
            // ==============================================

            for(int i = 0; i < gols; i++){

                // CRIA MAPA EVENTO
                Map<String,Object> ev =
                        new HashMap<>();

                // SALVA GOL
                ev.put("gol", jogadorId);

                // ADICIONA EVENTO
                eventos.add(ev);
            }

            // ==============================================
            // EVENTOS DE ASSISTÊNCIA
            // ==============================================

            for(int i = 0; i < assist; i++){

                // CRIA MAPA EVENTO
                Map<String,Object> ev =
                        new HashMap<>();

                // SALVA ASSISTÊNCIA
                ev.put("assist", jogadorId);

                // ADICIONA EVENTO
                eventos.add(ev);
            }
        }

        // ======================================================
        // CRIA MAPA DOS DADOS
        // ======================================================

        Map<String,Object> dados =
                new HashMap<>();

        // ======================================================
        // SALVA PLACAR NOSSO
        // ======================================================

        dados.put(

                "placarNosso",

                golsNosso
        );

        // ======================================================
        // SALVA PLACAR ADVERSÁRIO
        // ======================================================

        dados.put(

                "placarAdversario",

                golsAdv
        );

        // ======================================================
        // SALVA EVENTOS
        // ======================================================

        dados.put(

                "eventos",

                eventos
        );

        // ======================================================
        // ATUALIZA JOGO
        // ======================================================

        FirebaseHelper.getFirestore()

                .collection("jogos")

                .document(jogoId)

                .update(dados)

                // SE DER CERTO
                .addOnSuccessListener(unused -> {

                    // ==============================================
                    // MOSTRA MENSAGEM
                    // ==============================================

                    Toast.makeText(

                            this,

                            "Súmula salva!",

                            Toast.LENGTH_SHORT
                    ).show();

                    // ==============================================
                    // FECHA TELA
                    // ==============================================

                    finish();
                });
    }
}