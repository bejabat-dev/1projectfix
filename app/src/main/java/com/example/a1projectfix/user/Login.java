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
import com.example.a1projectfix.tampilan_awal.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.pass);
        buttonLogin = findViewById(R.id.btn_login);

        // Menghapus teks default
        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextUsername.setText("");

                }
            }
        });

        // Mengubah input type
        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Menghapus teks default
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextPassword.setText("");
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                // Redirect to main activity
                                Intent intent = new Intent(Login.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Aduhhh, Email Atau Password Nya Salah Nih Gesss", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}