// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA CONTEXTO DA TELA
import android.content.Context;

// IMPORTA INFLADOR DE XML
import android.view.LayoutInflater;

// IMPORTA VIEW
import android.view.View;

// IMPORTA GRUPO DE VIEWS
import android.view.ViewGroup;

// IMPORTA BOTÃO
import android.widget.Button;

// IMPORTA CHECKBOX
import android.widget.CheckBox;

// IMPORTA TEXTO
import android.widget.TextView;

// IMPORTA RECYCLER VIEW
import androidx.recyclerview.widget.RecyclerView;

// IMPORTA R DO PROJETO
import com.example.appcontrolepro.R;

// IMPORTA LISTA
import java.util.List;

// IMPORTA MAPA
import java.util.Map;

// ======================================================
// ADAPTER DA SÚMULA
// ======================================================
//
// ESSE ADAPTER:
//
// ✔ MOSTRA OS JOGADORES
// ✔ MARCA QUEM JOGOU
// ✔ CONTROLA GOLS
// ✔ CONTROLA ASSISTÊNCIAS
// ✔ LIMITA PELO PLACAR
//
// ======================================================

// CRIA A CLASSE DO ADAPTER
public class JogadorSumulaAdapter

        // HERDA DO RECYCLER VIEW
        extends RecyclerView.Adapter<JogadorSumulaAdapter.ViewHolder>{

    // ======================================================
    // CONTEXTO
    // ======================================================

    // GUARDA A TELA
    Context context;

    // ======================================================
    // LISTA
    // ======================================================

    // GUARDA LISTA DOS JOGADORES
    List<Map<String,Object>> lista;

    // ======================================================
    // CONSTRUTOR
    // ======================================================

    // MÉTODO CHAMADO AO CRIAR O ADAPTER
    public JogadorSumulaAdapter(

            // RECEBE CONTEXTO
            Context context,

            // RECEBE LISTA
            List<Map<String,Object>> lista
    ){

        // SALVA CONTEXTO
        this.context = context;

        // SALVA LISTA
        this.lista = lista;
    }

    // ======================================================
    // CRIA ITEM DA LISTA
    // ======================================================

    @Override
    public ViewHolder onCreateViewHolder(

            // VIEW PAI
            ViewGroup parent,

            // TIPO DA VIEW
            int viewType
    ){

        // ABRE O XML DO ITEM
        View view = LayoutInflater

                // PEGA CONTEXTO
                .from(parent.getContext())

                // INFLA XML
                .inflate(

                        // XML DO ITEM
                        R.layout.item_sumula_jogador,

                        // VIEW PAI
                        parent,

                        // NÃO ANEXA AUTOMATICAMENTE
                        false
                );

        // RETORNA HOLDER
        return new ViewHolder(view);
    }

    // ======================================================
    // DEFINE DADOS DO ITEM
    // ======================================================

    @Override
    public void onBindViewHolder(

            // HOLDER
            ViewHolder holder,

            // POSIÇÃO
            int position
    ){

        // ==================================================
        // PEGA JOGADOR
        // ==================================================

        // PEGA O JOGADOR DA POSIÇÃO
        Map<String,Object> jogador =
                lista.get(position);

        // ==================================================
        // PEGA NOME
        // ==================================================

        // PEGA NOME DO JOGADOR
        String nome =
                (String) jogador.get("nome");

        // ==================================================
        // MOSTRA NOME
        // ==================================================

        // COLOCA NOME NO CHECKBOX
        holder.checkJogou.setText(nome);

        // ==================================================
        // PEGA GOLS
        // ==================================================

        // ARRAY FINAL PARA PODER ALTERAR
        final int[] gols = {

                // VERIFICA SE EXISTE GOLS
                jogador.containsKey("golsPartida")

                        // PEGA VALOR
                        ? ((Number) jogador.get("golsPartida")).intValue()

                        // SENÃO ZERO
                        : 0
        };

        // ==================================================
        // PEGA ASSISTÊNCIAS
        // ==================================================

        // ARRAY FINAL PARA PODER ALTERAR
        final int[] assist = {

                // VERIFICA SE EXISTE ASSISTÊNCIAS
                jogador.containsKey("assistPartida")

                        // PEGA VALOR
                        ? ((Number) jogador.get("assistPartida")).intValue()

                        // SENÃO ZERO
                        : 0
        };

        // ==================================================
        // MOSTRA GOLS
        // ==================================================

        // MOSTRA QUANTIDADE DE GOLS
        holder.txtGols.setText(

                // CONVERTE PARA TEXTO
                String.valueOf(gols[0])
        );

        // ==================================================
        // MOSTRA ASSISTÊNCIAS
        // ==================================================

        // MOSTRA QUANTIDADE DE ASSISTÊNCIAS
        holder.txtAssist.setText(

                // CONVERTE PARA TEXTO
                String.valueOf(assist[0])
        );

        // ==================================================
        // REMOVE LISTENER ANTIGO
        // ==================================================

        // EVITA BUG DE RECYCLER VIEW
        holder.checkJogou.setOnCheckedChangeListener(null);

        // ==================================================
        // DEFINE ESTADO DO CHECKBOX
        // ==================================================

        // DEFINE SE ESTÁ MARCADO
        holder.checkJogou.setChecked(

                // VERIFICA SE EXISTE CHAVE
                jogador.containsKey("jogou")

                        // VERIFICA SE É TRUE
                        && (boolean) jogador.get("jogou")
        );

        // ==================================================
        // NOVO LISTENER
        // ==================================================

        // QUANDO MARCAR OU DESMARCAR
        holder.checkJogou.setOnCheckedChangeListener(

                // RECEBE EVENTO
                (buttonView, isChecked) -> {

                    // SALVA ESTADO
                    jogador.put("jogou", isChecked);

                    // ==================================================
                    // SE DESMARCOU
                    // ==================================================

                    if(!isChecked){

                        // ZERA GOLS
                        gols[0] = 0;

                        // ZERA ASSISTÊNCIAS
                        assist[0] = 0;

                        // SALVA GOLS
                        jogador.put("golsPartida", 0);

                        // SALVA ASSISTÊNCIAS
                        jogador.put("assistPartida", 0);

                        // MOSTRA ZERO GOLS
                        holder.txtGols.setText("0");

                        // MOSTRA ZERO ASSISTÊNCIAS
                        holder.txtAssist.setText("0");
                    }
                }
        );

        // ==================================================
        // BOTÃO MAIS GOL
        // ==================================================

        holder.btnMaisGol.setOnClickListener(v -> {

            // ==================================================
            // VERIFICA SE JOGOU
            // ==================================================

            // SE NÃO MARCOU CHECKBOX
            if(!holder.checkJogou.isChecked()){

                // PARA EXECUÇÃO
                return;
            }

            // ==================================================
            // TOTAL DE GOLS
            // ==================================================

            // COMEÇA COM ZERO
            int totalGols = 0;

            // ==================================================
            // SOMA TODOS OS GOLS
            // ==================================================

            for(Map<String,Object> j : lista){

                // SOMA GOLS
                totalGols += j.containsKey("golsPartida")

                        // PEGA VALOR
                        ? ((Number) j.get("golsPartida")).intValue()

                        // SENÃO ZERO
                        : 0;
            }

            // ==================================================
            // PEGA ACTIVITY
            // ==================================================

            // CONVERTE CONTEXTO
            SumulaActivity activity =
                    (SumulaActivity) context;

            // ==================================================
            // LIMITA PELO PLACAR
            // ==================================================

            // SE PASSOU DO PLACAR
            if(totalGols >= activity.golsNosso){

                // PARA EXECUÇÃO
                return;
            }

            // ==================================================
            // SOMA GOL
            // ==================================================

            // ADICIONA 1
            gols[0]++;

            // ==================================================
            // SALVA GOLS
            // ==================================================

            jogador.put("golsPartida", gols[0]);

            // ==================================================
            // ATUALIZA TEXTO
            // ==================================================

            holder.txtGols.setText(

                    String.valueOf(gols[0])
            );
        });

        // ==================================================
        // BOTÃO MENOS GOL
        // ==================================================

        holder.btnMenosGol.setOnClickListener(v -> {

            // ==================================================
            // VERIFICA SE É MAIOR QUE ZERO
            // ==================================================

            if(gols[0] > 0){

                // REMOVE 1
                gols[0]--;

                // SALVA
                jogador.put("golsPartida", gols[0]);

                // ATUALIZA TEXTO
                holder.txtGols.setText(

                        String.valueOf(gols[0])
                );
            }
        });

        // ==================================================
        // BOTÃO MAIS ASSISTÊNCIA
        // ==================================================

        holder.btnMaisAssist.setOnClickListener(v -> {

            // ==================================================
            // VERIFICA SE JOGOU
            // ==================================================

            if(!holder.checkJogou.isChecked()){

                return;
            }

            // ==================================================
            // TOTAL ASSISTÊNCIAS
            // ==================================================

            int totalAssist = 0;

            // ==================================================
            // SOMA TODAS
            // ==================================================

            for(Map<String,Object> j : lista){

                totalAssist += j.containsKey("assistPartida")

                        ? ((Number) j.get("assistPartida")).intValue()

                        : 0;
            }

            // ==================================================
            // PEGA ACTIVITY
            // ==================================================

            SumulaActivity activity =
                    (SumulaActivity) context;

            // ==================================================
            // LIMITA PELO PLACAR
            // ==================================================

            if(totalAssist >= activity.golsNosso){

                return;
            }

            // ==================================================
            // SOMA ASSISTÊNCIA
            // ==================================================

            assist[0]++;

            // SALVA
            jogador.put("assistPartida", assist[0]);

            // ATUALIZA TEXTO
            holder.txtAssist.setText(

                    String.valueOf(assist[0])
            );
        });

        // ==================================================
        // BOTÃO MENOS ASSISTÊNCIA
        // ==================================================

        holder.btnMenosAssist.setOnClickListener(v -> {

            // ==================================================
            // VERIFICA SE É MAIOR QUE ZERO
            // ==================================================

            if(assist[0] > 0){

                // REMOVE 1
                assist[0]--;

                // SALVA
                jogador.put("assistPartida", assist[0]);

                // ATUALIZA TEXTO
                holder.txtAssist.setText(

                        String.valueOf(assist[0])
                );
            }
        });
    }

    // ======================================================
    // TOTAL DE ITENS
    // ======================================================

    @Override
    public int getItemCount(){

        // RETORNA TAMANHO DA LISTA
        return lista.size();
    }

    // ======================================================
    // VIEW HOLDER
    // ======================================================

    static class ViewHolder extends RecyclerView.ViewHolder{

        // ==================================================
        // CHECKBOX
        // ==================================================

        CheckBox checkJogou;

        // ==================================================
        // BOTÕES DE GOL
        // ==================================================

        Button btnMaisGol;

        Button btnMenosGol;

        // ==================================================
        // BOTÕES DE ASSISTÊNCIA
        // ==================================================

        Button btnMaisAssist;

        Button btnMenosAssist;

        // ==================================================
        // TEXTOS
        // ==================================================

        TextView txtGols;

        TextView txtAssist;

        // ==================================================
        // CONSTRUTOR
        // ==================================================

        public ViewHolder(View itemView){

            // CHAMA CONSTRUTOR PAI
            super(itemView);

            // ==================================================
            // CONECTA XML COM JAVA
            // ==================================================

            // CHECKBOX
            checkJogou =
                    itemView.findViewById(R.id.checkJogou);

            // BOTÃO MAIS GOL
            btnMaisGol =
                    itemView.findViewById(R.id.btnMaisGol);

            // BOTÃO MENOS GOL
            btnMenosGol =
                    itemView.findViewById(R.id.btnMenosGol);

            // BOTÃO MAIS ASSISTÊNCIA
            btnMaisAssist =
                    itemView.findViewById(R.id.btnMaisAssist);

            // BOTÃO MENOS ASSISTÊNCIA
            btnMenosAssist =
                    itemView.findViewById(R.id.btnMenosAssist);

            // TEXTO GOLS
            txtGols =
                    itemView.findViewById(R.id.txtGols);

            // TEXTO ASSISTÊNCIAS
            txtAssist =
                    itemView.findViewById(R.id.txtAssist);
        }
    }
}