package com.example.a1projectfix.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;
import com.example.a1projectfix.tampilan_awal.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Daftar extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRePassword;
    private EditText editTextNama;
    private Button buttonRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);
        auth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.pass);
        editTextRePassword = findViewById(R.id.repass);
        buttonRegister = findViewById(R.id.btn_regis);
        editTextNama = findViewById(R.id.nama);

        // Mengatur type menjadi password
        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Menghapus teks default
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextEmail.setText("");
                }
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextPassword.setText("");
                }
            }
        });

        editTextRePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextRePassword.setText("");
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String rePassword = editTextRePassword.getText().toString();
                String nama = editTextNama.getText().toString();

                if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(Daftar.this, "Silakan Masukan Email, password, dan re-password yang benar yaa", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(Daftar.this, "Password dan re-password tidak sama", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DataUser d = new DataUser();
                                HashMap<String,Object> map = new HashMap<>();
                                map.put("nama",nama);
                                map.put("email",username);
                                map.put("nohp","Belum diset");
                                map.put("sabuk","Belum diset");
                                map.put("foto","");
                                map.put("selection","0");
                                d.updateUser(map);
                                Toast.makeText(Daftar.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Daftar.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Daftar.this,"Terjadi kesalahan, periksa koneksi internet", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }
}