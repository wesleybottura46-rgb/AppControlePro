package com.example.appcontrolepro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.activities.JogadoresAdapter;
import com.example.appcontrolepro.activities.JogosAdapter;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtNomeTime;

    RecyclerView recyclerJogos, recyclerJogadores;

    String timeId;

    List<Map<String, Object>> listaJogos = new ArrayList<>();
    List<Map<String, Object>> listaJogadores = new ArrayList<>();

    JogosAdapter jogosAdapter;
    com.example.appcontrolepro.activities.JogadoresAdapter jogadoresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNomeTime = findViewById(R.id.txtNomeTime);

        recyclerJogos = findViewById(R.id.recyclerJogos);
        recyclerJogadores = findViewById(R.id.recyclerJogadores);

        recyclerJogos.setLayoutManager(new LinearLayoutManager(this));
        recyclerJogadores.setLayoutManager(new LinearLayoutManager(this));

        jogosAdapter = new JogosAdapter(this, listaJogos);
        jogadoresAdapter = new JogadoresAdapter(this, listaJogadores);

        recyclerJogos.setAdapter(jogosAdapter);
        recyclerJogadores.setAdapter(jogadoresAdapter);

        timeId = SessionManager.getTimeId(this);

        carregarTime();
        carregarJogos();
        carregarJogadores();
    }

    private void carregarTime() {
        FirebaseHelper.getFirestore()
                .collection("times")
                .document(timeId)
                .get()
                .addOnSuccessListener(doc ->
                        txtNomeTime.setText(doc.getString("nome")));
    }

    private void carregarJogos() {
        FirebaseHelper.getFirestore()
                .collection("jogos")
                .whereEqualTo("timeId", timeId)
                .addSnapshotListener((q, e) -> {

                    listaJogos.clear();

                    for (var doc : q) {
                        Map<String, Object> jogo = doc.getData();
                        jogo.put("id", doc.getId());
                        listaJogos.add(jogo);
                    }

                    jogosAdapter.notifyDataSetChanged();
                });
    }


    public void abrirCadastroJogo(View v) {
        startActivity(new Intent(this, CadastroJogoActivity.class));
    }

    public void abrirCadastroJogador(View v) {
        startActivity(new Intent(this, CadastroJogadorActivity.class));
    }

    public void trocarTime(View v) {
        SessionManager.limparSessao(this);
        startActivity(new Intent(this, EscolherTimeActivity.class));
        finish();
    }

    private void carregarJogadores() {

        FirebaseHelper.getFirestore()
                .collection("jogadores")
                .whereEqualTo("timeId", timeId)
                .addSnapshotListener((q, e) -> {

                    if (q == null) return;

                    listaJogadores.clear();

                    for (var doc : q) {

                        // 🔥 cria novo map (IMPORTANTE)
                        Map<String, Object> jogador = new HashMap<>(doc.getData());

                        // 🔥 adiciona ID corretamente
                        jogador.put("id", doc.getId());

                        listaJogadores.add(jogador);
                    }

                    // ORDENA POR GOLS
                    listaJogadores.sort((a, b) -> Long.compare(
                            ((Number) (b.get("gols") == null ? 0 : b.get("gols"))).longValue(),
                            ((Number) (a.get("gols") == null ? 0 : a.get("gols"))).longValue()
                    ));

                    jogadoresAdapter.notifyDataSetChanged();
                });
    }

    // ==========================
// LOGOUT COMPLETO
// ==========================
    public void sair(View view){

        // 🔥 limpa dados do app
        SessionManager.limparSessao(this);

        // 🔥 desloga do Firebase (ESSENCIAL)
        FirebaseHelper.getAuth().signOut();

        // 🔥 vai para login
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}