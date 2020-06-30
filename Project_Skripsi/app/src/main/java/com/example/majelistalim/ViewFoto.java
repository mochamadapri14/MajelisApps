package com.example.majelistalim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.majelistalim.Api.Retroserver;
import com.example.majelistalim.Session.SessionManager;
import com.squareup.picasso.Picasso;

public class ViewFoto extends AppCompatActivity {
    ImageView foto;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_foto);
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
            Picasso.get().load(Retroserver.url_foto_user+data.getStringExtra("foto"))
                    .fit().centerInside().into(foto);



    }
}
