package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// ALERT DIALOG
import android.app.AlertDialog;

// CONTEXT
import android.content.Context;

// LAYOUT
import android.view.LayoutInflater;

// VIEW
import android.view.View;

// VIEW GROUP
import android.view.ViewGroup;

// EDIT TEXT
import android.widget.EditText;

// IMAGE VIEW
import android.widget.ImageView;

// TEXT VIEW
import android.widget.TextView;

// TOAST
import android.widget.Toast;

// RECYCLER VIEW
import androidx.recyclerview.widget.RecyclerView;

// GLIDE
import com.bumptech.glide.Glide;

// XML
import com.example.appcontrolepro.R;

// FIREBASE
import com.example.appcontrolepro.database.FirebaseHelper;

// COLLECTIONS
import java.util.Collections;

// LIST
import java.util.List;

// MAP
import java.util.Map;

// ======================================================
// ADAPTER JOGADORES
// ======================================================

// ESTA CLASSE MOSTRA:
//
// ✔ NOME JOGADOR
// ✔ GOLS
// ✔ ASSISTÊNCIAS
// ✔ JOGOS
// ✔ EMBLEMA DO TIME
// ✔ EDITAR
// ✔ EXCLUIR

public class JogadoresAdapter
        extends RecyclerView.Adapter<JogadoresAdapter.ViewHolder>{

    // ======================================================
    // CONTEXTO
    // ======================================================

    Context context;

    // ======================================================
    // LISTA
    // ======================================================

    List<Map<String,Object>> lista;

    // ======================================================
    // URL EMBLEMA
    // ======================================================

    String emblemaTime = "";

    // ======================================================
    // CONSTRUTOR
    // ======================================================

    public JogadoresAdapter(

            Context context,

            List<Map<String, Object>> lista
    ){

        // SALVA CONTEXTO
        this.context = context;

        // SALVA LISTA
        this.lista = lista;

        // ======================================================
        // ORDENA POR GOLS
        // ======================================================

        Collections.sort(lista, (a, b) -> {

            Long golsA =

                    a.get("gols") == null

                            ? 0

                            : (Long) a.get("gols");

            Long golsB =

                    b.get("gols") == null

                            ? 0

                            : (Long) b.get("gols");

            return golsB.compareTo(golsA);
        });

        // ======================================================
        // CARREGA EMBLEMA TIME
        // ======================================================

        carregarEmblema();
    }

    // ======================================================
    // CARREGA EMBLEMA
    // ======================================================

    private void carregarEmblema(){

        // PEGA PRIMEIRO JOGADOR
        if(lista.isEmpty()) return;

        // TIME ID
        String timeId =

                (String) lista.get(0).get("timeId");

        // PROTEÇÃO
        if(timeId == null) return;

        // FIREBASE
        FirebaseHelper.getFirestore()

                .collection("times")

                .document(timeId)

                .get()

                .addOnSuccessListener(doc -> {

                    // SALVA URL
                    emblemaTime =
                            doc.getString("emblema");

                    // ATUALIZA LISTA
                    notifyDataSetChanged();
                });
    }

    // ======================================================
    // CRIA ITEM
    // ======================================================

    @Override
    public ViewHolder onCreateViewHolder(

            ViewGroup parent,

            int viewType
    ){

        // INFLA XML
        View view = LayoutInflater

                .from(parent.getContext())

                .inflate(

                        R.layout.item_jogador,

                        parent,

                        false
                );

        // RETORNA HOLDER
        return new ViewHolder(view);
    }

    // ======================================================
    // DEFINE DADOS
    // ======================================================

    @Override
    public void onBindViewHolder(

            ViewHolder holder,

            int position
    ){

        // ======================================================
        // JOGADOR
        // ======================================================

        Map<String,Object> jogador =
                lista.get(position);

        // ======================================================
        // NOME
        // ======================================================

        String nome =
                (String) jogador.get("nome");

        // ======================================================
        // ID
        // ======================================================

        Object idObj =
                jogador.get("id");

        // PROTEÇÃO
        if(idObj == null){
            return;
        }

        // STRING ID
        String jogadorId =
                idObj.toString();

        // ======================================================
        // GOLS
        // ======================================================

        Long gols =

                jogador.get("gols") == null

                        ? 0

                        : (Long) jogador.get("gols");

        // ======================================================
        // ASSISTÊNCIAS
        // ======================================================

        Long assist =

                jogador.get("assistencias") == null

                        ? 0

                        : (Long) jogador.get("assistencias");

        // ======================================================
        // JOGOS
        // ======================================================

        Long jogos =

                jogador.get("jogos") == null

                        ? 0

                        : (Long) jogador.get("jogos");

        // ======================================================
        // NOME
        // ======================================================

        holder.txtNome.setText(nome);

        // ======================================================
        // POSIÇÃO
        // ======================================================

        holder.txtPosicao.setText("Jogador");

        // ======================================================
        // GOLS
        // ======================================================

        holder.txtGols.setText(
                String.valueOf(gols)
        );

        // ======================================================
        // ASSISTÊNCIAS
        // ======================================================

        holder.txtAssist.setText(
                String.valueOf(assist)
        );

        // ======================================================
        // JOGOS
        // ======================================================

        holder.txtJogos.setText(
                String.valueOf(jogos)
        );

        // ======================================================
        // EMBLEMA
        // ======================================================

        if(emblemaTime != null){

            Glide.with(context)

                    .load(emblemaTime)

                    .into(holder.imgTimeJogador);
        }

        // ======================================================
        // CLICK ITEM
        // ======================================================

        holder.itemView.setOnClickListener(v -> {

            // ======================================================
            // OPÇÕES
            // ======================================================

            String[] opcoes = {

                    "Editar",

                    "Excluir"
            };

            // ======================================================
            // DIALOG
            // ======================================================

            new AlertDialog.Builder(context)

                    .setTitle(nome)

                    .setItems(opcoes,
                            (dialog, which) -> {

                                // ======================================================
                                // EDITAR
                                // ======================================================

                                if(which == 0){

                                    // INPUT
                                    EditText input =
                                            new EditText(context);

                                    // DEFINE NOME
                                    input.setText(nome);

                                    // DIALOG
                                    new AlertDialog.Builder(context)

                                            .setTitle(
                                                    "Editar jogador"
                                            )

                                            .setView(input)

                                            .setPositiveButton(

                                                    "Salvar",

                                                    (d, w) -> {

                                                        // NOVO NOME
                                                        String novoNome =

                                                                input.getText()

                                                                        .toString()

                                                                        .trim();

                                                        // VALIDAÇÃO
                                                        if(novoNome.isEmpty()){

                                                            Toast.makeText(

                                                                    context,

                                                                    "Nome inválido",

                                                                    Toast.LENGTH_SHORT

                                                            ).show();

                                                            return;
                                                        }

                                                        // FIREBASE
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

                                                        // TOAST
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

                                // ======================================================
                                // EXCLUIR
                                // ======================================================

                                else{

                                    // DIALOG
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

                                                        // FIREBASE
                                                        FirebaseHelper

                                                                .getFirestore()

                                                                .collection(
                                                                        "jogadores"
                                                                )

                                                                .document(
                                                                        jogadorId
                                                                )

                                                                .delete();

                                                        // TOAST
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

    // ======================================================
    // TOTAL ITENS
    // ======================================================

    @Override
    public int getItemCount(){

        return lista.size();
    }

    // ======================================================
    // VIEW HOLDER
    // ======================================================

    static class ViewHolder
            extends RecyclerView.ViewHolder{

        // ======================================================
        // EMBLEMA
        // ======================================================

        ImageView imgTimeJogador;

        // ======================================================
        // TEXTOS
        // ======================================================

        TextView txtNome;

        TextView txtPosicao;

        TextView txtJogos;

        TextView txtAssist;

        TextView txtGols;

        // ======================================================
        // CONSTRUTOR
        // ======================================================

        public ViewHolder(View itemView){

            super(itemView);

            // ======================================================
            // XML → JAVA
            // ======================================================

            imgTimeJogador =
                    itemView.findViewById(
                            R.id.imgTimeJogador
                    );

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