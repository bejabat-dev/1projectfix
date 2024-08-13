package com.example.a1projectfix.tampilan_awal;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1projectfix.Konfirmasi;
import com.example.a1projectfix.daftar.DaftarKegiatan;
import com.example.a1projectfix.daftar.DaftarMurid;
import com.example.a1projectfix.R;
import com.example.a1projectfix.absensi_kehadiran.Absen;
import com.example.a1projectfix.daftar.DaftarPelatih;
import com.example.a1projectfix.utilitas.DataKegiatan;
import com.example.a1projectfix.utilitas.DataMurid;
import com.example.a1projectfix.utilitas.DataUser;
import com.example.a1projectfix.user.Profil;
import com.example.a1projectfix.Prestasi;
import com.example.a1projectfix.pendaftaran.Tambah;
import com.example.a1projectfix.Terbaik;

public class Dashboard extends AppCompatActivity {
    private LinearLayout muridt;
    private LinearLayout jumlahm;
    private LinearLayout tambahmurid;
    private LinearLayout absenp;
    private LinearLayout prestasim;
    private LinearLayout historyp;
    private LinearLayout daftarp;
    private LinearLayout daftarkegiatan,jumlah_murid;
    private ImageView profil;
    private TextView daftar_kegiatan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        DataUser data = new DataUser();
        data.loadUser();
        data.loadRiwayat();
        DataMurid dataMurid = new DataMurid();
        dataMurid.loadMurid();
        DataKegiatan dataKegiatan = new DataKegiatan();
        dataKegiatan.loadKegiatan();

        profil = findViewById(R.id.profil);
        tambahmurid = findViewById(R.id.tambah_murid);
        absenp = findViewById(R.id.bt_absenp);
        daftarp = findViewById(R.id.btn_daftarp);
        daftar_kegiatan = findViewById(R.id.daftark);
        jumlah_murid = findViewById(R.id.jumlah_murid);

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
        absenp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Absen.class);
                startActivity(intent);
            }
        });
        daftar_kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, DaftarKegiatan.class));
            }
        });
    }

}