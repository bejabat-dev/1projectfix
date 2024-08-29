package com.example.a1projectfix.daftar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.a1projectfix.R;
import com.example.a1projectfix.pendaftaran.Tambah;
import com.example.a1projectfix.utilitas.DataMurid;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DaftarMurid extends AppCompatActivity {
    private RecyclerView rv;
    private EditText search;
    private ArrayList<HashMap<String, Object>> search_filter;
    private ArrayList<HashMap<String, Object>> init_filter;
    private ImageView filter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public DaftarMurid() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_murid);
        filter = findViewById(R.id.filter);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this::onResume);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataMurid.loadMurid();
        init(DaftarMurid.this);
    }

    private void initFilter(Context context, String kelas) {
        init_filter = new ArrayList<>();
        ArrayList<HashMap<String, Object>> oldData = DataMurid.getList_murid();
        for (int i = 0; i < oldData.size(); i++) {
            if (Objects.requireNonNull(oldData.get(i).get("kelas")).toString().equals(kelas)) {
                init_filter.add(oldData.get(i));
            }
        }
        CustomAdapter adapter = new CustomAdapter(context, init_filter);
        rv.setAdapter(adapter);

    }

    private void filteredsearch(Context context) {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<HashMap<String, Object>> filter = new ArrayList<>();
                for (int i = 0; i < init_filter.size(); i++) {
                    if (Objects.requireNonNull(init_filter.get(i).get("nama")).toString().toLowerCase().contains(s.toString().toLowerCase())) {
                        filter.add(init_filter.get(i));
                    }
                }
                CustomAdapter adapter = new CustomAdapter(context, filter);
                rv.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init(Context context) {
        search = findViewById(R.id.search);
        rv = findViewById(R.id.recycler);
        CustomAdapter adapter = new CustomAdapter(context, DataMurid.getList_murid());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        searchByNama(context);
        filter.setOnClickListener(v -> showFilter(context, filter));
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showFilter(Context context, View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.filter, menu.getMenu());

        menu.setOnMenuItemClickListener(item -> {

            if (Objects.equals(item.getTitle(), "Nama")) {
                CustomAdapter adapter = new CustomAdapter(context, DataMurid.getList_murid());
                rv.setAdapter(adapter);
                searchByNama(context);
            }
            if (Objects.equals(item.getTitle(), "Kelas Reguler")) {
                initFilter(context, "Reguler");
                filteredsearch(context);
            }
            if (Objects.equals(item.getTitle(), "Kelas Prestasi")) {
                initFilter(context, "Prestasi");
                filteredsearch(context);
            }
            if (Objects.equals(item.getTitle(), "Kelas Khusus")) {
                initFilter(context, "Khusus");
                filteredsearch(context);
            }
            return true;
        });
        menu.show();
    }

    private void searchByNama(Context context) {
        ArrayList<HashMap<String, Object>> oldDataMurid = DataMurid.getList_murid();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    CustomAdapter adapter = new CustomAdapter(context, oldDataMurid);
                    rv.setAdapter(adapter);
                }
                search_filter = new ArrayList<>();
                for (int i = 0; i < oldDataMurid.size(); i++) {
                    if (Objects.requireNonNull(oldDataMurid.get(i).get("nama")).toString().toLowerCase().contains(s.toString().toLowerCase())) {
                        search_filter.add(oldDataMurid.get(i));
                    }
                }
                CustomAdapter adapter = new CustomAdapter(context, search_filter);
                rv.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static class CustomAdapter extends RecyclerView.Adapter<DaftarMurid.CustomAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> localDataSet;
        private final Context context;

        public static class ViewHolder extends RecyclerView.ViewHolder {
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

            public TextView getTanggal() {
                return tanggal;
            }


            public LinearLayout getCard() {
                return card;
            }

            public TextView getAlamat() {
                return alamat;
            }


            public TextView getKelas() {
                return kelas;
            }

            public TextView getNama() {
                return nama;
            }

            public ImageView getFoto() {
                return foto;
            }
        }

        public CustomAdapter(Context mContext, ArrayList<HashMap<String, Object>> dataSet) {
            localDataSet = dataSet;
            context = mContext;
        }

        @NonNull
        @Override
        public DaftarMurid.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_murid, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DaftarMurid.CustomAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
            String key = Objects.requireNonNull(localDataSet.get(position).get("key")).toString();
            String tempat = Objects.requireNonNull(localDataSet.get(position).get("tempat")).toString();
            String tanggal = Objects.requireNonNull(localDataSet.get(position).get("tanggal")).toString();

            String foto1 = Objects.requireNonNull(localDataSet.get(position).get("foto1")).toString();
            String foto2 = Objects.requireNonNull(localDataSet.get(position).get("foto2")).toString();

            String nama = Objects.requireNonNull(localDataSet.get(position).get("nama")).toString();
            String kelas = Objects.requireNonNull(localDataSet.get(position).get("kelas")).toString();

            String alamat = Objects.requireNonNull(localDataSet.get(position).get("alamat")).toString();
            String ttl = tempat + ", " + tanggal;
            viewHolder.getNama().setText(Objects.requireNonNull(localDataSet.get(position).get("nama")).toString());
            viewHolder.getTanggal().setText(ttl);
            viewHolder.getAlamat().setText(Objects.requireNonNull(localDataSet.get(position).get("alamat")).toString());
            viewHolder.getKelas().setText(Objects.requireNonNull(localDataSet.get(position).get("kelas")).toString());


            if(!foto1.equals("unset")) {
                Picasso.get().load(foto1).into(viewHolder.getFoto());
            }


            viewHolder.getCard().setOnClickListener(v -> {
                Intent i = new Intent(context,DetailMurid.class);
                i.putExtra("key",key);
                i.putExtra("nama",nama);
                i.putExtra("tempat",tempat);
                i.putExtra("tanggal",tanggal);
                i.putExtra("kelas",kelas);
                i.putExtra("alamat",alamat);
                i.putExtra("foto1",foto1);
                i.putExtra("foto2",foto2);
                context.startActivity(i);
            });

            viewHolder.getCard().setOnLongClickListener(v -> {
                DataMurid.foto1 = Objects.requireNonNull(localDataSet.get(position).get("foto1")).toString();
                DataMurid.foto2 = Objects.requireNonNull(localDataSet.get(position).get("foto2")).toString();
                DataMurid.key = key;
                Intent i = new Intent(context, Tambah.class);
                i.putExtra("nama", nama);
                i.putExtra("tempat", tempat);
                i.putExtra("editing", true);
                i.putExtra("alamat", alamat);
                i.putExtra("kelas", kelas);
                i.putExtra("tanggal", tanggal);
                context.startActivity(i);
                return true;
            });
        }

        @Override
        public int getItemCount() {
            int size = 0;
            if (localDataSet != null) {
                size = localDataSet.size();
            }
            return size;
        }
    }
}