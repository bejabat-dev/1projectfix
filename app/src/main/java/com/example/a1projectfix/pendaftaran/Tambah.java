package com.example.a1projectfix.pendaftaran;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.R;
import com.example.a1projectfix.absensi_kehadiran.InputAbsen;
import com.example.a1projectfix.utilitas.DataMurid;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Tambah extends AppCompatActivity {
    private LinearLayout lprestasi;
    private LinearLayout lreguler;
    private LinearLayout lkhusus;
    private TextView kelas;
    private EditText nama, tempat, tanggal, alamat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_murid);

        lprestasi = findViewById(R.id.k_prestasi);

        lkhusus = findViewById(R.id.khusus);

        lreguler = findViewById(R.id.reguler);


        lprestasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tambah.this, KelasPrestasi.class);
                startActivity(intent);
            }
        });
        lreguler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tambah.this, KelasReguler.class);
                startActivity(intent);
            }
        });
        lkhusus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tambah.this, KelasKhusus.class);
                startActivity(intent);
            }
        });
    }

    private void checking() {
        Intent i = getIntent();
        if (i.getBooleanExtra("editing", false)) {
            nama.setText(i.getStringExtra("nama"));
            kelas.setText(i.getStringExtra("kelas"));
            tempat.setText(i.getStringExtra("tempat"));
            tanggal.setText(i.getStringExtra("tanggal"));
            alamat.setText(i.getStringExtra("alamat"));
        }
    }

    private void init() {
        kelas = findViewById(R.id.kelas);
        nama = findViewById(R.id.inputNama);
        tempat = findViewById(R.id.inputTempatLahir);
        tanggal = findViewById(R.id.inputTanggalLahir);
        alamat = findViewById(R.id.inputAlamat);
        checking();
        String s;
        if (DataUser.getPilihan() == null) {
            s = "Pilih kelas";
        } else {
            s = "Kelas yang dipilih : " + DataUser.getPilihan();
        }
        kelas.setText(s);

        Button order = findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama, sTempat, sTanggal, sAlamat;
                sNama = nama.getText().toString();
                sTanggal = tanggal.getText().toString();
                sAlamat = alamat.getText().toString();
                sTempat = tempat.getText().toString();
                if (sNama.isEmpty() || sTanggal.isEmpty() || sAlamat.isEmpty() || sTempat.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Penuhi semua kolom", Toast.LENGTH_SHORT).show();
                } else {
                    if (DataUser.getPilihan() == null) {
                        Toast.makeText(getApplicationContext(), "Pilih kelas terlebih dahulu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    String key = db.push().getKey();
                    if (getIntent().getBooleanExtra("editing", false)) {
                        key = DataMurid.key;
                    }
                    DataMurid d = new DataMurid(key, sNama, DataUser.getPilihan(), sTempat, sTanggal, sAlamat);
                    d.tambahMurid(Tambah.this, DataMurid.getData_murid(), key);
                }
            }
        });
    }

    private DatePickerDialog datePickerDialog;

    private void input_tanggal() {
        tanggal = findViewById(R.id.inputTanggalLahir);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Tambah.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String tgl = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                tanggal.setText(tgl);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        input_tanggal();
    }
}