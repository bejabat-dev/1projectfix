package com.example.a1projectfix.daftar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.a1projectfix.R;
import com.example.a1projectfix.databinding.ActivityDetailMuridBinding;

public class DetailMurid extends AppCompatActivity {
private ActivityDetailMuridBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailMuridBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
    }

    private void init(){

    }
}