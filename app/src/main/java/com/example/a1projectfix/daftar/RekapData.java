package com.example.a1projectfix.daftar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class RekapData extends AppCompatActivity {
    private RecyclerView rv;
    private EditText search;
    private ArrayList<HashMap<String, Object>> search_filter;
    private ArrayList<HashMap<String, Object>> init_filter;
    private ImageView filter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView list_download;
    public static ArrayAdapter<String> download_arrays;
    public static ArrayList<String> adapter_download = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_data);
        download_arrays = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,adapter_download);

        list_download = findViewById(R.id.list_download);
        list_download.setAdapter(download_arrays);
        filter = findViewById(R.id.filter);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        DataMurid.loadMurid();
        init(RekapData.this);
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
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilter(context, filter);
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    private ArrayList<HashMap<String, Object>> filtered;
    private ArrayList<HashMap<String, Object>> filteredResult;

    private void showFilter(Context context, View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.filter, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

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
            }
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

    public class CustomAdapter extends RecyclerView.Adapter<RekapData.CustomAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> localDataSet;
        private Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nama, tanggal, alamat, kelas;
            private final LinearLayout card;
            private final ImageView foto;

            public ViewHolder(View view) {
                super(view);
                nama = view.findViewById(R.id.nama);
                tanggal = view.findViewById(R.id.tanggal);
                kelas = view.findViewById(R.id.kelas);
                alamat = view.findViewById(R.id.alamat);
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
        public RekapData.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_murid, viewGroup, false);

            return new RekapData.CustomAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RekapData.CustomAdapter.ViewHolder viewHolder, final int position) {
            String key = localDataSet.get(position).get("key").toString();
            String tempat = localDataSet.get(position).get("tempat").toString();
            String tanggal = localDataSet.get(position).get("tanggal").toString();

            String foto1 = localDataSet.get(position).get("foto1").toString();
            String foto2 = localDataSet.get(position).get("foto2").toString();

            String nama = localDataSet.get(position).get("nama").toString();
            String kelas = localDataSet.get(position).get("kelas").toString();

            String alamat = localDataSet.get(position).get("alamat").toString();
            String ttl = tempat + ", " + tanggal;
            String pos = String.valueOf(position + 1) + ".";
            viewHolder.getNama().setText(localDataSet.get(position).get("nama").toString());
            viewHolder.getTanggal().setText(ttl);
            viewHolder.getAlamat().setText(localDataSet.get(position).get("alamat").toString());
            viewHolder.getKelas().setText(localDataSet.get(position).get("kelas").toString());


            if(!foto1.equals("unset")) {
                Picasso.get().load(foto1).into(viewHolder.getFoto());
            }

            viewHolder.getCard().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
    adapter_download.add(nama);
    download_arrays.notifyDataSetChanged();
                }
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