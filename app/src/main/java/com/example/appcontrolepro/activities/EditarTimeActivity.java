package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// Tela padrão do Android
import androidx.appcompat.app.AppCompatActivity;

// Importações Android
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

// Arquivos do projeto
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

// ======================================================
// TELA EDITAR TIME
// ======================================================

public class EditarTimeActivity extends AppCompatActivity {

    // ======================================================
    // CAMPO DE TEXTO
    // ======================================================

    // Campo onde edita o nome do time
    EditText edtNome;

    // ======================================================
    // ID DO TIME
    // ======================================================

    // Guarda o ID do time logado
    String timeId;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle b) {

        super.onCreate(b);

        // Liga XML nessa tela
        setContentView(R.layout.activity_editar_time);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        edtNome =
                findViewById(R.id.edtNome);

        // ======================================================
        // PEGA O ID DO TIME
        // ======================================================

        timeId =
                SessionManager.getTimeId(this);

        // ======================================================
        // CARREGA OS DADOS DO TIME
        // ======================================================

        carregar();
    }

    // ======================================================
    // CARREGAR DADOS
    // ======================================================

    private void carregar(){

        // Busca o time no Firestore
        FirebaseHelper.getFirestore()

                // Coleção times
                .collection("times")

                // Documento do time
                .document(timeId)

                // Busca os dados
                .get()

                // Se deu certo
                .addOnSuccessListener(doc ->

                        // Coloca o nome no campo
                        edtNome.setText(
                                doc.getString("nome")
                        )
                );
    }

    // ======================================================
    // SALVAR ALTERAÇÃO
    // ======================================================

    public void salvar(View v){

        // Atualiza o nome do time
        FirebaseHelper.getFirestore()

                // Coleção times
                .collection("times")

                // Documento do time
                .document(timeId)

                // Atualiza o campo nome
                .update(
                        "nome",
                        edtNome.getText().toString()
                );

        // Fecha a tela
        finish();
    }
}