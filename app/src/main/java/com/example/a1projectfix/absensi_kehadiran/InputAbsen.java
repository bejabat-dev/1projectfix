package com.example.a1projectfix.absensi_kehadiran;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;

public class InputAbsen extends AppCompatActivity {
    private TextView nama;
    private Button simpan;
    private TextInputEditText tanggal,tempat,keterangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_absen);
        init();
        input_tanggal();
    }
    private DatePickerDialog datePickerDialog;
    private void input_tanggal(){
        tanggal = findViewById(R.id.tanggal);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(InputAbsen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String tgl = dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year;
                                tanggal.setText(tgl);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void init(){
        nama = findViewById(R.id.nama);
        tanggal = findViewById(R.id.tanggal);
        tempat = findViewById(R.id.tempat);
        keterangan = findViewById(R.id.keterangan);
        simpan = findViewById(R.id.simpan);
        nama.setText(DataUser.getNama());

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTanggal,sTempat,sKeterangan;
                sTanggal = tanggal.getText().toString();
                sTempat = tempat.getText().toString();
                sKeterangan = keterangan.getText().toString();
                if(sTanggal.isEmpty()||sTempat.isEmpty()||sKeterangan.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Penuhi semua kolom",Toast.LENGTH_SHORT).show();
                }else{
                    DataUser data = new DataUser();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("nama", DataUser.getNama());
                    map.put("tempat",sTempat);
                    map.put("tanggal",sTanggal);
                    map.put("keterangan",sKeterangan);
                    data.updateRiwayat(InputAbsen.this,map);
                }
            }
        });
    }
}