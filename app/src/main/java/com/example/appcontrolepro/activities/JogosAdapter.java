package com.example.appcontrolepro.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.activities.EditarJogoActivity;
import com.example.appcontrolepro.activities.SumulaActivity;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.google.firebase.firestore.FieldValue;

import java.util.List;
import java.util.Map;

// ======================================================
// ADAPTER DE JOGOS (VERSÃO FINAL PROFISSIONAL)
// ======================================================
public class JogosAdapter extends RecyclerView.Adapter<JogosAdapter.ViewHolder>{

    List<Map<String,Object>> lista;
    Context context;

    public JogosAdapter(Context context, List<Map<String,Object>> lista){
        this.context = context;
        this.lista = lista;
    }

    // ==========================
    // CRIA ITEM
    // ==========================
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jogo, parent, false);
        return new ViewHolder(view);
    }

    // ==========================
    // PREENCHE ITEM
    // ==========================
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        Map<String,Object> jogo = lista.get(position);

        String data = (String) jogo.get("data");
        String adversario = (String) jogo.get("adversario");
        String jogoId = (String) jogo.get("id");

        Long nosso = jogo.get("placarNosso") == null ? 0 : (Long) jogo.get("placarNosso");
        Long deles = jogo.get("placarAdversario") == null ? 0 : (Long) jogo.get("placarAdversario");

        // ==========================
        // MOSTRAR DADOS
        // ==========================
        holder.txtData.setText("📅 " + (data == null ? "-" : data));
        holder.txtAdversario.setText("⚽ " + (adversario == null ? "Sem nome" : adversario));

        // NÃO MOSTRAR 0x0 SEM SÚMULA
        if(jogo.get("placarNosso") == null && jogo.get("placarAdversario") == null){
            holder.txtPlacar.setText("Sem resultado");
        }else{
            holder.txtPlacar.setText("🏁 " + nosso + " x " + deles);
        }

        // ==========================
        // VERIFICA SE TEM SÚMULA
        // ==========================
        List<Map<String,Object>> eventos =
                (List<Map<String,Object>>) jogo.get("eventos");

        String nomeSumula = (eventos == null || eventos.isEmpty())
                ? "Cadastrar Súmula"
                : "Editar Súmula";

        // ==========================
        // CLIQUE → MENU
        // ==========================
        holder.itemView.setOnClickListener(v -> {

            String[] opcoes = {
                    nomeSumula,
                    "Editar Jogo",
                    "Excluir Jogo"
            };

            new AlertDialog.Builder(context)
                    .setTitle("Opções do Jogo")
                    .setItems(opcoes, (dialog, which) -> {

                        // ==========================
                        // SÚMULA
                        // ==========================
                        if(which == 0){
                            Intent i = new Intent(context, SumulaActivity.class);
                            i.putExtra("jogoId", jogoId);
                            context.startActivity(i);
                        }

                        // ==========================
                        // EDITAR JOGO
                        // ==========================
                        else if(which == 1){
                            Intent i = new Intent(context, EditarJogoActivity.class);
                            i.putExtra("jogoId", jogoId);
                            context.startActivity(i);
                        }

                        // ==========================
                        // EXCLUIR JOGO (COM ROLLBACK)
                        // ==========================
                        else{

                            FirebaseHelper.getFirestore()
                                    .collection("jogos")
                                    .document(jogoId)
                                    .get()
                                    .addOnSuccessListener(doc -> {

                                        List<Map<String,Object>> eventosDoc =
                                                (List<Map<String,Object>>) doc.get("eventos");

                                        // 🔥 REMOVE ESTATÍSTICAS
                                        if(eventosDoc != null){
                                            for(Map<String,Object> ev : eventosDoc){

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

                                        // 🔥 EXCLUI JOGO
                                        FirebaseHelper.getFirestore()
                                                .collection("jogos")
                                                .document(jogoId)
                                                .delete();

                                        Toast.makeText(context,"Jogo excluído",Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .show();
        });
    }

    // ==========================
    // TAMANHO DA LISTA
    // ==========================
    @Override
    public int getItemCount(){
        return lista.size();
    }

    // ==========================
    // VIEW HOLDER
    // ==========================
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtData;
        TextView txtAdversario;
        TextView txtPlacar;

        public ViewHolder(View itemView){
            super(itemView);

            txtData = itemView.findViewById(R.id.txtData);
            txtAdversario = itemView.findViewById(R.id.txtAdversario);
            txtPlacar = itemView.findViewById(R.id.txtPlacar);
        }
    }
}