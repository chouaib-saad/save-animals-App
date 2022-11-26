package com.example.saveanimals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class password_reinitialisation extends AppCompatActivity {

    TextInputLayout regEmail;
    TextInputEditText regEmail2;
    RelativeLayout progressBar;
    static int tentative=0;

    //translation declarations :
    Button login_btn;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout password; //(remplacer EditText)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reinitialisation);

        //make full screen and remove taskbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //translation hook
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.Login_btn);

        //email champ hook
        regEmail = findViewById(R.id.email);
        regEmail2 = findViewById(R.id.regEmail2);

        //progress bar hook
        progressBar = findViewById(R.id.ProgressBar);


    } //onCreate methode end

    public void returnToLogin(View view) {
        onBackPressed();
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        /*regic expression: pour Respecter la forme de @ email*/
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            regEmail.requestFocus();
            regEmail.setError(getString(R.string.empty_error));
            return false;
        } else if(!val.matches(emailPattern)){
            regEmail.requestFocus();
            regEmail.setError(getString(R.string.email_forme_err));
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    public void recuperer(View view) {
        progressBar.setVisibility(View.VISIBLE);

        //test de la cnx
        if (isConnected(this)) {

        if (!validateEmail()) {
            progressBar.setVisibility(View.INVISIBLE);
            String val = regEmail.getEditText().getText().toString();
            if (!val.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.email_enregistration, Toast.LENGTH_SHORT).show();
            }
            return;
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            if(tentative==0){
            Toast.makeText(getApplicationContext(), "lien de reinisialisation envoyer", Toast.LENGTH_LONG).show();
            tentative++;}
            else if(tentative>=1&&tentative<=2){
                Toast.makeText(getApplicationContext(), "Consulter votre Email..", Toast.LENGTH_LONG).show();
                tentative++;
            }
            else{
                regEmail2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tentative=0;
                    }
                });
            }
            //Intent intent = new Intent(getApplicationContext(), Login.class);
            //startActivity(intent);
        }
    }
        else{
            snackBarCnxAlert();
        }
    }


    /*
    verifier
    la connexion
    internet
     */
    private boolean isConnected(password_reinitialisation password_reinitialisation) {
        ConnectivityManager connectivityManager = (ConnectivityManager) password_reinitialisation.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }



    //snackbar function

    private void snackBarCnxAlert(){
        progressBar.setVisibility(View.VISIBLE);
        final Snackbar snackbar = Snackbar.make(progressBar, R.string.veuille_reessayer, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.reessayer, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(password_reinitialisation.this)){
                    snackBarCnxAlert();
                }
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setTextColor(getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorWrong));
        TextView textView = (TextView)sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_error,0,0,0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
        //textView.setTextAlignment(View.);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        snackbar.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 1500);   //2.75 seconds

    }


    @Override
    public void onBackPressed() {
        Login.ProgressBar.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

}