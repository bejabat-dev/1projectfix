package com.example.a1projectfix;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1projectfix.databinding.ActivityLaporanBiayaBinding;
import com.example.a1projectfix.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LaporanBiaya extends AppCompatActivity {
    private ActivityLaporanBiayaBinding bind;
    private int Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLaporanBiayaBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        initDatabase();
    }

    private void init() {

    }

    private void initDatabase() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Murid");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Total = 0;
                List<User> users = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    User user = s.getValue(User.class);
                    if (user != null) {
                        Total += user.getBiaya();
                        String newTotal = "Rp" + Total;
                        bind.total.setText(newTotal);
                        users.add(user);
                    }
                }
                CustomAdapter customAdapter = new CustomAdapter(users);
                bind.recycler.setAdapter(customAdapter);
                bind.recycler.setLayoutManager(new LinearLayoutManager(LaporanBiaya.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private final List<User> localDataSet;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nama, kelas, biaya;
            private final ImageView foto;

            public ViewHolder(View view) {
                super(view);
                nama = view.findViewById(R.id.nama);
                kelas = view.findViewById(R.id.kelas);
                biaya = view.findViewById(R.id.biaya);
                foto = view.findViewById(R.id.foto);
            }

            public TextView getNama() {
                return nama;
            }

            public TextView getKelas() {
                return kelas;
            }

            public TextView getBiaya() {
                return biaya;
            }

            public ImageView getFoto() {
                return foto;
            }
        }

        public CustomAdapter(List<User> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_biaya, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder v, final int position) {
            Picasso.get().load(localDataSet.get(position).getFoto1()).into(v.getFoto());
            String biaya = "Rp" + localDataSet.get(position).getBiaya();
            v.getNama().setText(localDataSet.get(position).getNama());
            v.getKelas().setText(localDataSet.get(position).getKelas());
            v.getBiaya().setText(biaya);
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}