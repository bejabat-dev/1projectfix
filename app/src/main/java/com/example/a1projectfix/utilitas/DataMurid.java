package com.example.a1projectfix.utilitas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.a1projectfix.TambahFoto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataMurid {
    public DataMurid() {
    }

    private static String nama;
    private static String kelas;
    public static String key;

    public static String getNama() {
        return nama;
    }

    public static String getKelas() {
        return kelas;
    }

    public static String getTempat() {
        return tempat;
    }

    public static String getTanggal() {
        return tanggal;
    }

    public static String getAlamat() {
        return alamat;
    }

    private static String tempat;
    private static String tanggal;
    private static String alamat;

    public static String foto1, foto2, foto3;
    public static ArrayList<HashMap<String, Object>> list_murid;

    public static ArrayList<HashMap<String, Object>> getList_murid() {
        return list_murid;
    }

    public static HashMap<String, Object> getData_murid() {
        return data_murid;
    }

    public static HashMap<String, Object> data_murid;
    public static HashMap<String, Object> edit;

    public DataMurid(String key, String nama, String kelas, String tempat, String tanggal, String alamat) {

        DataMurid.nama = nama;
        DataMurid.kelas = kelas;
        DataMurid.tempat = tempat;
        DataMurid.tanggal = tanggal;
        DataMurid.alamat = alamat;
        data_murid = new HashMap<>();
        data_murid.put("key", key);
        data_murid.put("nama", nama);
        data_murid.put("kelas", kelas);
        data_murid.put("tempat", tempat);
        data_murid.put("tanggal", tanggal);
        data_murid.put("alamat", alamat);
        data_murid.put("foto1", "");
        data_murid.put("foto2", "");
        data_murid.put("foto3", "");
    }

    public void tambahMurid(Context c, HashMap<String, Object> map, String key) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Murid").child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(c, "Murid berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    c.startActivity(new Intent(c, TambahFoto.class));
                    ((Activity) c).finish();
                } else {
                    Toast.makeText(c, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void loadMurid() {
        list_murid = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Murid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Object>> map = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HashMap<String, Object> d = ds.getValue(map);
                    if (d.get("register") == null) {

                        list_murid.add(d);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}