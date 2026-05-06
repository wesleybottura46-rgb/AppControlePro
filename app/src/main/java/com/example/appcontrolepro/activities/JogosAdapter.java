// ======================================================
// ARQUIVO:
// JogosAdapter.java
// ======================================================
//
// SUBSTITUA O ARQUIVO INTEIRO
//
// CORREÇÕES:
//
// ✔ Popup voltou igual antes
// ✔ Cadastrar súmula
// ✔ Editar súmula
// ✔ Excluir jogo
// ✔ Escudo time casa
// ✔ Escudo adversário
// ✔ Nome time aparece
// ✔ Sem resultado mostra "x"
// ✔ Resultado mostra gols
// ✔ Exclusão atualiza estatísticas
//
// ======================================================

package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ======================================================
// ADAPTER JOGOS
// ======================================================

public class JogosAdapter
        extends RecyclerView.Adapter<JogosAdapter.MyViewHolder> {

    // ======================================================
    // CONTEXT
    // ======================================================

    Context context;

    // ======================================================
    // LISTA
    // ======================================================

    List<Map<String,Object>> lista;

    // ======================================================
    // NOME TIME
    // ======================================================

    String nomeTime;

    // ======================================================
    // ESCUDO TIME
    // ======================================================

    String emblemaTime;

    // ======================================================
    // CONSTRUTOR
    // ======================================================

    public JogosAdapter(

            Context context,

            List<Map<String,Object>> lista,
            String nomeTime, String emblemaTime){

        this.context = context;

        this.lista = lista;

        this.nomeTime = nomeTime;

        this.emblemaTime = emblemaTime;
    }

    // ======================================================
    // CRIA VIEW
    // ======================================================

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(

            @NonNull ViewGroup parent,

            int viewType
    ) {

        View view = LayoutInflater

                .from(context)

                .inflate(
                        R.layout.item_jogo,
                        parent,
                        false
                );

        return new MyViewHolder(view);
    }

    // ======================================================
    // BIND
    // ======================================================

    @Override
    public void onBindViewHolder(

            @NonNull MyViewHolder holder,

            int position
    ) {

        // ======================================================
        // MAPA
        // ======================================================

        Map<String,Object> jogo =
                lista.get(position);

        // ======================================================
        // ID
        // ======================================================

        String jogoId =
                jogo.get("id").toString();

        // ======================================================
        // DATA
        // ======================================================

        holder.txtData.setText(

                String.valueOf(
                        jogo.get("data")
                )
        );

        // ======================================================
        // NOME TIME CASA
        // ======================================================

        holder.txtNossoTime.setText(
                nomeTime
        );

        // ======================================================
        // ADVERSÁRIO
        // ======================================================

        holder.txtAdversario.setText(

                String.valueOf(
                        jogo.get("adversario")
                )
        );

        // ======================================================
        // ESCUDO TIME CASA
        // ======================================================

        if(emblemaTime != null &&
                !emblemaTime.isEmpty()){

            Glide.with(context)
                    .load(emblemaTime)
                    .into(holder.imgCasa);
        }

        // ======================================================
        // ESCUDO ADVERSÁRIO
        // ======================================================

        if(jogo.containsKey("emblemaAdv")){

            Object obj =
                    jogo.get("emblemaAdv");

            if(obj != null){

                String url =
                        obj.toString();

                if(!url.isEmpty()){

                    Glide.with(context)

                            .load(url)

                            .into(holder.imgFora);
                }
            }
        }

        // ======================================================
        // PLACAR
        // ======================================================

        Object nossoObj =
                jogo.get("placarNosso");

        Object advObj =
                jogo.get("placarAdversario");

        // ======================================================
        // SEM RESULTADO
        // ======================================================

        if(nossoObj == null || advObj == null){

            holder.txtPlacar.setText("x");
        }

        // ======================================================
        // COM RESULTADO
        // ======================================================

        else {

            int nosso =
                    ((Number) nossoObj)
                            .intValue();

            int adv =
                    ((Number) advObj)
                            .intValue();

            holder.txtPlacar.setText(
                    nosso + " x " + adv
            );
        }

        // ======================================================
        // CLICK VER DETALHES
        // ======================================================
        // ABRE MENU
        // ======================================================

        holder.txtDetalhes.setOnClickListener(v -> {

            PopupMenu popup =
                    new PopupMenu(
                            context,
                            holder.txtDetalhes
                    );

            // ======================================================
            // SEM SÚMULA
            // ======================================================

            if(nossoObj == null){

                popup.getMenu().add(
                        "Cadastrar súmula"
                );
            }

            // ======================================================
            // COM SÚMULA
            // ======================================================

            else {

                popup.getMenu().add(
                        "Editar súmula"
                );
            }

            // ======================================================
            // EXCLUIR
            // ======================================================

            popup.getMenu().add(
                    "Excluir jogo"
            );

            // ======================================================
            // CLICK MENU
            // ======================================================

            popup.setOnMenuItemClickListener(item -> {

                String titulo =
                        item.getTitle().toString();

                // ======================================================
                // SÚMULA
                // ======================================================

                if(
                        titulo.equals("Cadastrar súmula")
                                ||
                                titulo.equals("Editar súmula")
                ){

                    Intent intent =
                            new Intent(
                                    context,
                                    SumulaActivity.class
                            );

                    intent.putExtra(
                            "jogoId",
                            jogoId
                    );

                    context.startActivity(intent);
                }

                // ======================================================
                // EXCLUIR
                // ======================================================

                else if(
                        titulo.equals("Excluir jogo")
                ){

                    excluirJogo(jogoId);
                }

                return true;
            });

            popup.show();
        });
    }

    // ======================================================
    // TOTAL
    // ======================================================

    @Override
    public int getItemCount() {

        return lista.size();
    }

    // ======================================================
    // EXCLUIR JOGO
    // ======================================================

    private void excluirJogo(String jogoId){

        FirebaseHelper.getFirestore()

                .collection("jogos")

                .document(jogoId)

                .get()

                .addOnSuccessListener(document -> {

                    if(!document.exists()){

                        return;
                    }

                    // ======================================================
                    // EVENTOS
                    // ======================================================

                    List<Map<String,Object>> eventos =

                            (List<Map<String, Object>>)
                                    document.get("eventos");

                    // ======================================================
                    // CONTROLES
                    // ======================================================

                    Map<String,Integer> golsJogador =
                            new HashMap<>();

                    Map<String,Integer> assistJogador =
                            new HashMap<>();

                    List<String> jogadoresParticiparam =
                            new ArrayList<>();

                    // ======================================================
                    // EVENTOS
                    // ======================================================

                    if(eventos != null){

                        for(Map<String,Object> evento : eventos){

                            // ======================================================
                            // GOL
                            // ======================================================

                            if(evento.containsKey("gol")){

                                String jogadorId =

                                        evento.get("gol")
                                                .toString();

                                if(!jogadoresParticiparam.contains(jogadorId)){

                                    jogadoresParticiparam.add(jogadorId);
                                }

                                int atual =

                                        golsJogador.containsKey(jogadorId)

                                                ? golsJogador.get(jogadorId)

                                                : 0;

                                golsJogador.put(
                                        jogadorId,
                                        atual + 1
                                );
                            }

                            // ======================================================
                            // ASSISTÊNCIA
                            // ======================================================

                            if(evento.containsKey("assist")){

                                String jogadorId =

                                        evento.get("assist")
                                                .toString();

                                if(!jogadoresParticiparam.contains(jogadorId)){

                                    jogadoresParticiparam.add(jogadorId);
                                }

                                int atual =

                                        assistJogador.containsKey(jogadorId)

                                                ? assistJogador.get(jogadorId)

                                                : 0;

                                assistJogador.put(
                                        jogadorId,
                                        atual + 1
                                );
                            }
                        }
                    }

                    // ======================================================
                    // REMOVE GOLS
                    // ======================================================

                    for(String jogadorId : golsJogador.keySet()){

                        FirebaseHelper.getFirestore()

                                .collection("jogadores")

                                .document(jogadorId)

                                .update(

                                        "gols",

                                        FieldValue.increment(
                                                -golsJogador.get(jogadorId)
                                        )
                                );
                    }

                    // ======================================================
                    // REMOVE ASSISTÊNCIAS
                    // ======================================================

                    for(String jogadorId : assistJogador.keySet()){

                        FirebaseHelper.getFirestore()

                                .collection("jogadores")

                                .document(jogadorId)

                                .update(

                                        "assistencias",

                                        FieldValue.increment(
                                                -assistJogador.get(jogadorId)
                                        )
                                );
                    }

                    // ======================================================
                    // REMOVE JOGOS
                    // ======================================================

                    for(String jogadorId : jogadoresParticiparam){

                        FirebaseHelper.getFirestore()

                                .collection("jogadores")

                                .document(jogadorId)

                                .update(

                                        "jogos",

                                        FieldValue.increment(-1)
                                );
                    }

                    // ======================================================
                    // EXCLUI JOGO
                    // ======================================================

                    FirebaseHelper.getFirestore()

                            .collection("jogos")

                            .document(jogoId)

                            .delete()

                            .addOnSuccessListener(unused -> {

                                Toast.makeText(

                                        context,

                                        "Jogo excluído",

                                        Toast.LENGTH_SHORT

                                ).show();
                            });
                });
    }

    // ======================================================
    // VIEW HOLDER
    // ======================================================

    public static class MyViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtData;

        TextView txtNossoTime;

        TextView txtAdversario;

        TextView txtPlacar;

        TextView txtDetalhes;

        ImageView imgCasa;

        ImageView imgFora;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtData =
                    itemView.findViewById(R.id.txtData);

            txtNossoTime =
                    itemView.findViewById(R.id.txtNossoTime);

            txtAdversario =
                    itemView.findViewById(R.id.txtAdversario);

            txtPlacar =
                    itemView.findViewById(R.id.txtPlacar);

            txtDetalhes =
                    itemView.findViewById(R.id.txtDetalhes);

            imgCasa =
                    itemView.findViewById(R.id.imgCasa);

            imgFora =
                    itemView.findViewById(R.id.imgFora);
        }
    }
}