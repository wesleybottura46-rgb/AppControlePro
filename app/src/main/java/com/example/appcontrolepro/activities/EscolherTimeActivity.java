package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// Tela padrão do Android
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// Importações Android
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// Arquivos do projeto
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

// Importações Java
import java.util.ArrayList;

// ======================================================
// TELA ESCOLHER TIME
// ======================================================

public class EscolherTimeActivity extends AppCompatActivity {

    // ======================================================
    // SPINNER
    // ======================================================

    // Lista que mostra os times
    Spinner spinnerTimes;

    // ======================================================
    // LISTAS
    // ======================================================

    // Lista com nomes dos times
    ArrayList<String> nomes =
            new ArrayList<>();

    // Lista com IDs dos times
    ArrayList<String> ids =
            new ArrayList<>();

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Liga XML nessa tela
        setContentView(R.layout.activity_escolher_time);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        spinnerTimes =
                findViewById(R.id.spinnerTimes);

        // ======================================================
        // CARREGA OS TIMES
        // ======================================================

        carregarTimes();
    }

    // ======================================================
    // CARREGAR TIMES EM TEMPO REAL
    // ======================================================

    private void carregarTimes(){

        // Busca coleção times
        FirebaseHelper.getFirestore()

                .collection("times")

                // Atualiza automaticamente
                .addSnapshotListener((query, error) -> {

                    // ======================================================
                    // SE A CONSULTA FOR NULA
                    // ======================================================

                    if(query == null) return;

                    // ======================================================
                    // LIMPA LISTAS
                    // ======================================================

                    nomes.clear();

                    ids.clear();

                    // ======================================================
                    // PERCORRE TODOS OS TIMES
                    // ======================================================

                    for(var doc : query){

                        // Adiciona nome do time
                        nomes.add(
                                doc.getString("nome")
                        );

                        // Adiciona ID do time
                        ids.add(
                                doc.getId()
                        );
                    }

                    // ======================================================
                    // SE NÃO EXISTIR TIME
                    // ======================================================

                    if(nomes.isEmpty()){

                        // Remove itens do spinner
                        spinnerTimes.setAdapter(null);

                        // Mostra mensagem
                        Toast.makeText(

                                this,

                                "Crie um time primeiro",

                                Toast.LENGTH_SHORT

                        ).show();

                        return;
                    }

                    // ======================================================
                    // CRIA ADAPTER DO SPINNER
                    // ======================================================

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(

                                    this,

                                    android.R.layout.simple_spinner_dropdown_item,

                                    nomes
                            );

                    // ======================================================
                    // COLOCA DADOS NO SPINNER
                    // ======================================================

                    spinnerTimes.setAdapter(adapter);
                });
    }

    // ======================================================
    // ENTRAR NO TIME
    // ======================================================

    public void entrarTime(View view){

        // ======================================================
        // PEGA POSIÇÃO SELECIONADA
        // ======================================================

        int pos =
                spinnerTimes.getSelectedItemPosition();

        // ======================================================
        // VERIFICA POSIÇÃO
        // ======================================================

        if(pos < 0 || pos >= ids.size()){

            // Mostra mensagem
            Toast.makeText(

                    this,

                    "Selecione um time",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // PEGA O ID DO TIME
        // ======================================================

        String timeId =
                ids.get(pos);

        // ======================================================
        // SALVA O TIME NA SESSÃO
        // ======================================================

        SessionManager.setTimeId(
                this,
                timeId
        );

        // ======================================================
        // ABRE A MAIN
        // ======================================================

        Intent intent =
                new Intent(
                        this,
                        MainActivity.class
                );

        startActivity(intent);
    }

    // ======================================================
    // CRIAR TIME
    // ======================================================

    public void criarTime(View view){

        // Abre tela de cadastro de time
        startActivity(

                new Intent(

                        this,

                        CadastroTimeActivity.class
                )
        );
    }

    // ======================================================
    // EXCLUIR TIME
    // ======================================================

    public void excluirTime(View view){

        // ======================================================
        // PEGA POSIÇÃO SELECIONADA
        // ======================================================

        int pos =
                spinnerTimes.getSelectedItemPosition();

        // ======================================================
        // VERIFICA POSIÇÃO
        // ======================================================

        if(pos < 0 || pos >= ids.size()){

            // Mostra mensagem
            Toast.makeText(

                    this,

                    "Selecione um time",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // PEGA ID E NOME
        // ======================================================

        String timeId =
                ids.get(pos);

        String nomeTime =
                nomes.get(pos);

        // ======================================================
        // ALERTA DE EXCLUSÃO
        // ======================================================

        new AlertDialog.Builder(this)

                // Título
                .setTitle("⚠️ Excluir Time")

                // Mensagem
                .setMessage(

                        "Você está prestes a excluir o time:\n\n"

                                + "👉 " + nomeTime + "\n\n"

                                + "Isso irá apagar:\n"

                                + "• Todos os jogadores\n"

                                + "• Todos os jogos\n\n"

                                + "Essa ação NÃO pode ser desfeita!"
                )

                // Botão excluir
                .setPositiveButton("EXCLUIR", (dialog, which) -> {

                    // ==========================================
                    // EXCLUI JOGADORES
                    // ==========================================

                    FirebaseHelper.getFirestore()

                            .collection("jogadores")

                            .whereEqualTo(
                                    "timeId",
                                    timeId
                            )

                            .get()

                            .addOnSuccessListener(jogadores -> {

                                for(var doc : jogadores){

                                    FirebaseHelper.getFirestore()

                                            .collection("jogadores")

                                            .document(doc.getId())

                                            .delete();
                                }
                            });

                    // ==========================================
                    // EXCLUI JOGOS
                    // ==========================================

                    FirebaseHelper.getFirestore()

                            .collection("jogos")

                            .whereEqualTo(
                                    "timeId",
                                    timeId
                            )

                            .get()

                            .addOnSuccessListener(jogos -> {

                                for(var doc : jogos){

                                    FirebaseHelper.getFirestore()

                                            .collection("jogos")

                                            .document(doc.getId())

                                            .delete();
                                }
                            });

                    // ==========================================
                    // EXCLUI O TIME
                    // ==========================================

                    FirebaseHelper.getFirestore()

                            .collection("times")

                            .document(timeId)

                            .delete()

                            .addOnSuccessListener(v -> {

                                // Mostra mensagem
                                Toast.makeText(

                                        this,

                                        "Time excluído",

                                        Toast.LENGTH_SHORT

                                ).show();
                            });
                })

                // Botão cancelar
                .setNegativeButton(
                        "Cancelar",
                        null
                )

                // Mostra alerta
                .show();
    }

    // ======================================================
    // ABRIR CADASTRO DE TIME
    // ======================================================

    // Compatível com XML
    public void abrirCadastroTime(View view){

        // Abre tela cadastro de time
        startActivity(

                new Intent(

                        this,

                        CadastroTimeActivity.class
                )
        );
    }
}