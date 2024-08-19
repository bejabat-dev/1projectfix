package com.example.a1projectfix.utilitas;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataUser {
    public static String getNama() {
        return nama;
    }

    public static String getSabuk() {
        return sabuk;
    }

    public static String getEmail() {
        return email;
    }

    public static String getNohp() {
        return nohp;
    }

    public static String getFoto() {
        return foto;
    }

    public static String getSelection() {
        return selection;
    }

    public static String getRole() {
        return role;
    }

    private static String nama,sabuk,email,nohp,foto,selection,role;

    public static ArrayList<HashMap<String, Object>> getList_riwayat() {
        return list_riwayat;
    }

    public static ArrayList<HashMap<String, Object>> getList_user() {
        return list_user;
    }

    private static ArrayList<HashMap<String,Object>> list_riwayat;
    private static ArrayList<HashMap<String,Object>> list_user;

    public DataUser(){

    }
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference db;

    public static String getPilihan() {
        return pilihan;
    }

    public static void setPilihan(String pilihan) {
        DataUser.pilihan = pilihan;
    }

    private static String pilihan;

    public void loadUser(){
        db = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        list_user = new ArrayList<>();
        db.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama = snapshot.child("nama").getValue(String.class);
                sabuk = snapshot.child("sabuk").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                nohp = snapshot.child("nohp").getValue(String.class);
                foto = snapshot.child("foto").getValue(String.class);
                selection = snapshot.child("selection").getValue(String.class);
                role = snapshot.child("role").getValue(String.class);
                Log.e("ERROR","ROLE : "+role);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void updateUser(HashMap<String,Object> map){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user!=null) {
            String uid = user.getUid();
            db = FirebaseDatabase.getInstance().getReference("Users/"+uid);
            db.setValue(map);
        }
    }

    public void updateRiwayat(Context c, HashMap<String,Object> map) {
        auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            if(user!=null) {
                db = FirebaseDatabase.getInstance().getReference("Riwayat");
                String key = db.push().getKey();
                db.child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(c,"Input berhasil",Toast.LENGTH_SHORT).show();
                            loadRiwayat();
                            ((Activity)c).finish();
                        }else{
                            Toast.makeText(c,"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }

    public void loadRiwayat(){
        list_riwayat = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Riwayat");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String,Object>> dt = new GenericTypeIndicator<HashMap<String, Object>>() {};
                for(DataSnapshot ds : snapshot.getChildren()){
                    HashMap<String,Object> data = ds.getValue(dt);
                    list_riwayat.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public FirebaseUser cekLogin(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        return user;
    }
}
