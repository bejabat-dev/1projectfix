package com.example.a1projectfix.tampilan_awal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1projectfix.LaporanBiaya;
import com.example.a1projectfix.daftar.Konfirmasi;
import com.example.a1projectfix.daftar.DaftarKegiatan;
import com.example.a1projectfix.daftar.DaftarMurid;
import com.example.a1projectfix.R;
import com.example.a1projectfix.daftar.DaftarPelatih;
import com.example.a1projectfix.daftar.RekapData;
import com.example.a1projectfix.utilitas.DataKegiatan;
import com.example.a1projectfix.utilitas.DataMurid;
import com.example.a1projectfix.utilitas.DataUser;
import com.example.a1projectfix.user.Profil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {
    private LinearLayout tambahmurid;
    private LinearLayout daftarp;
    private LinearLayout jumlah_murid, rekap_data, konfirmasi_data, laporan_biaya;
    private ImageView profil;
    private TextView daftar_kegiatan;
    private TextView total_murid, total_belum_terkonfirmasi, total_terkonfirmasi;
    DataUser data;
    Handler handler;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        total_murid = findViewById(R.id.total_murid);
        total_belum_terkonfirmasi = findViewById(R.id.total_belum_terkonfirmasi);
        total_terkonfirmasi = findViewById(R.id.total_terkonfirmasi);
        rekap_data = findViewById(R.id.rekap_data);
        data = new DataUser();
        data.loadUser(this);
        data.loadRiwayat();
        DataMurid data = new DataMurid();
        DataMurid.loadMurid();
        DataKegiatan dataKegiatan = new DataKegiatan();
        dataKegiatan.loadKegiatan();
        konfirmasi_data = findViewById(R.id.konfirmasi_data);
        profil = findViewById(R.id.profil);
        tambahmurid = findViewById(R.id.tambah_murid);
        daftarp = findViewById(R.id.btn_daftarp);
        daftar_kegiatan = findViewById(R.id.daftark);
        jumlah_murid = findViewById(R.id.jumlah_murid);
        laporan_biaya = findViewById(R.id.laporan_biaya);

        jumlah_murid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, DaftarMurid.class));
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Profil.class));
            }
        });

        daftarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, DaftarPelatih.class);
                startActivity(intent);
            }
        });
        tambahmurid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Konfirmasi.class);
                startActivity(intent);
            }
        });

        daftar_kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, DaftarKegiatan.class));
            }
        });
        rekap_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, RekapData.class);
                startActivity(i);
            }
        });
        konfirmasi_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, Konfirmasi.class);
                startActivity(i);
            }
        });
        laporan_biaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Dashboard.this, LaporanBiaya.class);
                startActivity(i);
            }
        });

        init();
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (DataUser.role.equals("admin")) {
                    konfirmasi_data.setVisibility(View.VISIBLE);
                    laporan_biaya.setVisibility(View.VISIBLE);
                } else {
                    konfirmasi_data.setVisibility(View.GONE);
                    laporan_biaya.setVisibility(View.GONE);
                }
                Log.d("MainActivity", "Task running...");
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }


    private void init() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Murid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total_murid.setText("0");
                total_terkonfirmasi.setText("0");
                total_belum_terkonfirmasi.setText("0");
                int total = 0;
                int total1 = 0;
                int total2 = 0;
                for (DataSnapshot muridSnapshot : snapshot.getChildren()) {

                    total++;
                    total_murid.setText("" + total);
                    Boolean reg = muridSnapshot.child("register").getValue(Boolean.class);

                    if (reg == null) {
                        total1++;
                        total_terkonfirmasi.setText("" + total1);
                    } else if (reg != null) {
                        total2++;
                        total_belum_terkonfirmasi.setText("" + total2);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}