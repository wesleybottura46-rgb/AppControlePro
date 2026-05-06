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
import android.widget.Toast;

// Arquivos do projeto
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

// Importações Java
import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA DE CADASTRO DE TIME
// ======================================================

public class CadastroTimeActivity extends AppCompatActivity {

    // ======================================================
    // CAMPO DE TEXTO
    // ======================================================

    // Campo onde digita o nome do time
    EditText edtNomeTime;

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Liga o XML nessa tela
        setContentView(R.layout.activity_cadastro_time);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        edtNomeTime =
                findViewById(R.id.edtNome);
    }

    // ======================================================
    // FUNÇÃO CADASTRAR TIME
    // ======================================================

    public void cadastrarTime(View view){

        // ======================================================
        // PEGA O TEXTO DIGITADO
        // ======================================================

        String nome =

                edtNomeTime
                        .getText()
                        .toString()
                        .trim();

        // ======================================================
        // VERIFICA SE O CAMPO ESTÁ VAZIO
        // ======================================================

        if(nome.isEmpty()){

            // Mostra mensagem
            Toast.makeText(

                    this,

                    "Digite o nome do time",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // CRIA MAPA COM DADOS DO TIME
        // ======================================================

        Map<String,Object> dados =
                new HashMap<>();

        // Nome do time
        dados.put(
                "nome",
                nome
        );

        // ======================================================
        // SALVA NO FIREBASE
        // ======================================================

        FirebaseHelper.getFirestore()

                // Coleção times
                .collection("times")

                // Adiciona os dados
                .add(dados)

                // Se deu certo
                .addOnSuccessListener(doc -> {

                    // ==========================================
                    // LIMPA O CAMPO
                    // ==========================================

                    edtNomeTime.setText("");

                    // ==========================================
                    // MENSAGEM DE SUCESSO
                    // ==========================================

                    Toast.makeText(

                            this,

                            "Time cadastrado com sucesso",

                            Toast.LENGTH_SHORT

                    ).show();
                })

                // Se deu erro
                .addOnFailureListener(e -> {

                    // ==========================================
                    // MENSAGEM DE ERRO
                    // ==========================================

                    Toast.makeText(

                            this,

                            "Erro ao cadastrar time",

                            Toast.LENGTH_SHORT

                    ).show();
                });
    }
}