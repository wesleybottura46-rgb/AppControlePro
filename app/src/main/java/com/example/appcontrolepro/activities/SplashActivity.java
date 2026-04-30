package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;

// =========================================================
// Tela com logo do app(Abre quando abre o app)
// =========================================================
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            // SE NÃO TEM USUÁRIO - LOGIN
            if (FirebaseHelper.getAuth().getCurrentUser() == null) {

                startActivity(new Intent(this, LoginActivity.class));

            } else {

                // SE TEM USUÁRIO - ESCOLHER TIME
                startActivity(new Intent(this, EscolherTimeActivity.class));
            }

            finish();

        }, 2000);
    }
}
