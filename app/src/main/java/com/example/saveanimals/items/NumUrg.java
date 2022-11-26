package com.example.saveanimals.items;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.R;
import com.example.saveanimals.UserProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NumUrg extends AppCompatActivity {
FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_urg);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        floatingActionButton = findViewById(R.id.floating_butt);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passer pour faire un appelle :
                call("");
            }
        });
    }// on reate meth end









    private void call(String s) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+s));
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        UserProfile.navigationView.setCheckedItem(R.id.nav_home);
        NumUrg.super.onBackPressed();
        Animatoo.animateFade(NumUrg.this);
    }
} //class end