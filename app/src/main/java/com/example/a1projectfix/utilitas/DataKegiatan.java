package com.example.a1projectfix.utilitas;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

public class DataKegiatan {

    public DataKegiatan(){

    }

    public static String getNama() {
        return nama;
    }

    public static String getTanggal() {
        return tanggal;
    }

    public static String getFoto() {
        return foto;
    }

    private static String nama,tanggal,foto;
    private static HashMap<String,String> data;

    public static HashMap<String, String> getData() {
        return data;
    }

    private static ArrayList<HashMap<String,String>> list_kegiatan;

    public static ArrayList<HashMap<String, String>> getList_kegiatan() {
        return list_kegiatan;
    }

    public DataKegiatan(String nama, String tanggal, String foto){
        DataKegiatan.nama = nama;
        DataKegiatan.tanggal = tanggal;
        DataKegiatan.foto = foto;
        data = new HashMap<>();
        data.put("nama",nama);
        data.put("tanggal",tanggal);
        data.put("foto",foto);
    }

    public void updateKegiatan(Context c,HashMap<String, String> map){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Kegiatan");
        String key = db.push().getKey();
        db.child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(c,"Berhasil ditambahkan",Toast.LENGTH_SHORT).show();
                    ((Activity)c).finish();
                }else{
                    Toast.makeText(c,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadKegiatan(){
        list_kegiatan = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Kegiatan");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String,String>> dt = new GenericTypeIndicator<HashMap<String, String>>() {};
                for(DataSnapshot ds : snapshot.getChildren()){
                    HashMap<String,String> d = ds.getValue(dt);
                    list_kegiatan.add(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
