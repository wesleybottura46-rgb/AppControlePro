package com.example.appcontrolepro.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.*;
import android.view.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

import java.util.List;
import java.util.Map;

// ======================================================
// ADAPTER DE JOGADORES
// ======================================================
public class JogadoresAdapter extends RecyclerView.Adapter<JogadoresAdapter.ViewHolder>{

    Context context;
    List<Map<String,Object>> lista;

    public JogadoresAdapter(Context context, List<Map<String,Object>> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jogador, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        Map<String,Object> jogador = lista.get(position);

        String nome = (String) jogador.get("nome");

        // PROTEÇÃO DO ID (EVITA CRASH)
        Object idObj = jogador.get("id");
        if(idObj == null) return;

        String jogadorId = idObj.toString();

        Long gols = jogador.get("gols") == null ? 0 : (Long) jogador.get("gols");
        Long assist = jogador.get("assistencias") == null ? 0 : (Long) jogador.get("assistencias");

        holder.txtNome.setText(nome + " - ⚽ " + gols + " | 🎯 " + assist);

        holder.itemView.setOnClickListener(v -> {

            String[] opcoes = {"Editar", "Excluir"};

            new AlertDialog.Builder(context)
                    .setTitle(nome)
                    .setItems(opcoes, (dialog, which) -> {

                        // ==========================
                        // EDITAR
                        // ==========================
                        if(which == 0){

                            EditText input = new EditText(context);
                            input.setText(nome);

                            new AlertDialog.Builder(context)
                                    .setTitle("Editar jogador")
                                    .setView(input)

                                    .setPositiveButton("Salvar", (d, w) -> {

                                        String novoNome = input.getText().toString().trim();

                                        if(novoNome.isEmpty()){
                                            Toast.makeText(context,"Nome inválido",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        FirebaseHelper.getFirestore()
                                                .collection("jogadores")
                                                .document(jogadorId)
                                                .update("nome", novoNome);

                                        Toast.makeText(context,"Jogador atualizado",Toast.LENGTH_SHORT).show();
                                    })

                                    .setNegativeButton("Cancelar", null)
                                    .show();
                        }

                        // ==========================
                        // EXCLUIR
                        // ==========================
                        else{

                            new AlertDialog.Builder(context)
                                    .setTitle("Excluir jogador")
                                    .setMessage("Deseja excluir " + nome + "?")

                                    .setPositiveButton("Excluir", (d, w) -> {

                                        FirebaseHelper.getFirestore()
                                                .collection("jogadores")
                                                .document(jogadorId)
                                                .delete();

                                        Toast.makeText(context,"Jogador excluído",Toast.LENGTH_SHORT).show();
                                    })

                                    .setNegativeButton("Cancelar", null)
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

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNome;

        public ViewHolder(View itemView){
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
        }
    }
}
