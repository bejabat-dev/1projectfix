package com.example.a1projectfix.daftar;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1projectfix.databinding.PdfLayoutBinding;
import com.example.a1projectfix.databinding.PdfLayoutBulkBinding;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DownloadDataBulk extends AppCompatActivity {
    private PdfLayoutBulkBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = PdfLayoutBulkBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        init();
    }

    private void init() {
        Intent i = getIntent();

        @SuppressWarnings("unchecked") // Suppress the unchecked cast warning
        ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) i.getSerializableExtra("data");
        if (data != null) {

            Toast.makeText(DownloadDataBulk.this, "Menyimpan data", Toast.LENGTH_SHORT).show();
            for (HashMap<String, Object> map : data) {
                String nama = (String) map.get("nama");
                String tempat = (String) map.get("tempat");
                String tanggal = (String) map.get("tanggal");
                String kelas = (String) map.get("kelas");
                String alamat = (String) map.get("alamat");
                String foto1 = (String) map.get("foto1");
                String foto2 = (String) map.get("foto2");

                Picasso.get().load(foto1).into(bind.foto1);
                Picasso.get().load(foto2).into(bind.foto2);

                bind.nama.setText(nama);
                bind.tempalLahir.setText(tempat);
                bind.tanggalLahir.setText(tanggal);
                bind.kelas.setText(kelas);
                bind.alamat.setText(alamat);
                bind.pdf.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                bind.pdf.layout(0, 0, bind.pdf.getMeasuredWidth(), bind.pdf.getMeasuredHeight());

                generatePdf(bind.pdf, getContentResolver(), nama);
            }
            Toast.makeText(DownloadDataBulk.this, "Data tersimpan di Dokumen", Toast.LENGTH_SHORT).show();
finish();
        }
    }

    private void generatePdf(View view, ContentResolver contentResolver, String name) {

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
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".pdf");
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

    }
}