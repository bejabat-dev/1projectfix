package com.example.a1projectfix.daftar;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.a1projectfix.R;
import com.example.a1projectfix.databinding.DialogKonfirmasiBinding;
import com.example.a1projectfix.databinding.PdfLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DownloadData extends AppCompatActivity {
    private PdfLayoutBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = PdfLayoutBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        init();
    }

    private void init() {
        Intent i = getIntent();
        String nama = i.getStringExtra("nama");
        String tempat = i.getStringExtra("tempat");
        String tanggal = i.getStringExtra("tanggal");
        String kelas = i.getStringExtra("kelas");
        String alamat = i.getStringExtra("alamat");
        String foto1 = i.getStringExtra("foto1");

        Picasso.get().load(foto1).into(bind.foto1);

        bind.nama.setText(nama);
        bind.tempalLahir.setText(tempat);
        bind.tanggalLahir.setText(tanggal);
        bind.kelas.setText(kelas);
        bind.alamat.setText(alamat);

        bind.simpan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder b = new AlertDialog.Builder(DownloadData.this);
                        DialogKonfirmasiBinding binding = DialogKonfirmasiBinding.inflate(getLayoutInflater());
                        View dialogView = binding.getRoot();
                        b.setView(dialogView);
                        AlertDialog d = b.create();
                        binding.judul.setText("Download data murid ?");
                        binding.hapus.setText("Download");
                        binding.batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                d.dismiss();
                            }
                        });
                        binding.hapus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                generatePdf(bind.pdf, getContentResolver(),nama);
                                finish();
                            }
                        });
                        d.show();
                    }
                }
        );
    }

    public void generatePdf(View view, ContentResolver contentResolver,String name) {
        Toast.makeText(DownloadData.this,"Menyimpan data",Toast.LENGTH_SHORT).show();

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        // Create a PdfDocument
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas pdfCanvas = page.getCanvas();

        // Draw the bitmap onto the PDF canvas
        pdfCanvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // Save the document via MediaStore
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name+".pdf");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/");

        try {
            // Insert the PDF into MediaStore and get a URI to it
            Uri uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values);

            if (uri != null) {
                ParcelFileDescriptor pfd = contentResolver.openFileDescriptor(uri, "w");
                if (pfd != null) {
                    FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
                    document.writeTo(fos);
                    fos.close();
                    pfd.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the document
        document.close();

        Toast.makeText(DownloadData.this,"Data tersimpan di Dokumen",Toast.LENGTH_SHORT).show();
    }
}