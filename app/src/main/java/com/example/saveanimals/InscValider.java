package com.example.saveanimals;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InscValider extends AppCompatActivity {
    TextView logoText;
    ImageView logoImage;
    Button returnToLogin;
    RelativeLayout ProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //pour cacher la bar statuts de l'ecran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insc_valider);

        //bar progressive
        ProgressBar = (RelativeLayout) findViewById(R.id.ProgressBar);

        //des hooks pour la rappelle de fct translation animation
        logoImage = findViewById(R.id.checkMark);
        logoText = findViewById(R.id.successTxt);
        returnToLogin = findViewById(R.id.returnToLogin);






    }
    /*bouton pour retourne a la login*/
    public void returnToLogin(View view){
        ProgressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getApplicationContext(),Login.class);

        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(logoImage, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_text");
        pairs[2] = new Pair<View, String>(returnToLogin, "button_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InscValider.this, pairs);
        startActivity(intent,options.toBundle());
        finish();
    }
}