package com.example.a1projectfix.daftar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.databinding.ActivityRekapDataBinding;

public class RekapData extends AppCompatActivity {
    private ActivityRekapDataBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRekapDataBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
    }

    private void init(){

    }
}