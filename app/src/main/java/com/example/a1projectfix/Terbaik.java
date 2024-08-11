package com.example.a1projectfix;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Terbaik extends AppCompatActivity {

    private AppCompatButton buttonBulanan;
    private AppCompatButton buttonTahunan;
    private AppCompatButton buttonAllTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terbaik);

        buttonBulanan = findViewById(R.id.btn_bulan);
        buttonTahunan = findViewById(R.id.btn_tahun);
        buttonAllTime = findViewById(R.id.btn_alltime);

        buttonBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi yang dijalankan saat button Bulanan diklik
                // Contoh: Berpindah ke halaman peringkat bulanan
            }
        });

        buttonTahunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi yang dijalankan saat button Tahunan diklik
                // Contoh: Berpindah ke halaman peringkat tahunan
            }
        });

        buttonAllTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi yang dijalankan saat button All Time diklik
                // Contoh: Berpindah ke halaman peringkat all time
            }
        });
    }
}