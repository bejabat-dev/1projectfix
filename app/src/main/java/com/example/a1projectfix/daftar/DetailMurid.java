package com.example.a1projectfix.daftar;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a1projectfix.databinding.ActivityDetailMuridBinding;

public class DetailMurid extends AppCompatActivity {
    private ActivityDetailMuridBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailMuridBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        init();
    }

    private void init() {
        Intent i = getIntent();
        bind.nama.setText(i.getStringExtra("nama"));
        bind.ttl.setText(i.getStringExtra("ttl"));
        bind.kelas.setText(i.getStringExtra("kelas"));
        bind.alamat.setText(i.getStringExtra("alamat"));
    }
}