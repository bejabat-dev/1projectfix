package com.example.a1projectfix.daftar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.databinding.ActivityDetailMuridBinding;
import com.example.a1projectfix.databinding.ActivityKonfirmasiDetailsBinding;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.HashMap;
import java.util.Map;

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
                    db.child(key).removeValue();
                    Toast.makeText(DetailMurid.this, "Berhasil hapus", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

        bind.nama.setText(i.getStringExtra("nama"));
        bind.tempat.setText(i.getStringExtra("tempat"));
        bind.tanggal.setText(i.getStringExtra("tanggal"));
        bind.kelas.setText(i.getStringExtra("kelas"));
        bind.alamat.setText(i.getStringExtra("alamat"));
        bind.download.setText("Download data");
        bind.save.setText("Simpan");

        bind.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        bind.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetailMurid.this, "Data sedang didownload", Toast.LENGTH_SHORT).show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                assert foto1 != null;
                assert foto2 != null;

                File localFile = new File(getExternalFilesDir(null), foto1 + ".jpg");
                File localFile2 = new File(getExternalFilesDir(null), foto2 + ".jpg");
                FileDownloadTask task1 = storageReference.child(foto2).getFile(localFile);
                FileDownloadTask task2 = storageReference.child(foto1).getFile(localFile2);
                Tasks.whenAll(task1, task2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DetailMurid.this, "Berhasil download", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}