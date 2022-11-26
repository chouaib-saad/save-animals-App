package com.example.saveanimals;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    RelativeLayout ProgressBar;
    ImageView logoImage;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    ImageView login_back;

    // utiliser pour collecte les donnes et la validation form
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button se_connecter /*(regToLoginBtn)*/,sign_up_btn /*regBtn*/;
    //declaration des attributs de Firebase :
    FirebaseDatabase rootNode; //el table lkbir
    DatabaseReference reference; //subElements of rootNode (les colonnes li fih)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //suprimer l'action_bar :
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* test de connexion dans l'activite signUp */
        if (!isConnected(this)) {
            showCustomDialog();
        }

        //arrete la bar progressive dans la fct precedent ;
        Login.ProgressBar.setVisibility(View.INVISIBLE);


        //des hooks pour la rappelle de fct translation animation
        logoImage = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        sign_up_btn = findViewById(R.id.signup_btn);
        se_connecter = findViewById(R.id.se_connecter);

        //progress bar animation hook..
        ProgressBar = (RelativeLayout) findViewById(R.id.ProgressBar);
        //image login_back hook
        login_back = (ImageView) findViewById(R.id.login_back);

        //HOOKS pour les champs de forme de validations :
        //hooks to all xml elements in activity_sign_up.xml
        regName = (TextInputLayout) findViewById(R.id.name);
        regUsername = (TextInputLayout) findViewById(R.id.username);
        regEmail = (TextInputLayout) findViewById(R.id.email);
        regPhoneNo = (TextInputLayout) findViewById(R.id.phoneNo);
        regPassword = (TextInputLayout) findViewById(R.id.password);


        //fait la translation entre l'activite login et l'activite signup
        /*bouton pour retourne a la login*/
        se_connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar.setVisibility(View.VISIBLE);
                if(isConnected(SignUp.this)){
                Intent intent = new Intent(SignUp.this, Login.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(logoImage, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(sign_up_btn, "button_tran");
                pairs[6] = new Pair<View, String>(se_connecter, "login_signup_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                //Login.fa.finish();
                startActivity(intent, options.toBundle());
                finish();
            }
                else{
                    snackBarCnxAlert();
                }
            }
        });

        //back function
        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SignUp.this,Login.class));
                //finish();
                onBackPressed();
                //retirer la bar progressive
                /*Login.ProgressBar.setVisibility(View.INVISIBLE);*/

            }
        }); //back butt end


    }   //onCreate Methode end

    /* fonctions de type booleen (true/false) pour valide les champs avant lenvoyer et stocker
        dans la bdd */
    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            regName.requestFocus();
            regName.setError(getString(R.string.empty_error));
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
        //String noWhiteSpace ="(?=\\s+$)";
        String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            regUsername.requestFocus();
            regUsername.setError(getString(R.string.empty_error));
            return false;
        } else if(val.length()>=15){
            regUsername.requestFocus();
            regUsername.setError(getString(R.string.length_err));
            return false;
        }else if(!val.matches(noWhiteSpace)){
            regUsername.requestFocus();
            regUsername.setError(getString(R.string.wait_spaces_err));
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

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
    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            regPhoneNo.requestFocus();
            regPhoneNo.setError(getString(R.string.empty_error));
            return false;
        } else if(val.length()!=8){
            regPhoneNo.requestFocus();
            regPhoneNo.setError(getString(R.string.phone_numb_err));
            return false;
        }
        else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        //"^" : starting of the screen "$" : end of the screen
        //il faut que le mot de passe contient le suivant:

        /* a verifier
        String passwordVal ="^"+
                //"(?=.*[0-9])" +             //at least 1 digit
                //"(?=.*[a-z])" +             //at least 1 lower case letter
                //"(?=.*[A-Z])" +             //at least 1 upper case letter
                "(?=.*[a-zA-Z])"+           //any letter
                "(?=.*[@#$%^&+=])"+         //at least 1 special caracter
                //"(?=\\s+$)" +             //no white spaces 1
                "\\A\\w{4,20}\\z" +         //no white spaces 2
                ".{4,}" +                   //at least 4 characters
                "$";
        */
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
        if (val.isEmpty()) {
            regPassword.requestFocus();
            regPassword.setError(getString(R.string.empty_error));
            return false;
        }else if(val.length()<=6){
            regPassword.requestFocus();
            regPassword.setError(getString(R.string.short_pswd_err));
            return false;
        }
        else if(val.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")){
            regPassword.requestFocus();
            regPassword.setError(getString(R.string.useless_pswd_err));
            return false;
            }


        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

    //bouton de sign_up
    /*inscription*/
    public void letTheUserSignedUp(View view) {

        ProgressBar.setVisibility(View.VISIBLE);

        //test de la cnx
        if (isConnected(this)) {

            /*rappelle de fonctions de verification
            single or '|' not '||' => valider tous les fcts
            la compulateur passer a lexecution de autres instrc si tous le fct bool  return true*/
            if(!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword()){
                ProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),R.string.verifier, Toast.LENGTH_SHORT).show();
                return;

            /* point d'entre au activite "inscription verfier*/
            }else{
                    ProgressBar.setVisibility(View.VISIBLE);

                    /*a completer soon ... */
                    /* point d'entre au activite "inscription verfier*/

                //tous les donnes sera stocker sur cette instance
                //include all the elements inside our project
                rootNode =FirebaseDatabase.getInstance();
                /*to acces all the fields we need to defined a reference*/
                //fel methode "getReference()" nektbou anahi el reference li hajtna biha
                /*users par exemple li fiha many users(1,2,3,4..) fihom values(name,pass,email..) */
                reference =rootNode.getReference("users");

                //recevoir de touts les valeurs de l'inscription de user en "String"
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                //avant la reference nous besoin d'appele le nouveau classe "UserHelperClass"
                UserHelperClass helperClass = new UserHelperClass(name,username,email,phoneNo,password);

                //nesta3mlou lmethode  setValue bch n3amrou les donnes mte3na fel database
                /*bach na3mlou 7aja unique lkol user nesta3mlou child() li n7otou fiha lclee unique
                dans notre cas le cles unique est la numero de telephone*/
                /* !!!lcle unique howa el username fam mochkla sghira a verifier..!!! */
                reference.child(username).setValue(helperClass);

                /*=> il peut ajouter plusieurs utilisateurs mais pour la meme clee en peut
                modifier les donners !!*/

                //nous besoin  d'un nauveau classe java pour ajouter une inscription!
                /*(je choisir un classe nommer "User helper class")...*/


                /* completer */
                /* point d'entre au activite "inscription verfier*/
                //afficher l'activite de validation
                Intent intent = new Intent(getApplicationContext(),InscValider.class);
                //Toast.makeText(getApplicationContext(), "inscription valider", Toast.LENGTH_LONG).show();
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Animatoo.animateZoom(this);
                finish();


            }
        }else{
            snackBarCnxAlert();
        }

    } //fin de la methode letTheUserSignUp

    /*
    verifier
    la connexion
    internet
     */
    private boolean isConnected(SignUp signUp) {
        ConnectivityManager connectivityManager = (ConnectivityManager) signUp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    //fct afficher un message d'erreur si la cnx ne pas etablir :
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this,R.style.AlertDialog);
        builder.setTitle(R.string.cnc_echec);
        builder.setMessage(R.string.concte_a_internet);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.connecte, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ouvrir les parametres wifi :
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                //ouvrir le parametres de donnes mobile :
                //startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                //retirer la bar progressive de l'activite mere..
                ProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        builder.setNegativeButton(R.string.exit_neg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //startActivity(new Intent(getApplicationContext(),SignUp.class));
                ProgressBar.setVisibility(View.INVISIBLE);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void snackBarCnxAlert(){
        ProgressBar.setVisibility(View.VISIBLE);
        final Snackbar snackbar = Snackbar.make(ProgressBar, R.string.veuille_reessayer, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.reessayer, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(SignUp.this)){
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
                ProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 1500);   //2.75 seconds

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}   //appCompatActivity Methode end