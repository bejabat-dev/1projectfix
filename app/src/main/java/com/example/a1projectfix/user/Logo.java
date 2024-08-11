package com.example.a1projectfix.user;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;
import com.example.a1projectfix.tampilan_awal.Dashboard;
import com.google.firebase.auth.FirebaseUser;

public class Logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        DataUser data = new DataUser();
        FirebaseUser user = data.cekLogin();
        if(user != null){
            startActivity(new Intent(Logo.this, Dashboard.class));
            finish();
        }

        Button loginButton = findViewById(R.id.btn_masuk);
        Button daftarButton = findViewById(R.id.btn_daftar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logo.this, Login.class);
                startActivity(intent);
            }
        });

        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logo.this, Daftar.class);
                startActivity(intent);
            }
        });
    }
}