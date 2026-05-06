package com.example.appcontrolepro.activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.models.Movimentacao;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class MovimentacaoAdapter
        extends RecyclerView.Adapter<MovimentacaoAdapter.ViewHolder>{

    List<Movimentacao> lista;

    FirebaseFirestore db =
            FirebaseFirestore.getInstance();

    public MovimentacaoAdapter(
            List<Movimentacao> lista
    ){

        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_movimentacao,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Movimentacao movimentacao =
                lista.get(position);

        holder.txtDescricao.setText(
                movimentacao.getDescricao()
        );

        holder.txtTipo.setText(
                movimentacao.getTipo()
        );

        holder.txtValor.setText(
                "R$ " + movimentacao.getValor()
        );

        // COR
        if("Entrada".equals(movimentacao.getTipo())){

            holder.txtValor.setTextColor(
                    Color.parseColor("#2E7D32")
            );

        }else{

            holder.txtValor.setTextColor(
                    Color.parseColor("#D32F2F")
            );
        }

        // EXCLUIR
        holder.btnExcluir.setOnClickListener(v -> {

            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Excluir")
                    .setMessage("Deseja excluir essa movimentação?")
                    .setPositiveButton("Excluir", (d,w) -> {

                        excluirMovimentacao(movimentacao);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    // EXCLUIR
    private void excluirMovimentacao(
            Movimentacao movimentacao
    ){

        db.collection("movimentacoes")
                .document(movimentacao.getId())
                .delete();

        // ATUALIZA CAIXA
        db.collection("caixa")
                .document("financeiro")
                .get()
                .addOnSuccessListener(document -> {

                    double saldo = 0;
                    double entradas = 0;
                    double saidas = 0;

                    Double saldoDb =
                            document.getDouble("saldo");

                    Double entradasDb =
                            document.getDouble("entradas");

                    Double saidasDb =
                            document.getDouble("saidas");

                    if(saldoDb != null) saldo = saldoDb;
                    if(entradasDb != null) entradas = entradasDb;
                    if(saidasDb != null) saidas = saidasDb;

                    // ENTRADA
                    if("Entrada".equals(movimentacao.getTipo())){

                        saldo -= movimentacao.getValor();
                        entradas -= movimentacao.getValor();
                    }

                    // SAÍDA
                    else{

                        saldo += movimentacao.getValor();
                        saidas -= movimentacao.getValor();
                    }

                    HashMap<String,Object> dados =
                            new HashMap<>();

                    dados.put("saldo", saldo);
                    dados.put("entradas", entradas);
                    dados.put("saidas", saidas);

                    db.collection("caixa")
                            .document("financeiro")
                            .set(dados);
                });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder{

        TextView txtDescricao;
        TextView txtTipo;
        TextView txtValor;

        Button btnExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDescricao =
                    itemView.findViewById(R.id.txtDescricao);

            txtTipo =
                    itemView.findViewById(R.id.txtTipo);

            txtValor =
                    itemView.findViewById(R.id.txtValor);

            btnExcluir =
                    itemView.findViewById(R.id.btnExcluir);
        }
    }
}
