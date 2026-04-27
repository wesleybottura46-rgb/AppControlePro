package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;
import com.google.firebase.firestore.FieldValue;

import java.util.*;

public class SumulaActivity extends AppCompatActivity {

    TextView txtPlacar, txtNosso, txtAdv;
    ListView listJogadores;

    int golsNosso = 0;
    int golsAdv = 0;

    ArrayList<String> nomes = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();

    ArrayList<Map<String,Object>> eventos = new ArrayList<>();

    String jogoId, timeId;

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_sumula);

        txtPlacar = findViewById(R.id.txtPlacar);
        txtNosso = findViewById(R.id.txtNosso);
        txtAdv = findViewById(R.id.txtAdv);
        listJogadores = findViewById(R.id.listJogadores);

        jogoId = getIntent().getStringExtra("jogoId");
        timeId = SessionManager.getTimeId(this);

        carregarJogadores();
        carregarSumula();
    }

    // ==========================
    // CARREGAR SÚMULA EXISTENTE
    // ==========================
    private void carregarSumula(){

        FirebaseHelper.getFirestore()
                .collection("jogos")
                .document(jogoId)
                .get()
                .addOnSuccessListener(doc -> {

                    Long nosso = doc.getLong("placarNosso");
                    Long adv = doc.getLong("placarAdversario");

                    if(nosso != null){
                        golsNosso = nosso.intValue();
                        txtNosso.setText(String.valueOf(golsNosso));
                    }

                    if(adv != null){
                        golsAdv = adv.intValue();
                        txtAdv.setText(String.valueOf(golsAdv));
                    }

                    List<Map<String,Object>> ev =
                            (List<Map<String,Object>>) doc.get("eventos");

                    if(ev != null){
                        eventos = new ArrayList<>(ev);
                    }

                    atualizar();
                });
    }

    // ==========================
    // BOTÕES PLACAR
    // ==========================
    public void maisNosso(View v){ golsNosso++; atualizar(); }
    public void menosNosso(View v){ if(golsNosso>0) golsNosso--; atualizar(); }

    public void maisAdv(View v){ golsAdv++; atualizar(); }
    public void menosAdv(View v){ if(golsAdv>0) golsAdv--; atualizar(); }

    private void atualizar(){
        txtPlacar.setText(golsNosso + " x " + golsAdv);
    }

    // ==========================
    // CONTADORES
    // ==========================
    private int contarGols(){
        int t = 0;
        for(Map<String,Object> ev:eventos){
            if(ev.containsKey("gol")) t++;
        }
        return t;
    }

    private int contarAssist(){
        int t = 0;
        for(Map<String,Object> ev:eventos){
            if(ev.containsKey("assist")) t++;
        }
        return t;
    }

    // ==========================
    // JOGADORES
    // ==========================
    private void carregarJogadores(){

        FirebaseHelper.getFirestore()
                .collection("jogadores")
                .whereEqualTo("timeId", timeId)
                .get()
                .addOnSuccessListener(q -> {

                    for(var doc:q){
                        nomes.add(doc.getString("nome"));
                        ids.add(doc.getId());
                    }

                    listJogadores.setAdapter(new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_list_item_1,
                            nomes
                    ));
                });

        listJogadores.setOnItemClickListener((p,v,pos,id)->{

            String jogadorId = ids.get(pos);

            String[] op = {"Gol", "Assistência"};

            new AlertDialog.Builder(this)
                    .setItems(op, (d,w)->{

                        Map<String,Object> ev = new HashMap<>();

                        if(w == 0){

                            if(contarGols() >= golsNosso){
                                Toast.makeText(this,"Limite de gols",Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ev.put("gol", jogadorId);
                        }

                        else{

                            if(contarAssist() >= golsNosso){
                                Toast.makeText(this,"Limite de assistências",Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(contarAssist() >= contarGols()){
                                Toast.makeText(this,"Assist > gols",Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ev.put("assist", jogadorId);
                        }

                        eventos.add(ev);
                        Toast.makeText(this,"Adicionado",Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });
    }

    // ==========================
    // SALVAR
    // ==========================
    public void salvar(View v){

        FirebaseHelper.getFirestore()
                .collection("jogos")
                .document(jogoId)
                .get()
                .addOnSuccessListener(doc -> {

                    List<Map<String,Object>> antigos =
                            (List<Map<String,Object>>) doc.get("eventos");

                    // REMOVE ANTIGOS
                    if(antigos != null){
                        for(Map<String,Object> ev:antigos){

                            if(ev.containsKey("gol")){
                                FirebaseHelper.getFirestore()
                                        .collection("jogadores")
                                        .document((String)ev.get("gol"))
                                        .update("gols", FieldValue.increment(-1));
                            }

                            if(ev.containsKey("assist")){
                                FirebaseHelper.getFirestore()
                                        .collection("jogadores")
                                        .document((String)ev.get("assist"))
                                        .update("assistencias", FieldValue.increment(-1));
                            }
                        }
                    }

                    // SALVA NOVO
                    Map<String,Object> dados = new HashMap<>();
                    dados.put("placarNosso", golsNosso);
                    dados.put("placarAdversario", golsAdv);
                    dados.put("eventos", eventos);

                    FirebaseHelper.getFirestore()
                            .collection("jogos")
                            .document(jogoId)
                            .update(dados);

                    // APLICA NOVOS
                    for(Map<String,Object> ev:eventos){

                        if(ev.containsKey("gol")){
                            FirebaseHelper.getFirestore()
                                    .collection("jogadores")
                                    .document((String)ev.get("gol"))
                                    .update("gols", FieldValue.increment(1));
                        }

                        if(ev.containsKey("assist")){
                            FirebaseHelper.getFirestore()
                                    .collection("jogadores")
                                    .document((String)ev.get("assist"))
                                    .update("assistencias", FieldValue.increment(1));
                        }
                    }

                    Toast.makeText(this,"Súmula salva!",Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}