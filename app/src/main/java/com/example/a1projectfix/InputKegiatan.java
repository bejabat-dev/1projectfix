package com.example.a1projectfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a1projectfix.absensi_kehadiran.InputAbsen;
import com.example.a1projectfix.utilitas.DataKegiatan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class InputKegiatan extends AppCompatActivity {
    private TextInputEditText nama,tanggal;
    private Button simpan;
    private ImageView foto;
    private Boolean upload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_kegiatan);
        init();
        input_tanggal();
    }

    private void init(){

        nama = findViewById(R.id.nama_kegiatan);
        tanggal = findViewById(R.id.tanggal);
        simpan = findViewById(R.id.simpan);
        foto = findViewById(R.id.foto_kegiatan);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNama,sTanggal;
                sNama = nama.getText().toString();
                sTanggal = tanggal.getText().toString();
                if(sNama.isEmpty() || sTanggal.isEmpty() || !upload){
                    Toast.makeText(getApplicationContext(),"Isi semua kolom dan foto",Toast.LENGTH_SHORT).show();
                }else{
                    DataKegiatan data = new DataKegiatan(sNama,sTanggal,sumber_foto);
                    data.updateKegiatan(InputKegiatan.this,DataKegiatan.getData());
                }
            }
        });
        }
    private DatePickerDialog datePickerDialog;
    private void input_tanggal(){
        tanggal = findViewById(R.id.tanggal);

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(InputKegiatan.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String tgl = dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year;
                                tanggal.setText(tgl);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
    private static final int PICK_IMAGE_REQUEST = 1;
    private String sumber_foto;

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
            String url = imageUri.toString();
            StorageReference store = FirebaseStorage.getInstance().getReference(url);
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
                            upload = true;
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