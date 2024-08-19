package com.example.a1projectfix.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class EditProfil extends AppCompatActivity {
    private String sSabuk;
    private static final int PICK_IMAGE_REQUEST = 1;
    private int selection;
    private ImageView foto;
    private String sumber_foto = "unset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        init();
    }

    private void init(){
        Intent i = getIntent();
        String snama,snohp;
        snama = i.getStringExtra("nama");
        snohp = i.getStringExtra("nohp");
        EditText nama,nohp;
        Spinner sabuk = findViewById(R.id.sabuk);
        TextView konfirmasi = findViewById(R.id.konfirmasi);
        nama = findViewById(R.id.nama);
        nohp = findViewById(R.id.nohp);
        nama.setText(snama);
        nohp.setText(snohp);
        foto = findViewById(R.id.foto_profil);

        String sfoto = DataUser.getFoto();
        if (!sfoto.equals("unset")) {
            Picasso.get().load(sfoto).into(foto);
        }
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        selection = getIntent().getIntExtra("selection",0);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.spinner_layout,getResources().getStringArray(R.array.list_sabuk));
        sabuk.setAdapter(arrayAdapter);
        sabuk.setSelection(selection);
        sabuk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSabuk = sabuk.getSelectedItem().toString();
                selection = sabuk.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama,sNohp;
                sNama = nama.getText().toString();
                sNohp = nohp.getText().toString();
                if(TextUtils.isEmpty(sNama)||TextUtils.isEmpty(sNohp)){
                    Toast.makeText(getApplicationContext(),"Masukkan nama dan nomor HP yang benar",Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String,Object> data = new HashMap<>();
                    data.put("nama",sNama);
                    data.put("sabuk",sSabuk);
                    data.put("nohp",sNohp);
                    data.put("selection",String.valueOf(selection));
                    data.put("foto",sumber_foto);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
                    if(user!=null){
                        String uid = auth.getCurrentUser().getUid();
                        data.put("email",user.getEmail());
                        db.child(uid).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    showMsg("Profil berhasil diubah");
                                    finish();
                                }else{
                                    showMsg("Terjadi kesalahan. Coba lagi");
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    private void showMsg(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            String name = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
            assert name != null;
            StorageReference store = FirebaseStorage.getInstance().getReference(name);
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            View v = getLayoutInflater().inflate(R.layout.upload_foto,null);
            b.setView(v);
            AlertDialog d = b.setCancelable(false).create();
            d.show();
            store.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            foto.setImageURI(imageUri);
                            sumber_foto = uri.toString();
                            d.dismiss();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Terjadi kesalahan: "+e,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}