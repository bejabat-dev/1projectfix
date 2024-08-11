package com.example.a1projectfix.pendaftaran;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a1projectfix.R;
import com.example.a1projectfix.utilitas.DataUser;

public class KelasKhusus extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kelas_khusus);
        init();
    }
    private void init(){
        Button b = findViewById(R.id.pilih);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUser.setPilihan("Khusus");
                finish();
            }
        });
    }
}
