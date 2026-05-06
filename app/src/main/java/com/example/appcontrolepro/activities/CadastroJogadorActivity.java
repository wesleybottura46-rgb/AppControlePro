package com.example.appcontrolepro.activities;

// Importa a tela padrão do Android
import androidx.appcompat.app.AppCompatActivity;

// Importações do Android
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// Importa arquivos do projeto
import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

// Importa mapas do Java
import java.util.HashMap;
import java.util.Map;

// ======================================================
// TELA DE CADASTRO DE JOGADOR
// ======================================================

public class CadastroJogadorActivity extends AppCompatActivity {

    // Campo onde digita o nome
    EditText edtNome;

    // Lista de posições
    Spinner spinnerPosicao;

    // Guarda o ID do time logado
    String timeId;

    // ======================================================
    // LISTA DE POSIÇÕES
    // ======================================================

    String[] posicoes = {

            "Goleiro",
            "Zagueiro",
            "Lateral",
            "Meio Campo",
            "Atacante"
    };

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Liga o XML nessa tela
        setContentView(R.layout.activity_cadastro_jogador);

        // ======================================================
        // CONECTA XML COM JAVA
        // ======================================================

        edtNome =
                findViewById(R.id.edtNomeJogador);

        spinnerPosicao =
                findViewById(R.id.spinnerPosicao);

        // ======================================================
        // CONFIGURA O SPINNER
        // ======================================================

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(

                        this,

                        android.R.layout.simple_spinner_dropdown_item,

                        posicoes
                );

        // Coloca as posições no spinner
        spinnerPosicao.setAdapter(adapter);

        // ======================================================
        // PEGA O ID DO TIME
        // ======================================================

        timeId =
                SessionManager.getTimeId(this);
    }

    // ======================================================
    // FUNÇÃO CADASTRAR JOGADOR
    // ======================================================

    public void cadastrarJogador(View view){

        // ======================================================
        // PEGA O NOME DIGITADO
        // ======================================================

        String nome =
                edtNome.getText()
                        .toString()
                        .trim();

        // ======================================================
        // PEGA A POSIÇÃO ESCOLHIDA
        // ======================================================

        String posicao =
                spinnerPosicao
                        .getSelectedItem()
                        .toString();

        // ======================================================
        // VERIFICA SE O NOME ESTÁ VAZIO
        // ======================================================

        if(nome.isEmpty()){

            // Mostra mensagem
            Toast.makeText(

                    this,

                    "Digite o nome",

                    Toast.LENGTH_SHORT

            ).show();

            return;
        }

        // ======================================================
        // CRIA MAPA COM DADOS DO JOGADOR
        // ======================================================

        Map<String,Object> dados =
                new HashMap<>();

        // Nome do jogador
        dados.put("nome", nome);

        // Posição
        dados.put("posicao", posicao);

        // ID do time
        dados.put("timeId", timeId);

        // Gols começa em 0
        dados.put("gols", 0);

        // Assistências começa em 0
        dados.put("assistencias", 0);

        // Jogos começa em 0
        dados.put("jogos", 0);

        // ======================================================
        // PARTE FINANCEIRA
        // ======================================================

        // ======================================================
        // MAPA DAS MENSALIDADES
        // ======================================================

        Map<String,Object> mensalidades =
                new HashMap<>();

        // ======================================================
        // MAPA DO MÊS
        // ======================================================

        Map<String,Object> maio2026 =
                new HashMap<>();

        // ======================================================
        // STATUS DA MENSALIDADE
        // ======================================================

        maio2026.put(

                "status",

                "Pendente"
        );

        // ======================================================
        // VALOR DA MENSALIDADE
        // ======================================================

        maio2026.put(

                "valor",

                50
        );

        // ======================================================
        // ADICIONA O MÊS DENTRO DAS MENSALIDADES
        // ======================================================

        mensalidades.put(

                "Maio_2026",

                maio2026
        );

        // ======================================================
        // SALVA AS MENSALIDADES NO JOGADOR
        // ======================================================

        dados.put(

                "mensalidades",

                mensalidades
        );

        // ======================================================
        // GUARDA O MÊS DE CADASTRO
        // ======================================================

        dados.put(

                "ordemMesCadastro",

                5
        );

        // ======================================================
        // SALVA NO FIREBASE
        // ======================================================

        FirebaseHelper.getFirestore()

                // Coleção jogadores
                .collection("jogadores")

                // Adiciona os dados
                .add(dados)

                // Se deu certo
                .addOnSuccessListener(doc -> {

                    // ==========================================
                    // LIMPA OS CAMPOS
                    // ==========================================

                    edtNome.setText("");

                    spinnerPosicao.setSelection(0);

                    // ==========================================
                    // MENSAGEM DE SUCESSO
                    // ==========================================

                    Toast.makeText(

                            this,

                            "Jogador cadastrado com sucesso",

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

                            "Erro ao cadastrar jogador",

                            Toast.LENGTH_SHORT

                    ).show();
                });
    }
}