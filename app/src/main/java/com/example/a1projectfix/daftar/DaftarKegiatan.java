package com.example.a1projectfix.daftar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1projectfix.InputKegiatan;
import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataKegiatan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class DaftarKegiatan extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        FloatingActionButton input = findViewById(R.id.input);
        RecyclerView rv = findViewById(R.id.recyclerKegiatan);
        CustomAdapter adapter = new CustomAdapter(DataKegiatan.getList_kegiatan());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        input.setOnClickListener(v -> startActivity(new Intent(DaftarKegiatan.this, InputKegiatan.class)));
    }

    public static class CustomAdapter extends RecyclerView.Adapter<DaftarKegiatan.CustomAdapter.ViewHolder> {

        private final ArrayList<HashMap<String,String>> localDataSet;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nama,tanggal;
            private final ImageView foto;

            public ViewHolder(View view) {
                super(view);

                nama = view.findViewById(R.id.nama);
                tanggal = view.findViewById(R.id.tanggal);
                foto = view.findViewById(R.id.foto);
            }

            public TextView getNama() {
                return nama;
            }

            public TextView getTanggal() {
                return tanggal;
            }

            public ImageView getFoto() {
                return foto;
            }
        }
        public CustomAdapter(ArrayList<HashMap<String,String>> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public DaftarKegiatan.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_kegiatan, viewGroup, false);

            return new DaftarKegiatan.CustomAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DaftarKegiatan.CustomAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getNama().setText(localDataSet.get(position).get("nama"));
            viewHolder.getTanggal().setText(localDataSet.get(position).get("tanggal"));
            Picasso.get().load(localDataSet.get(position).get("foto")).into(viewHolder.getFoto());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}