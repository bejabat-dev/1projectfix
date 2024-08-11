package com.example.a1projectfix.absensi_kehadiran;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.R;

public class AbsenKehadiran extends AppCompatActivity {
    private EditText inputNama;
    private EditText inputTanggal;
    private EditText inputLokasi;
    private EditText inputKeterangan;
    private Button btnAbsen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absensi);

        inputNama = findViewById(R.id.inputNama);
        inputTanggal = findViewById(R.id.inputTanggal);
        inputLokasi = findViewById(R.id.inputLokasi);
        inputKeterangan = findViewById(R.id.inputKeterangan);
        btnAbsen = findViewById(R.id.btnAbsen);

        btnAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = inputNama.getText().toString();
                String tanggal = inputTanggal.getText().toString();
                String lokasi = inputLokasi.getText().toString();
                String keterangan = inputKeterangan.getText().toString();

                if (!nama.isEmpty() && !tanggal.isEmpty() && !lokasi.isEmpty() && !keterangan.isEmpty()) {
                    // Kode untuk melakukan absensi, seperti mengirim data ke server atau menyimpan data ke database
                    Toast.makeText(AbsenKehadiran.this, "Absensi berhasil", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                } else {
                    Toast.makeText(AbsenKehadiran.this, "Mohon lengkapi semua kolom input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearInputFields() {
        inputNama.setText("");
        inputTanggal.setText("");
        inputLokasi.setText("");
        inputKeterangan.setText("");
    }
}