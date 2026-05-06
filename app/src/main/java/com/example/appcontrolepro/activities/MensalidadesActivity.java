package com.example.appcontrolepro.activities;

// ==========================================
// IMPORTAÇÕES
// ==========================================

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.models.Mensalidade;
import com.example.appcontrolepro.utils.SessionManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

// ==========================================
// TELA MENSALIDADES
// ==========================================

public class MensalidadesActivity
        extends AppCompatActivity {

    // RECYCLERVIEW
    RecyclerView recyclerMensalidades;

    // SPINNERS
    Spinner spinnerMes;
    Spinner spinnerAno;

    // LISTA
    List<Mensalidade> lista;

    // ADAPTER
    MensalidadeAdapter adapter;

    // FIREBASE
    FirebaseFirestore db;

    // TIME ID
    String timeId;

    // ==========================================
    // MESES
    // ⚠ IMPORTANTE:
    // SEM ACENTO
    // PARA NÃO DAR ERRO NO FIRESTORE
    // ==========================================

    String[] meses = {

            "Janeiro",
            "Fevereiro",
            "Marco",
            "Abril",
            "Maio",
            "Junho",
            "Julho",
            "Agosto",
            "Setembro",
            "Outubro",
            "Novembro",
            "Dezembro"
    };

    // ANOS
    String[] anos = {

            "2026",
            "2027",
            "2028",
            "2029",
            "2030"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // LAYOUT
        setContentView(
                R.layout.activity_mensalidades
        );

        // FIREBASE
        db = FirebaseFirestore.getInstance();

        // TIME ID
        timeId =
                SessionManager.getTimeId(this);

        // SPINNER ANO
        spinnerAno =
                findViewById(R.id.spinnerAno);

        // ADAPTER ANO
        ArrayAdapter<String> adapterAno =
                new ArrayAdapter<>(

                        this,

                        android.R.layout.simple_spinner_dropdown_item,

                        anos
                );

        spinnerAno.setAdapter(adapterAno);

        // SPINNER MÊS
        spinnerMes =
                findViewById(R.id.spinnerMes);

        // ADAPTER MÊS
        ArrayAdapter<String> adapterMes =
                new ArrayAdapter<>(

                        this,

                        android.R.layout.simple_spinner_dropdown_item,

                        meses
                );

        spinnerMes.setAdapter(adapterMes);

        // DATA ATUAL
        Calendar calendar =
                Calendar.getInstance();

        int mesAtual =
                calendar.get(Calendar.MONTH);

        int anoAtual =
                calendar.get(Calendar.YEAR);

        // DEFINE MÊS
        spinnerMes.setSelection(mesAtual);

        // DEFINE ANO
        for(int i = 0; i < anos.length; i++){

            if(anos[i].equals(
                    String.valueOf(anoAtual)
            )){

                spinnerAno.setSelection(i);

                break;
            }
        }

        // RECYCLER
        recyclerMensalidades =
                findViewById(
                        R.id.recyclerMensalidades
                );

        recyclerMensalidades.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // LISTA
        lista = new ArrayList<>();

        // ADAPTER
        adapter =
                new MensalidadeAdapter(lista);

        recyclerMensalidades.setAdapter(adapter);

        // EVENTO MÊS
        spinnerMes.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        carregarMensalidades();
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {

                    }
                });

        // EVENTO ANO
        spinnerAno.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        carregarMensalidades();
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent
                    ) {

                    }
                });
    }

    // ==========================================
    // CARREGA LISTA
    // ==========================================

    private void carregarMensalidades(){

        // MÊS
        String mes =
                spinnerMes
                        .getSelectedItem()
                        .toString();

        // ANO
        String ano =
                spinnerAno
                        .getSelectedItem()
                        .toString();

        // CHAVE
        String chaveMes =
                mes + "_" + ano;

        // FIREBASE
        db.collection("jogadores")

                .whereEqualTo(
                        "timeId",
                        timeId
                )

                .addSnapshotListener((q, e) -> {

                    if(q == null) return;

                    // LIMPA LISTA
                    lista.clear();

                    // PERCORRE DOCUMENTOS
                    for(QueryDocumentSnapshot document : q){

                        // NOME
                        String nome =
                                document.getString("nome");

                        // SE NULO
                        if(nome == null){

                            nome = "Jogador";
                        }

                        // VALOR PAGO
                        double valorPago = 0;

                        // MAPA
                        Map<String,Object> mensalidades =

                                (Map<String, Object>)
                                        document.get(
                                                "mensalidades"
                                        );

                        // SE EXISTIR
                        if(mensalidades != null){

                            Object mesObj =
                                    mensalidades.get(
                                            chaveMes
                                    );

                            // SE FOR MAPA
                            if(mesObj instanceof Map){

                                Map<String,Object> dadosMes =
                                        (Map<String, Object>) mesObj;

                                Object valorObj =
                                        dadosMes.get(
                                                "valorPago"
                                        );

                                // SE NÃO NULO
                                if(valorObj != null){

                                    valorPago =
                                            Double.parseDouble(
                                                    valorObj.toString()
                                            );
                                }
                            }
                        }

                        // ADICIONA
                        lista.add(

                                new Mensalidade(

                                        document.getId(),

                                        nome,

                                        chaveMes,

                                        valorPago
                                )
                        );
                    }

                    // ATUALIZA
                    adapter.notifyDataSetChanged();
                });
    }
}