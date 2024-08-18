package com.example.a1projectfix.daftar;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.R;
import com.example.a1projectfix.databinding.ActivityDetailMuridBinding;
import com.example.a1projectfix.databinding.ActivityKonfirmasiDetailsBinding;
import com.example.a1projectfix.databinding.DialogKonfirmasiBinding;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DetailMurid extends AppCompatActivity {
    private ActivityDetailMuridBinding bind;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailMuridBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        init();
    }

    private void init() {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Murid");
        Intent i = getIntent();

        key = i.getStringExtra("key");

        String foto1, foto2;
        foto1 = i.getStringExtra("foto1");
        foto2 = i.getStringExtra("foto2");

        if (foto1 != null && foto2 != null) {
            Picasso.get().load(foto1).into(bind.foto1);
            Picasso.get().load(foto2).into(bind.foto2);
        }
        if (DataUser.role == null) {
            bind.hapus.setVisibility(View.GONE);
        } else {
            bind.hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder b = new AlertDialog.Builder(DetailMurid.this);
                    DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
                    View dialogView = binding.getRoot();
                    b.setView(dialogView);
                    AlertDialog d = b.create();
                    binding.batal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            d.dismiss();
                        }
                    });
                    binding.hapus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.child(key).removeValue();
                            Toast.makeText(DetailMurid.this, "Berhasil hapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    d.show();
                }
            });
        }
        String nama = i.getStringExtra("nama");
        String tempat = i.getStringExtra("tempat");
        String tanggal = i.getStringExtra("tanggal");
        String kelas = i.getStringExtra("kelas");
        String alamat = i.getStringExtra("alamat");

        bind.nama.setText(nama);
        bind.tempat.setText(tempat);
        bind.tanggal.setText(tanggal);
        bind.kelas.setText(kelas);
        bind.alamat.setText(alamat);
        bind.download.setText("Download data");
        bind.save.setText("Simpan");

        bind.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(DetailMurid.this);
                DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
                View dialogView = binding.getRoot();
                b.setView(dialogView);
                AlertDialog d = b.create();
                binding.judul.setText("Simpan perubahan ?");
                binding.hapus.setText("Simpan");
                binding.batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                binding.hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("nama", bind.nama.getText().toString());
                        data.put("tempat", bind.tempat.getText().toString());
                        data.put("tanggal", bind.tanggal.getText().toString());
                        data.put("kelas", bind.kelas.getText().toString());
                        data.put("alamat", bind.alamat.getText().toString());

                        db.child(key).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailMurid.this, "Berhasil simpan", Toast.LENGTH_SHORT).show();
                                    finish();
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
                Intent i = new Intent(DetailMurid.this, DownloadData.class);
                i.putExtra("key", key);
                i.putExtra("nama", nama);
                i.putExtra("tempat", tempat);
                i.putExtra("tanggal", tanggal);
                i.putExtra("kelas", kelas);
                i.putExtra("alamat", alamat);
                i.putExtra("foto1", foto1);
                i.putExtra("foto2", foto2);
                startActivity(i);
            }
        });
    }


}