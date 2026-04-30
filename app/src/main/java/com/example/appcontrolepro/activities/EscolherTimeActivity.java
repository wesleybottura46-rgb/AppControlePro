package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

import java.util.ArrayList;

public class EscolherTimeActivity extends AppCompatActivity {

    Spinner spinnerTimes;

    ArrayList<String> nomes = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_time);

        spinnerTimes = findViewById(R.id.spinnerTimes);

        carregarTimes();
    }

    // ======================================================
    // CARREGAR TIMES
    // ======================================================
    private void carregarTimes(){

        FirebaseHelper.getFirestore()
                .collection("times")
                .addSnapshotListener((query, error) -> {

                    if(query == null) return;

                    nomes.clear();
                    ids.clear();

                    for(var doc : query){
                        nomes.add(doc.getString("nome"));
                        ids.add(doc.getId());
                    }

                    // SE NÃO TEM TIME
                    if(nomes.isEmpty()){
                        spinnerTimes.setAdapter(null);
                        Toast.makeText(this,"Crie um time primeiro",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            nomes
                    );

                    spinnerTimes.setAdapter(adapter);
                });
    }

    // ======================================================
    // BOTÃO ENTRAR NO TIME
    // ======================================================
    public void entrarTime(View view){

        int pos = spinnerTimes.getSelectedItemPosition();

        if(pos < 0 || pos >= ids.size()){
            Toast.makeText(this,"Selecione um time",Toast.LENGTH_SHORT).show();
            return;
        }

        String timeId = ids.get(pos);

        // SALVA NA SESSÃO
        SessionManager.setTimeId(this, timeId);

        //VAI PARA MAIN
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // ======================================================
    // BOTÃO CRIAR TIME
    // ======================================================
    public void criarTime(View view){
        startActivity(new Intent(this, CadastroTimeActivity.class));
    }

    // ======================================================
    // EXCLUIR TIME
    // ======================================================
    public void excluirTime(View view){

        int pos = spinnerTimes.getSelectedItemPosition();

        if(pos < 0 || pos >= ids.size()){
            Toast.makeText(this,"Selecione um time",Toast.LENGTH_SHORT).show();
            return;
        }

        String timeId = ids.get(pos);
        String nomeTime = nomes.get(pos);

        new AlertDialog.Builder(this)
                .setTitle("⚠️ Excluir Time")
                .setMessage(
                        "Você está prestes a excluir o time:\n\n" +
                                "👉 " + nomeTime + "\n\n" +
                                "Isso irá apagar:\n" +
                                "• Todos os jogadores\n" +
                                "• Todos os jogos\n\n" +
                                "Essa ação NÃO pode ser desfeita!"
                )
                .setPositiveButton("EXCLUIR", (dialog, which) -> {

                    FirebaseHelper.getFirestore()
                            .collection("jogadores")
                            .whereEqualTo("timeId", timeId)
                            .get()
                            .addOnSuccessListener(jogadores -> {

                                for(var doc : jogadores){
                                    FirebaseHelper.getFirestore()
                                            .collection("jogadores")
                                            .document(doc.getId())
                                            .delete();
                                }
                            });

                    FirebaseHelper.getFirestore()
                            .collection("jogos")
                            .whereEqualTo("timeId", timeId)
                            .get()
                            .addOnSuccessListener(jogos -> {

                                for(var doc : jogos){
                                    FirebaseHelper.getFirestore()
                                            .collection("jogos")
                                            .document(doc.getId())
                                            .delete();
                                }
                            });

                    FirebaseHelper.getFirestore()
                            .collection("times")
                            .document(timeId)
                            .delete()
                            .addOnSuccessListener(v -> {
                                Toast.makeText(this,"Time excluído",Toast.LENGTH_SHORT).show();
                            });

                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // ==========================
// ABRIR CADASTRO DE TIME
// ==========================
    public void abrirCadastroTime(View view){
        startActivity(new Intent(this, CadastroTimeActivity.class));
    }
}
