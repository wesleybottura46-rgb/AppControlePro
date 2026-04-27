package com.example.appcontrolepro.activities;

// IMPORTA CONTEXTO
import android.content.Context;

// IMPORTA VIEWS
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// IMPORTA COMPONENTES
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

// IMPORTA RECYCLER VIEW
import androidx.recyclerview.widget.RecyclerView;

// IMPORTA R
import com.example.appcontrolepro.R;

// IMPORTA LISTAS
import java.util.List;
import java.util.Map;

// ======================================================
// ADAPTER DA SÚMULA
// ======================================================
//
// RESPONSÁVEL POR:
//
// ✔ MOSTRAR JOGADORES
// ✔ MARCAR QUEM JOGOU
// ✔ CONTROLAR GOLS
// ✔ CONTROLAR ASSISTÊNCIAS
// ✔ LIMITAR PELO PLACAR
//
// ======================================================
public class JogadorSumulaAdapter
        extends RecyclerView.Adapter<JogadorSumulaAdapter.ViewHolder>{

    // CONTEXTO
    Context context;

    // LISTA DOS JOGADORES
    List<Map<String,Object>> lista;

    // ======================================================
    // CONSTRUTOR
    // ======================================================
    public JogadorSumulaAdapter(
            Context context,
            List<Map<String,Object>> lista
    ){

        // SALVA CONTEXTO
        this.context = context;

        // SALVA LISTA
        this.lista = lista;
    }

    // ======================================================
    // CRIA ITEM
    // ======================================================
    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ){

        // ABRE XML DA SÚMULA
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_sumula_jogador,
                        parent,
                        false
                );

        // RETORNA HOLDER
        return new ViewHolder(view);
    }

    // ======================================================
    // MOSTRA DADOS
    // ======================================================
    @Override
    public void onBindViewHolder(
            ViewHolder holder,
            int position
    ){

        // PEGA JOGADOR
        Map<String,Object> jogador = lista.get(position);

        // PEGA NOME
        String nome = (String) jogador.get("nome");

        // MOSTRA NOME
        holder.checkJogou.setText(nome);

        // ==================================================
        // PEGA GOLS
        // ==================================================
        final int[] gols = {

                jogador.containsKey("golsPartida")
                        ? ((Number) jogador.get("golsPartida")).intValue()
                        : 0
        };

        // ==================================================
        // PEGA ASSISTÊNCIAS
        // ==================================================
        final int[] assist = {

                jogador.containsKey("assistPartida")
                        ? ((Number) jogador.get("assistPartida")).intValue()
                        : 0
        };

        // ==================================================
        // MOSTRA GOLS
        // ==================================================
        holder.txtGols.setText(
                String.valueOf(gols[0])
        );

        // ==================================================
        // MOSTRA ASSISTÊNCIAS
        // ==================================================
        holder.txtAssist.setText(
                String.valueOf(assist[0])
        );

        // ==================================================
        // REMOVE LISTENER ANTIGO
        // ==================================================
        holder.checkJogou.setOnCheckedChangeListener(null);

        // ==================================================
        // DEFINE ESTADO DO CHECKBOX
        // ==================================================
        holder.checkJogou.setChecked(

                jogador.containsKey("jogou")
                        && (boolean) jogador.get("jogou")
        );

        // ==================================================
        // NOVO LISTENER
        // ==================================================
        holder.checkJogou.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    // SALVA ESTADO
                    jogador.put("jogou", isChecked);

                    // SE DESMARCOU
                    if(!isChecked){

                        // ZERA GOLS
                        gols[0] = 0;

                        // ZERA ASSISTÊNCIAS
                        assist[0] = 0;

                        // SALVA
                        jogador.put("golsPartida", 0);

                        // SALVA
                        jogador.put("assistPartida", 0);

                        // ATUALIZA TELA
                        holder.txtGols.setText("0");

                        holder.txtAssist.setText("0");
                    }
                }
        );

        // ==================================================
        // MAIS GOL
        // ==================================================
        holder.btnMaisGol.setOnClickListener(v -> {

            // SÓ FUNCIONA SE JOGOU
            if(!holder.checkJogou.isChecked()){

                return;
            }

            // TOTAL DE GOLS
            int totalGols = 0;

            // SOMA TODOS OS GOLS
            for(Map<String,Object> j : lista){

                totalGols += j.containsKey("golsPartida")
                        ? ((Number) j.get("golsPartida")).intValue()
                        : 0;
            }

            // PEGA ACTIVITY
            SumulaActivity activity =
                    (SumulaActivity) context;

            // LIMITA PELO PLACAR
            if(totalGols >= activity.golsNosso){

                return;
            }

            // SOMA GOL
            gols[0]++;

            // SALVA
            jogador.put("golsPartida", gols[0]);

            // ATUALIZA
            holder.txtGols.setText(
                    String.valueOf(gols[0])
            );
        });

        // ==================================================
        // MENOS GOL
        // ==================================================
        holder.btnMenosGol.setOnClickListener(v -> {

            // SE MAIOR QUE ZERO
            if(gols[0] > 0){

                // REMOVE
                gols[0]--;

                // SALVA
                jogador.put("golsPartida", gols[0]);

                // ATUALIZA
                holder.txtGols.setText(
                        String.valueOf(gols[0])
                );
            }
        });

        // ==================================================
        // MAIS ASSISTÊNCIA
        // ==================================================
        holder.btnMaisAssist.setOnClickListener(v -> {

            // SÓ FUNCIONA SE JOGOU
            if(!holder.checkJogou.isChecked()){

                return;
            }

            // TOTAL ASSISTÊNCIAS
            int totalAssist = 0;

            // SOMA TODAS
            for(Map<String,Object> j : lista){

                totalAssist += j.containsKey("assistPartida")
                        ? ((Number) j.get("assistPartida")).intValue()
                        : 0;
            }

            // PEGA ACTIVITY
            SumulaActivity activity =
                    (SumulaActivity) context;

            // LIMITA PELO PLACAR
            if(totalAssist >= activity.golsNosso){

                return;
            }

            // SOMA ASSIST
            assist[0]++;

            // SALVA
            jogador.put("assistPartida", assist[0]);

            // ATUALIZA
            holder.txtAssist.setText(
                    String.valueOf(assist[0])
            );
        });

        // ==================================================
        // MENOS ASSISTÊNCIA
        // ==================================================
        holder.btnMenosAssist.setOnClickListener(v -> {

            // SE MAIOR QUE ZERO
            if(assist[0] > 0){

                // REMOVE
                assist[0]--;

                // SALVA
                jogador.put("assistPartida", assist[0]);

                // ATUALIZA
                holder.txtAssist.setText(
                        String.valueOf(assist[0])
                );
            }
        });
    }

    // ======================================================
    // QUANTIDADE DE ITENS
    // ======================================================
    @Override
    public int getItemCount(){

        return lista.size();
    }

    // ======================================================
    // VIEW HOLDER
    // ======================================================
    static class ViewHolder extends RecyclerView.ViewHolder{

        // CHECKBOX
        CheckBox checkJogou;

        // BOTÕES GOL
        Button btnMaisGol;
        Button btnMenosGol;

        // BOTÕES ASSIST
        Button btnMaisAssist;
        Button btnMenosAssist;

        // TEXTOS
        TextView txtGols;
        TextView txtAssist;

        // CONSTRUTOR
        public ViewHolder(View itemView){

            super(itemView);

            // LIGA CHECKBOX
            checkJogou =
                    itemView.findViewById(R.id.checkJogou);

            // LIGA BOTÕES GOL
            btnMaisGol =
                    itemView.findViewById(R.id.btnMaisGol);

            btnMenosGol =
                    itemView.findViewById(R.id.btnMenosGol);

            // LIGA BOTÕES ASSIST
            btnMaisAssist =
                    itemView.findViewById(R.id.btnMaisAssist);

            btnMenosAssist =
                    itemView.findViewById(R.id.btnMenosAssist);

            // LIGA TEXTOS
            txtGols =
                    itemView.findViewById(R.id.txtGols);

            txtAssist =
                    itemView.findViewById(R.id.txtAssist);
        }
    }
}