package com.example.appcontrolepro.activities;

// ==========================================
// IMPORTAÇÕES
// ==========================================

// COR DE TEXTO
import android.graphics.Color;

// INFLAR XML
import android.view.LayoutInflater;

// VIEW
import android.view.View;

// VIEW GROUP
import android.view.ViewGroup;

// BOTÃO
import android.widget.Button;

// CAMPO TEXTO
import android.widget.EditText;

// TEXTO
import android.widget.TextView;

// MENSAGEM
import android.widget.Toast;

// NÃO NULO
import androidx.annotation.NonNull;

// RECYCLERVIEW
import androidx.recyclerview.widget.RecyclerView;

// XMLS
import com.example.appcontrolepro.R;

// MODEL
import com.example.appcontrolepro.models.Mensalidade;

// FIREBASE
import com.google.firebase.firestore.FirebaseFirestore;

// MERGE FIREBASE
import com.google.firebase.firestore.SetOptions;

// HASHMAP
import java.util.HashMap;

// LISTA
import java.util.List;

// ==========================================
// ADAPTER MENSALIDADES
// ==========================================

public class MensalidadeAdapter
        extends RecyclerView.Adapter<MensalidadeAdapter.ViewHolder> {

    // ==========================================
    // LISTA DE MENSALIDADES
    // ==========================================

    List<Mensalidade> lista;

    // ==========================================
    // FIREBASE
    // ==========================================

    FirebaseFirestore db =
            FirebaseFirestore.getInstance();

    // ==========================================
    // CONSTRUTOR
    // ==========================================

    public MensalidadeAdapter(
            List<Mensalidade> lista
    ) {

        // RECEBE LISTA
        this.lista = lista;
    }

    // ==========================================
    // CRIA ITEM XML
    // ==========================================

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(

            @NonNull ViewGroup parent,

            int viewType
    ) {

        // INFLA XML
        View view = LayoutInflater

                .from(parent.getContext())

                .inflate(

                        R.layout.item_mensalidade,

                        parent,

                        false
                );

        // RETORNA HOLDER
        return new ViewHolder(view);
    }

    // ==========================================
    // DEFINE DADOS
    // ==========================================

    @Override
    public void onBindViewHolder(

            @NonNull ViewHolder holder,

            int position
    ) {

        // ==========================================
        // ITEM DA LISTA
        // ==========================================

        Mensalidade mensalidade =
                lista.get(position);

        // ==========================================
        // NOME JOGADOR
        // ==========================================

        holder.txtNome.setText(
                mensalidade.getNome()
        );

        // ==========================================
        // MÊS
        // ==========================================

        holder.txtMes.setText(

                "Mês: "

                        + mensalidade.getMes()
        );

        // ==========================================
        // VALOR PAGO
        // ==========================================

        holder.txtPago.setText(

                "Pago: R$ "

                        + mensalidade.getValorPago()
        );

        // ==========================================
        // STATUS
        // ==========================================

        holder.txtStatus.setText(
                mensalidade.getStatus()
        );

        // ==========================================
        // COR STATUS
        // ==========================================

        // SE PAGOU
        if("Pago".equals(
                mensalidade.getStatus()
        )){

            // VERDE
            holder.txtStatus.setTextColor(

                    Color.parseColor("#2E7D32")
            );

        }

        // SE PENDENTE
        else{

            // VERMELHO
            holder.txtStatus.setTextColor(

                    Color.parseColor("#D32F2F")
            );
        }

        // ==========================================
        // BOTÃO SALVAR PAGAMENTO
        // ==========================================

        holder.btnSalvarPagamento
                .setOnClickListener(v -> {

                    // ==========================================
                    // VALOR DIGITADO
                    // ==========================================

                    String valorDigitado =

                            holder.edtValorPago

                                    .getText()

                                    .toString()

                                    .trim();

                    // ==========================================
                    // VALIDAÇÃO
                    // ==========================================

                    if(valorDigitado.isEmpty()){

                        Toast.makeText(

                                holder.itemView.getContext(),

                                "Digite um valor",

                                Toast.LENGTH_SHORT

                        ).show();

                        return;
                    }

                    // ==========================================
                    // CONVERTE DOUBLE
                    // ==========================================

                    double valorPago =

                            Double.parseDouble(
                                    valorDigitado
                            );

                    // ==========================================
                    // NOVO TOTAL
                    // ==========================================

                    double novoValorPago =

                            mensalidade.getValorPago()

                                    + valorPago;

                    // ==========================================
                    // CAMPO FIREBASE
                    // ==========================================

                    String campoFirestore =

                            "mensalidades."

                                    + mensalidade.getMes()

                                    + ".valorPago";

                    // ==========================================
                    // SALVA PAGAMENTO
                    // ==========================================

                    db.collection("jogadores")

                            .document(
                                    mensalidade.getId()
                            )

                            .update(

                                    campoFirestore,

                                    novoValorPago
                            )

                            .addOnSuccessListener(unused -> {

                                // ==========================================
                                // ATUALIZA OBJETO
                                // ==========================================

                                mensalidade.setValorPago(
                                        novoValorPago
                                );

                                // ==========================================
                                // LIMPA CAMPO
                                // ==========================================

                                holder.edtValorPago
                                        .setText("");

                                // ==========================================
                                // ATUALIZA ITEM
                                // ==========================================

                                notifyItemChanged(position);

                                // ==========================================
                                // ATUALIZA CAIXA
                                // ==========================================

                                atualizarCaixaEntrada(
                                        valorPago
                                );

                                // ==========================================
                                // TOAST
                                // ==========================================

                                Toast.makeText(

                                        holder.itemView.getContext(),

                                        "Pagamento salvo",

                                        Toast.LENGTH_SHORT

                                ).show();
                            });
                });

        // ==========================================
        // BOTÃO EXCLUIR PAGAMENTO
        // ==========================================

        holder.btnExcluirPagamento
                .setOnClickListener(v -> {

                    // ==========================================
                    // VALOR ANTIGO
                    // ==========================================

                    double valorAntigo =

                            mensalidade.getValorPago();

                    // ==========================================
                    // CAMPO FIREBASE
                    // ==========================================

                    String campoFirestore =

                            "mensalidades."

                                    + mensalidade.getMes()

                                    + ".valorPago";

                    // ==========================================
                    // REMOVE PAGAMENTO
                    // ==========================================

                    db.collection("jogadores")

                            .document(
                                    mensalidade.getId()
                            )

                            .update(

                                    campoFirestore,

                                    0
                            )

                            .addOnSuccessListener(unused -> {

                                // ==========================================
                                // ZERA VALOR LOCAL
                                // ==========================================

                                mensalidade.setValorPago(0);

                                // ==========================================
                                // ATUALIZA ITEM
                                // ==========================================

                                notifyItemChanged(position);

                                // ==========================================
                                // REMOVE DO CAIXA
                                // ==========================================

                                atualizarCaixaSaida(
                                        valorAntigo
                                );

                                // ==========================================
                                // TOAST
                                // ==========================================

                                Toast.makeText(

                                        holder.itemView.getContext(),

                                        "Pagamento excluído",

                                        Toast.LENGTH_SHORT

                                ).show();
                            });
                });
    }

    // ==========================================
    // TOTAL ITENS
    // ==========================================

    @Override
    public int getItemCount() {

        return lista.size();
    }

    // ==========================================
    // ENTRADA CAIXA
    // ==========================================

    private void atualizarCaixaEntrada(
            double valor
    ){

        db.collection("caixa")

                .document("financeiro")

                .get()

                .addOnSuccessListener(document -> {

                    // VALORES
                    double saldo = 0;
                    double entradas = 0;
                    double saidas = 0;

                    // SE EXISTIR
                    if(document.exists()){

                        Double saldoDb =
                                document.getDouble("saldo");

                        Double entradasDb =
                                document.getDouble("entradas");

                        Double saidasDb =
                                document.getDouble("saidas");

                        if(saldoDb != null){

                            saldo = saldoDb;
                        }

                        if(entradasDb != null){

                            entradas = entradasDb;
                        }

                        if(saidasDb != null){

                            saidas = saidasDb;
                        }
                    }

                    // SOMA
                    saldo += valor;

                    entradas += valor;

                    // MAPA
                    HashMap<String,Object> dados =
                            new HashMap<>();

                    dados.put(
                            "saldo",
                            saldo
                    );

                    dados.put(
                            "entradas",
                            entradas
                    );

                    dados.put(
                            "saidas",
                            saidas
                    );

                    // SALVA
                    db.collection("caixa")

                            .document("financeiro")

                            .set(
                                    dados,
                                    SetOptions.merge()
                            );
                });
    }

    // ==========================================
    // REMOVER PAGAMENTO
    // ==========================================

    // ⚠ IMPORTANTE:
    // EXCLUIR PAGAMENTO:
    //
    // ✔ REMOVE SALDO
    // ✔ REMOVE ARRECADAÇÃO
    // ❌ NÃO VIRA DESPESA

    private void atualizarCaixaSaida(
            double valor
    ){

        db.collection("caixa")

                .document("financeiro")

                .get()

                .addOnSuccessListener(document -> {

                    // ==========================================
                    // VALORES
                    // ==========================================

                    double saldo = 0;

                    double entradas = 0;

                    double saidas = 0;

                    // ==========================================
                    // SE EXISTIR
                    // ==========================================

                    if(document.exists()){

                        Double saldoDb =
                                document.getDouble("saldo");

                        Double entradasDb =
                                document.getDouble("entradas");

                        Double saidasDb =
                                document.getDouble("saidas");

                        // SALDO
                        if(saldoDb != null){

                            saldo = saldoDb;
                        }

                        // ENTRADAS
                        if(entradasDb != null){

                            entradas = entradasDb;
                        }

                        // SAÍDAS
                        if(saidasDb != null){

                            saidas = saidasDb;
                        }
                    }

                    // ==========================================
                    // REMOVE DO SALDO
                    // ==========================================

                    saldo -= valor;

                    // ==========================================
                    // REMOVE DA ARRECADAÇÃO
                    // ==========================================

                    entradas -= valor;

                    // ==========================================
                    // PROTEÇÃO
                    // ==========================================

                    if(saldo < 0){

                        saldo = 0;
                    }

                    if(entradas < 0){

                        entradas = 0;
                    }

                    // ==========================================
                    // MAPA
                    // ==========================================

                    HashMap<String,Object> dados =
                            new HashMap<>();

                    // SALDO
                    dados.put(
                            "saldo",
                            saldo
                    );

                    // ENTRADAS
                    dados.put(
                            "entradas",
                            entradas
                    );

                    // SAÍDAS
                    dados.put(
                            "saidas",
                            saidas
                    );

                    // ==========================================
                    // SALVA FIREBASE
                    // ==========================================

                    db.collection("caixa")

                            .document("financeiro")

                            .set(
                                    dados,
                                    SetOptions.merge()
                            );
                });
    }

    // ==========================================
    // VIEW HOLDER
    // ==========================================

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        // TEXTOS
        TextView txtNome;
        TextView txtMes;
        TextView txtPago;
        TextView txtStatus;

        // CAMPO
        EditText edtValorPago;

        // BOTÕES
        Button btnSalvarPagamento;
        Button btnExcluirPagamento;

        // ==========================================
        // CONSTRUTOR
        // ==========================================

        public ViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            // ==========================================
            // XML → JAVA
            // ==========================================

            txtNome =
                    itemView.findViewById(
                            R.id.txtNome
                    );

            txtMes =
                    itemView.findViewById(
                            R.id.txtMes
                    );

            txtPago =
                    itemView.findViewById(
                            R.id.txtPago
                    );

            txtStatus =
                    itemView.findViewById(
                            R.id.txtStatus
                    );

            edtValorPago =
                    itemView.findViewById(
                            R.id.edtValorPago
                    );

            btnSalvarPagamento =
                    itemView.findViewById(
                            R.id.btnSalvarPagamento
                    );

            btnExcluirPagamento =
                    itemView.findViewById(
                            R.id.btnExcluirPagamento
                    );
        }
    }
}