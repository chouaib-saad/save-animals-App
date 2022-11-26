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

public class OrgsSocial extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgs_social);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //floating butt hook
        floatingActionButton = findViewById(R.id.floating_butt);

        //floating butt cklicked
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitUrl("https://www.facebook.com/groups/882571718456084");
            }
        });





    } //onCreate meth end

    private void visitUrl(String string) {
        Uri uri = Uri.parse(string);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

        //***autre methode***
        /*
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(string));
        startActivity(intent);  */
    }


    @Override
    public void onBackPressed() {
        UserProfile.navigationView.setCheckedItem(R.id.nav_home);
        OrgsSocial.super.onBackPressed();
        Animatoo.animateFade(OrgsSocial.this);
    }



} //class end


