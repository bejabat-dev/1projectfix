package com.example.a1projectfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a1projectfix.pendaftaran.Tambah;
import com.example.a1projectfix.tampilan_awal.Dashboard;
import com.example.a1projectfix.user.Profil;
import com.example.a1projectfix.utilitas.DataMurid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TambahFoto extends AppCompatActivity {
    private Button bt_selesai;
    private String url;
    private ImageView foto1;
    private ImageView foto2;
    private ImageView foto3;
    private int idfoto;

    private ImageView currentFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_foto);
        foto1 = findViewById(R.id.foto1);
        foto2 = findViewById(R.id.foto2);
        foto3 = findViewById(R.id.foto3);
        bt_selesai = findViewById(R.id.btn_selesai);

        if(!Objects.equals(DataMurid.foto1, "")){
            foto1.setImageURI(Uri.parse(DataMurid.foto1));
        }
        if(!Objects.equals(DataMurid.foto2, "")){
            foto2.setImageURI(Uri.parse(DataMurid.foto2));
        }
        if(!Objects.equals(DataMurid.foto3, "")){
            foto3.setImageURI(Uri.parse(DataMurid.foto3));
        }

        foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(foto1);
            }
        });
        foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(foto2);
            }
        });
        foto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(foto3);
            }
        });
    }
    private static final int PICK_IMAGE_REQUEST = 1;
    private void openGallery(ImageView foto) {
        currentFoto = foto;
        if(currentFoto == foto1){
            idfoto = 1;
        }if(currentFoto == foto2){
            idfoto = 2;
        }if(currentFoto == foto3){
            idfoto = 3;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
            String uid = auth.getCurrentUser().getUid();
            Uri imageUri = data.getData();
            StorageReference store = FirebaseStorage.getInstance().getReference(email+idfoto);
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
                            url = uri.toString();
                            Map<String, Object> data = new HashMap<>();
                            data.put("foto"+idfoto,url);
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Murid");

                            db.child(DataMurid.key).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        currentFoto.setImageURI(imageUri);
                                        d.dismiss();
                                    }else{
                                        d.dismiss();
                                    }
                                }
                            });
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