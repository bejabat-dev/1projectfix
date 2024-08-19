package com.example.a1projectfix.daftar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.databinding.ActivityDetailMuridBinding;
import com.example.a1projectfix.databinding.DialogKonfirmasiBinding;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

    String nextKey, nextFoto;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        uploadImageToFirebase(selectedImageUri, nextKey, nextFoto);
                    }
                }
            }
    );

    private void uploadImageToFirebase(Uri imageUri, String key, String foto) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Murid");
        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imagesRef = storageRef.child("images/" + UUID.randomUUID().toString());
            imagesRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        String downloadUrl = downloadUri.toString();
                        Map<String, Object> data = new HashMap<>();
                        data.put(foto, downloadUrl);
                        db.child(key).updateChildren(data).addOnCompleteListener(task -> {
                            if (Objects.equals(foto, "foto1")) {
                                Picasso.get().load(downloadUrl).into(bind.foto1);
                            } else if (foto.equals("foto2")) {
                                Picasso.get().load(downloadUrl).into(bind.foto2);
                            }
                        });
                    }))
                    .addOnFailureListener(exception -> {
                        // Handle failed upload
                        Log.e("FirebaseUpload", "Upload failed", exception);
                    });
        }
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Murid");
        Intent i = getIntent();

        key = i.getStringExtra("key");

        String foto1, foto2;
        foto1 = i.getStringExtra("foto1");
        foto2 = i.getStringExtra("foto2");

        bind.foto1.setOnClickListener(view -> {
            nextKey = key;
            nextFoto = "foto1";
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });
        bind.foto2.setOnClickListener(view -> {
            nextKey = key;
            nextFoto = "foto2";
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        if (foto1 != null && foto2 != null) {
            Picasso.get().load(foto1).into(bind.foto1);
            Picasso.get().load(foto2).into(bind.foto2);
        }
        if (DataUser.role != "admin") {
            bind.hapus.setVisibility(View.GONE);
        } else {
            bind.hapus.setOnClickListener(v -> {
                AlertDialog.Builder b = new AlertDialog.Builder(DetailMurid.this);
                DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
                View dialogView = binding.getRoot();
                b.setView(dialogView);
                AlertDialog d = b.create();
                binding.batal.setOnClickListener(view -> d.dismiss());
                binding.hapus.setOnClickListener(view -> {
                    db.child(key).removeValue();
                    Toast.makeText(DetailMurid.this, "Berhasil hapus", Toast.LENGTH_SHORT).show();
                    finish();
                });
                d.show();
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

        bind.save.setOnClickListener(v -> {
            AlertDialog.Builder b = new AlertDialog.Builder(DetailMurid.this);
            DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
            View dialogView = binding.getRoot();
            b.setView(dialogView);
            AlertDialog d = b.create();
            binding.judul.setText("Simpan perubahan ?");
            binding.hapus.setText("Simpan");
            binding.batal.setOnClickListener(view -> d.dismiss());
            binding.hapus.setOnClickListener(view -> {
                Map<String, Object> data = new HashMap<>();
                data.put("nama", bind.nama.getText().toString());
                data.put("tempat", bind.tempat.getText().toString());
                data.put("tanggal", bind.tanggal.getText().toString());
                data.put("kelas", bind.kelas.getText().toString());
                data.put("alamat", bind.alamat.getText().toString());

                db.child(key).updateChildren(data).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DetailMurid.this, "Berhasil simpan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            });
            d.show();

        });
        bind.download.setOnClickListener(
                v -> {
                    Intent i1 = new Intent(DetailMurid.this, DownloadData.class);
                    i1.putExtra("key", key);
                    i1.putExtra("nama", nama);
                    i1.putExtra("tempat", tempat);
                    i1.putExtra("tanggal", tanggal);
                    i1.putExtra("kelas", kelas);
                    i1.putExtra("alamat", alamat);
                    i1.putExtra("foto1", foto1);
                    i1.putExtra("foto2", foto2);
                    startActivity(i1);
                });
    }


}