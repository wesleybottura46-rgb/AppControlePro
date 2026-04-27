package com.example.appcontrolepro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appcontrolepro.R;
import com.example.appcontrolepro.database.FirebaseHelper;
import com.example.appcontrolepro.utils.SessionManager;

public class EditarTimeActivity extends AppCompatActivity {

    EditText edtNome;
    String timeId;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_editar_time);

        edtNome = findViewById(R.id.edtNome);
        timeId = SessionManager.getTimeId(this);

        carregar();
    }

    private void carregar(){
        FirebaseHelper.getFirestore()
                .collection("times")
                .document(timeId)
                .get()
                .addOnSuccessListener(doc ->
                        edtNome.setText(doc.getString("nome")));
    }

    public void salvar(View v){

        FirebaseHelper.getFirestore()
                .collection("times")
                .document(timeId)
                .update("nome", edtNome.getText().toString());

        finish();
    }
}