package com.example.a1projectfix;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.databinding.ActivityDownloadRekapBinding;

public class DownloadRekap extends AppCompatActivity {
    private ActivityDownloadRekapBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDownloadRekapBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        init();
    }

    String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String[] years = {"2024", "2025", "2026"};

    private void init() {
        ArrayAdapter<String> bulan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> tahun = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        bulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bind.bulan.setAdapter(bulan);
        bind.tahun.setAdapter(tahun);
    }
}