package com.example.a1projectfix.absensi_kehadiran;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class Absen extends AppCompatActivity {
    private FloatingActionButton b;
    private RecyclerView rv;
    private DataUser data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riwayat_absen);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }


    private void init(){
        b = findViewById(R.id.input);
        b.setOnClickListener(v -> startActivity(new Intent(Absen.this, InputAbsen.class)));
        rv = findViewById(R.id.recyclerRekapAbsen);
        if(DataUser.getList_riwayat()!=null){
            CustomAdapter adapter = new CustomAdapter(DataUser.getList_riwayat());
            rv.setAdapter(adapter);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
    public static class CustomAdapter extends RecyclerView.Adapter<Absen.CustomAdapter.ViewHolder> {

        private ArrayList<HashMap<String,Object>> localDataSet;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nama,tempat,tanggal,keterangan;

            public ViewHolder(View view) {
                super(view);

                nama = view.findViewById(R.id.nama);
                tempat = view.findViewById(R.id.tempat);
                tanggal = view.findViewById(R.id.tanggal);
                keterangan = view.findViewById(R.id.keterangan);
            }

            public TextView getNama() {
                return nama;
            }

            public TextView getKeterangan() {
                return keterangan;
            }

            public TextView getTanggal() {
                return tanggal;
            }

            public TextView getTempat() {
                return tempat;
            }
        }
        public CustomAdapter(ArrayList<HashMap<String,Object>> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public Absen.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_absen, viewGroup, false);

            return new Absen.CustomAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Absen.CustomAdapter.ViewHolder viewHolder, final int position) {
            viewHolder.getNama().setText(localDataSet.get(position).get("nama").toString());
            viewHolder.getKeterangan().setText(localDataSet.get(position).get("keterangan").toString());
            viewHolder.getTanggal().setText(localDataSet.get(position).get("tanggal").toString());
            viewHolder.getTempat().setText(localDataSet.get(position).get("tempat").toString());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}