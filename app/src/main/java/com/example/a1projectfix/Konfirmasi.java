package com.example.a1projectfix;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1projectfix.databinding.ActivityKonfirmasiBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Konfirmasi extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> dataSet;
    private ActivityKonfirmasiBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityKonfirmasiBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        loadMurid();
        CustomAdapter rvAdapter = new CustomAdapter(dataSet);
        bind.recycler.setAdapter(rvAdapter);
        bind.recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadMurid() {
        dataSet = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Murid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Object>> map = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HashMap<String, Object> d = ds.getValue(map);
                    dataSet.add(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nama, tanggal, alamat, kelas;
            private final LinearLayout card;
            private final ImageView foto1, foto2, foto3;

            public ViewHolder(View view) {
                super(view);
                nama = view.findViewById(R.id.nama);
                tanggal = view.findViewById(R.id.tanggal);
                kelas = view.findViewById(R.id.kelas);
                alamat = view.findViewById(R.id.alamat);
                card = view.findViewById(R.id.card);
                foto1 = view.findViewById(R.id.foto1);
                foto2 = view.findViewById(R.id.foto2);
                foto3 = view.findViewById(R.id.foto3);
            }

            public TextView getNama() {
                return nama;
            }

            public TextView getTanggal() {
                return tanggal;
            }

            public TextView getKelas() {
                return kelas;
            }

            public TextView getAlamat() {
                return alamat;
            }

            public LinearLayout getCard() {
                return card;
            }

            public ImageView getFoto1() {
                return foto1;
            }

            public ImageView getFoto2() {
                return foto2;
            }
        }


        public CustomAdapter(ArrayList<HashMap<String, Object>> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_murid, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            String tempat = localDataSet.get(position).get("tempat").toString();
            String tanggal = localDataSet.get(position).get("tanggal").toString();
            String ttl = tempat + ", " + tanggal;
            viewHolder.getNama().setText(localDataSet.get(position).get("nama").toString());
            viewHolder.getTanggal().setText(ttl);
            viewHolder.getAlamat().setText(localDataSet.get(position).get("alamat").toString());
            viewHolder.getKelas().setText(localDataSet.get(position).get("kelas").toString());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }


}