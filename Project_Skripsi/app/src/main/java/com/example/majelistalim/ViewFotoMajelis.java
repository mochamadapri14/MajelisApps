package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.majelistalim.Api.Retroserver;
import com.squareup.picasso.Picasso;

public class ViewFotoMajelis extends AppCompatActivity {
    ImageView foto;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_foto_majelis);
        foto= findViewById(R.id.imgView);
        toolbar = findViewById(R.id.tbIndex);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent data = getIntent();
        toolbar.setTitle("Foto "+data.getStringExtra("nama"));
        Picasso.get().load(Retroserver.url_foto+data.getStringExtra("foto"))
                .fit().centerInside().into(foto);

    }
}
