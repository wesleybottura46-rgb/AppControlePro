package com.example.appcontrolepro.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JogadoresAdapter
        extends RecyclerView.Adapter<JogadoresAdapter.ViewHolder>{

    Context context;

    List<Map<String,Object>> lista;

    // CONSTRUTOR
    public JogadoresAdapter(
            Context context,
            List<Map<String,Object>> lista
    ){

        this.context = context;

        this.lista = lista;

        // ORDENA POR GOLS
        Collections.sort(lista, (a, b) -> {

            Long golsA = a.get("gols") == null
                    ? 0
                    : (Long) a.get("gols");

            Long golsB = b.get("gols") == null
                    ? 0
                    : (Long) b.get("gols");

            return golsB.compareTo(golsA);
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ){

        View view = LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.item_jogador,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            ViewHolder holder,
            int position
    ){

        Map<String,Object> jogador =
                lista.get(position);

        String nome =
                (String) jogador.get("nome");

        Object idObj =
                jogador.get("id");

        if(idObj == null){
            return;
        }

        String jogadorId =
                idObj.toString();

        Long gols =
                jogador.get("gols") == null
                        ? 0
                        : (Long) jogador.get("gols");

        Long assist =
                jogador.get("assistencias") == null
                        ? 0
                        : (Long) jogador.get("assistencias");

        Long jogos =
                jogador.get("jogos") == null
                        ? 0
                        : (Long) jogador.get("jogos");

        // NOME
        holder.txtNome.setText(nome);

        // POSIÇÃO
        holder.txtPosicao.setText("Jogador");

        // GOLS
        holder.txtGols.setText(
                String.valueOf(gols)
        );

        // ASSISTÊNCIAS
        holder.txtAssist.setText(
                String.valueOf(assist)
        );

        // JOGOS
        holder.txtJogos.setText(
                String.valueOf(jogos)
        );

        // CLIQUE
        holder.itemView.setOnClickListener(v -> {

            String[] opcoes = {
                    "Editar",
                    "Excluir"
            };

            new AlertDialog.Builder(context)
                    .setTitle(nome)
                    .setItems(opcoes,
                            (dialog, which) -> {

                                // EDITAR
                                if(which == 0){

                                    EditText input =
                                            new EditText(context);

                                    input.setText(nome);

                                    new AlertDialog.Builder(context)
                                            .setTitle(
                                                    "Editar jogador"
                                            )
                                            .setView(input)

                                            .setPositiveButton(
                                                    "Salvar",
                                                    (d, w) -> {

                                                        String novoNome =
                                                                input.getText()
                                                                        .toString()
                                                                        .trim();

                                                        if(novoNome.isEmpty()){

                                                            Toast.makeText(
                                                                    context,
                                                                    "Nome inválido",
                                                                    Toast.LENGTH_SHORT
                                                            ).show();

                                                            return;
                                                        }

                                                        FirebaseHelper
                                                                .getFirestore()
                                                                .collection(
                                                                        "jogadores"
                                                                )
                                                                .document(
                                                                        jogadorId
                                                                )
                                                                .update(
                                                                        "nome",
                                                                        novoNome
                                                                );

                                                        Toast.makeText(
                                                                context,
                                                                "Jogador atualizado",
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                    })

                                            .setNegativeButton(
                                                    "Cancelar",
                                                    null
                                            )
                                            .show();
                                }

                                // EXCLUIR
                                else{

                                    new AlertDialog.Builder(context)
                                            .setTitle(
                                                    "Excluir jogador"
                                            )

                                            .setMessage(
                                                    "Deseja excluir "
                                                            + nome
                                                            + "?"
                                            )

                                            .setPositiveButton(
                                                    "Excluir",
                                                    (d, w) -> {

                                                        FirebaseHelper
                                                                .getFirestore()
                                                                .collection(
                                                                        "jogadores"
                                                                )
                                                                .document(
                                                                        jogadorId
                                                                )
                                                                .delete();

                                                        Toast.makeText(
                                                                context,
                                                                "Jogador excluído",
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                    })

                                            .setNegativeButton(
                                                    "Cancelar",
                                                    null
                                            )

                                            .show();
                                }
                            })

                    .show();
        });
    }

    @Override
    public int getItemCount(){

        return lista.size();
    }

    // VIEW HOLDER
    static class ViewHolder
            extends RecyclerView.ViewHolder{

        TextView txtNome;
        TextView txtPosicao;

        TextView txtJogos;
        TextView txtAssist;
        TextView txtGols;

        public ViewHolder(View itemView){

            super(itemView);

            txtNome =
                    itemView.findViewById(
                            R.id.txtNome
                    );

            txtPosicao =
                    itemView.findViewById(
                            R.id.txtPosicao
                    );

            txtJogos =
                    itemView.findViewById(
                            R.id.txtJogos
                    );

            txtAssist =
                    itemView.findViewById(
                            R.id.txtAssist
                    );

            txtGols =
                    itemView.findViewById(
                            R.id.txtGols
                    );
        }
    }
}