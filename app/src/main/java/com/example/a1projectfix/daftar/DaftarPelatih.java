package com.example.a1projectfix.daftar;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class DaftarPelatih extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_pelatih);
        init();
    }
    private ArrayList<HashMap<String,Object>> arrays;
    private void init(){
        arrays = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String,Object>> a = new GenericTypeIndicator<HashMap<String, Object>>() {};
                for(DataSnapshot ds : snapshot.getChildren()){
                    HashMap<String,Object> data = ds.getValue(a);
                    arrays.add(data);
                }
                recyclerView = findViewById(R.id.recycler);
                CustomAdapter adapter = new CustomAdapter(arrays);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(DaftarPelatih.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<HashMap<String,Object>> localDataSet;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nomor,nama,sabuk;
            private final ShapeableImageView foto;

            public ViewHolder(View view) {
                super(view);

                nama = view.findViewById(R.id.nama);
                sabuk = view.findViewById(R.id.sabuk);
                nomor = view.findViewById(R.id.nomor);
                foto = view.findViewById(R.id.foto);
            }

            public TextView getNama() {
                return nama;
            }

            public TextView getNomor() {
                return nomor;
            }

            public TextView getSabuk() {
                return sabuk;
            }

            public ShapeableImageView getFoto() {
                return foto;
            }
        }
        public CustomAdapter(ArrayList<HashMap<String,Object>> dataSet) {
            localDataSet = dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_daftar_pelatih, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            String pos = String.valueOf(position+1);
            String sabuk = "Sabuk : " + localDataSet.get(position).get("sabuk").toString();
            String url_foto = localDataSet.get(position).get("foto").toString();
            viewHolder.getNama().setText(localDataSet.get(position).get("nama").toString());
            viewHolder.getSabuk().setText(sabuk);
            viewHolder.getNomor().setText(pos);
            if(!url_foto.equals("")){
                Picasso.get().load(url_foto).into(viewHolder.getFoto());
            }
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

}