package com.example.a1projectfix.daftar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.databinding.ActivityDetailMuridBinding;
import com.example.a1projectfix.databinding.ActivityKonfirmasiDetailsBinding;
import com.example.a1projectfix.databinding.DialogKonfirmasiBinding;
import com.example.a1projectfix.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class KonfirmasiDetails extends AppCompatActivity {
    private ActivityKonfirmasiDetailsBinding bind;
    private String keyNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityKonfirmasiDetailsBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        init();
    }

    private void init() {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Murid");
        Intent i = getIntent();

        keyNama = i.getStringExtra("nama");

        String foto1, foto2;
        foto1 = i.getStringExtra("foto1");
        foto2 = i.getStringExtra("foto2");

        if (foto1 != null && foto2 != null) {
            Picasso.get().load(foto1).into(bind.foto1);
            Picasso.get().load(foto2).into(bind.foto2);
        }


        bind.nama.setText(i.getStringExtra("nama"));
        bind.tanggal.setText(i.getStringExtra("tanggal"));
        bind.tempat.setText(i.getStringExtra("tempat"));
        bind.kelas.setText(i.getStringExtra("kelas"));
        bind.alamat.setText(i.getStringExtra("kelas"));
        long biaya = i.getLongExtra("biaya",0);
        bind.download.setText("Hapus");
        bind.save.setText("Konfirmasi");

        bind.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(KonfirmasiDetails.this);
                DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
                View dialogView = binding.getRoot();
                b.setView(dialogView);
                AlertDialog d = b.create();
                binding.judul.setText("Konfirmasi data ?");
                binding.hapus.setText("Konfirmasi");
                binding.batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                binding.hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = db.push().getKey();
                        Map<String, Object> data = new HashMap<>();
                        data.put("key", key);
                        data.put("nama", bind.nama.getText().toString());
                        data.put("tempat", bind.tempat.getText().toString());
                        data.put("tanggal", bind.tanggal.getText().toString());
                        data.put("kelas", bind.kelas.getText().toString());
                        data.put("alamat",bind.alamat.getText().toString());
                        data.put("biaya",biaya);
                        data.put("foto1", foto1);
                        data.put("foto2", foto2);
                        assert key != null;
                        db.child(key).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    db.child(keyNama).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(KonfirmasiDetails.this, "Berhasil konfirmasi", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                d.show();


            }
        });
        bind.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(KonfirmasiDetails.this);
                DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
                View dialogView = binding.getRoot();
                b.setView(dialogView);
                AlertDialog d = b.create();
                binding.judul.setText("Hapus data ?");
                binding.hapus.setText("Hapus");
                binding.batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                binding.hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.child(keyNama).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(KonfirmasiDetails.this, "Berhasil hapus", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                });
                d.show();
            }
        });
    }
}