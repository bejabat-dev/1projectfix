package com.example.a1projectfix.daftar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1projectfix.R;
import com.example.a1projectfix.databinding.ActivityKonfirmasiBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Konfirmasi extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> dataSet;
    private ActivityKonfirmasiBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityKonfirmasiBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        loadMurid(this);
        CustomAdapter rvAdapter = new CustomAdapter(this, dataSet);
        bind.recycler.setAdapter(rvAdapter);
        bind.recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMurid(this);
    }

    public void loadMurid(Context context) {
        dataSet = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Murid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSet = new ArrayList<>();
                GenericTypeIndicator<HashMap<String, Object>> map = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HashMap<String, Object> d = ds.getValue(map);
                    assert d != null;
                    if (Objects.equals(d.get("register"), true)) {
                        dataSet.add(d);
                    }
                }
                CustomAdapter rvAdapter = new CustomAdapter(context, dataSet);
                bind.recycler.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> localDataSet;
        private Context localContext;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nama, tanggal, alamat, kelas;
            private final LinearLayout card;
            private final ImageView foto;

            public ViewHolder(View view) {
                super(view);
                nama = view.findViewById(R.id.nama);
                tanggal = view.findViewById(R.id.tanggal);
                kelas = view.findViewById(R.id.kelas);
                alamat = view.findViewById(R.id.biaya);
                card = view.findViewById(R.id.card);
                foto = view.findViewById(R.id.foto);
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

            public ImageView getFoto() {
                return foto;
            }
        }


        public CustomAdapter(Context mContext, ArrayList<HashMap<String, Object>> dataSet) {
            localDataSet = dataSet;
            localContext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_murid, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            String key = localDataSet.get(position).get("key").toString();
            String tempat = localDataSet.get(position).get("tempat").toString();
            String tanggal = localDataSet.get(position).get("tanggal").toString();

            String foto1 = localDataSet.get(position).get("foto1").toString();
            String foto2 = localDataSet.get(position).get("foto2").toString();

            String nama = localDataSet.get(position).get("nama").toString();
            String kelas = localDataSet.get(position).get("kelas").toString();
            long biaya = (long) localDataSet.get(position).get("biaya");

            String alamat = localDataSet.get(position).get("alamat").toString();
            String ttl = tempat + ", " + tanggal;
            viewHolder.getNama().setText(localDataSet.get(position).get("nama").toString());
            viewHolder.getTanggal().setText(ttl);
            viewHolder.getAlamat().setText(localDataSet.get(position).get("alamat").toString());
            viewHolder.getKelas().setText(localDataSet.get(position).get("kelas").toString());
            Picasso.get().load(foto1).into(viewHolder.getFoto());

            viewHolder.getCard().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(localContext, KonfirmasiDetails.class);
                    i.putExtra("key",key);
                    i.putExtra("nama", nama);
                    i.putExtra("tempat", tempat);
                    i.putExtra("tanggal", tanggal);
                    i.putExtra("kelas", kelas);
                    i.putExtra("alamat", alamat);
                    i.putExtra("foto1", foto1);
                    i.putExtra("foto2", foto2);
                    i.putExtra("biaya",biaya);
                    localContext.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }


}