// DEFINE O PACOTE DO ARQUIVO
package com.example.appcontrolepro.activities;

// ======================================================
// IMPORTAÇÕES
// ======================================================

// IMPORTA TELA PADRÃO
import androidx.appcompat.app.AppCompatActivity;

// IMPORTA TROCA DE TELAS
import android.content.Intent;

// IMPORTA CICLO DA TELA
import android.os.Bundle;

// IMPORTA HANDLER
import android.os.Handler;

// IMPORTA LOOP PRINCIPAL
import android.os.Looper;

// IMPORTA R
import com.example.appcontrolepro.R;

// IMPORTA FIREBASE HELPER
import com.example.appcontrolepro.database.FirebaseHelper;

// ======================================================
// TELA SPLASH
// ======================================================
//
// ESSA TELA:
//
// ✔ MOSTRA LOGO INICIAL
// ✔ ESPERA 2 SEGUNDOS
// ✔ VERIFICA LOGIN
// ✔ ABRE LOGIN
// ✔ OU ABRE ESCOLHER TIME
//
// ======================================================

// CRIA CLASSE
public class SplashActivity

        // HERDA DA ACTIVITY
        extends AppCompatActivity {

    // ======================================================
    // QUANDO A TELA ABRIR
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // CHAMA MÉTODO PAI
        super.onCreate(savedInstanceState);

        // ======================================================
        // DEFINE XML
        // ======================================================

        setContentView(R.layout.activity_splash);

        // ======================================================
        // ESPERA 2 SEGUNDOS
        // ======================================================

        new Handler(

                // USA LOOP PRINCIPAL
                Looper.getMainLooper()

        ).postDelayed(() -> {

            // ==================================================
            // VERIFICA USUÁRIO LOGADO
            // ==================================================

            if (

                    FirebaseHelper
                            .getAuth()
                            .getCurrentUser()

                            == null
            ) {

                // ==============================================
                // SE NÃO TEM USUÁRIO
                // ==============================================

                // ABRE LOGIN
                startActivity(

                        new Intent(

                                this,

                                LoginActivity.class
                        )
                );

            } else {

                // ==============================================
                // SE TEM USUÁRIO
                // ==============================================

                // ABRE ESCOLHER TIME
                startActivity(

                        new Intent(

                                this,

                                EscolherTimeActivity.class
                        )
                );
            }

            // ==================================================
            // FECHA SPLASH
            // ==================================================

            finish();

            // TEMPO EM MILISSEGUNDOS
        }, 2000);
    }
}