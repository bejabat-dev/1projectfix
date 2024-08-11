package com.example.a1projectfix.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Profil extends AppCompatActivity {
    private TextView nama, sabuk, email, nohp, keluar, edit;

    private String snama, ssabuk, semail, snohp, sfoto;
    private ImageView foto;
    private int select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUser();
    }

    private void init() {
        nama = findViewById(R.id.nama);
        sabuk = findViewById(R.id.tingkatsabuk);
        email = findViewById(R.id.email);
        nohp = findViewById(R.id.nohp);
        keluar = findViewById(R.id.keluar);
        edit = findViewById(R.id.edit_profil);
        foto = findViewById(R.id.foto_profil);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            loadUser();
            keluar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Profil.this, Logo.class));
                    finish();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent s = new Intent(Profil.this, EditProfil.class);
                    s.putExtra("selection", select);
                    s.putExtra("nama", snama);
                    s.putExtra("nohp", snohp);
                    startActivity(s);
                }
            });
        }
    }

    private void loadUser() {
        snama = DataUser.getNama();
        ssabuk = DataUser.getSabuk();
        semail = DataUser.getEmail();
        snohp = DataUser.getNohp();
        sfoto = DataUser.getFoto();
        String selection = DataUser.getSelection();
        assert selection != null;
        select = Integer.parseInt(selection);
        if (ssabuk != null || snohp != null) {
            sabuk.setText(ssabuk);
            nohp.setText(snohp);
        }
        if (!sfoto.equals("")) {
            Picasso.get().load(sfoto).into(foto);
        }
        nama.setText(snama);
        email.setText(semail);
    }
}