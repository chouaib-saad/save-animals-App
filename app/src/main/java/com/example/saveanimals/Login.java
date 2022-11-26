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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saveanimals.items.Profile;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
Button callSignUp,login_btn,mdpOublier;
ImageView image;
TextView logoText,sloganText;

CheckBox checkbox;
public static RelativeLayout ProgressBar;

//closer Activity
public  static   Login fa;

//declarer d'un entite statique intent profile  pour envoyer les donnees
public static Intent intentP;

// utiliser pour collecte les donnes de la login "connexion"
TextInputLayout username,password; //(remplacer EditText)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fa=this;

        //close main activity
        //MainActivity.ma.finish();
        
        //checkbox hook
        //checkbox = (CheckBox) findViewById(R.id.checkbox);


        //pour cacher la bar statuts de l'ecran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        /* test de connexion dans l'activite signUp */
        if (!isConnected(this)) {
            showCustomDialog();
        }

        //progress bar animation
        ProgressBar = (RelativeLayout) findViewById(R.id.ProgressBar);
        //Create Hooks
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.Login_btn);
        //hook pour la bouton d'inscription
        callSignUp = findViewById(R.id.signup_screen);

        //checkbox hook memoriser
        checkbox = findViewById(R.id.checkbox);

        // Renitialisation de mot de passe hook
        mdpOublier = findViewById(R.id.mdpOublier);



        //fait la translation entre l'activite login et l'activite signup
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar.setVisibility(View.VISIBLE);
                if (isConnected(Login.this)) {
                    Intent intent = new Intent(Login.this, SignUp.class);

                    Pair[] pairs = new Pair[7];
                    pairs[0] = new Pair<View, String>(image, "logo_image");
                    pairs[1] = new Pair<View, String>(logoText, "logo_text");
                    pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                    pairs[3] = new Pair<View, String>(username, "username_tran");
                    pairs[4] = new Pair<View, String>(password, "password_tran");
                    pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                    pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());
                }else {
                    snackBarCnxAlert();
                }
            }
        });

        //HOOKS pour les champs de connexion (login)  : deja declarer ne pas besoin..
        /*regUsername = (TextInputLayout) findViewById(R.id.username);
        regPassword = (TextInputLayout) findViewById(R.id.password);*/


    //simple message de bienvenue
        //Toast.makeText(Login.this, "Les animaux ont besoin de vous !\n", Toast.LENGTH_LONG).show();

        //remember me checked (to be continued..)
        //isChecked();

        //memorise la compte a la prochaine authentification




        //Reinisialisation de mot de passe
        mdpOublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar.setVisibility(View.VISIBLE);
                if (isConnected(Login.this)) {
                    Intent intent = new Intent(Login.this,password_reinitialisation.class);

                    Pair[] pairs = new Pair[5];
                    pairs[0] = new Pair<View, String>(image, "logo_image");
                    pairs[1] = new Pair<View, String>(logoText, "logo_text");
                    pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                    pairs[3] = new Pair<View, String>(password, "password_tran");
                    pairs[4] = new Pair<View, String>(login_btn, "button_tran");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());
                }else {
                    snackBarCnxAlert();
                }

            }
        });


    }   //onCreate Methode end

  //verification de champs de login
  private Boolean validateUsername() {
      String val = username.getEditText().getText().toString();
        /*en peut utiliser "\\A\\w{4,20}\\z" (touts le caract a lexep de spaces)
        au lieu de "(?=\\s+$)"
        pour eliminer les espaces blanc */
      //String noWhiteSpace ="(?=\\s+$)";
      String noWhiteSpace ="\\A\\w{4,20}\\z";
        /*en peut utiliser aussi val.equals("")
        pour verifier que le champ est vide*/
      if (val.isEmpty()) {
          username.requestFocus();
          username.setError(getString(R.string.empty_error));
          return false;
      }else if(!val.matches(noWhiteSpace)){
          username.requestFocus();
          username.setError(getString(R.string.wait_spaces_err));
          return false;
      }
      else {
          username.setError(null);
          username.setErrorEnabled(false);
          return true;
      }

  }
  private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        //"^" : starting of the screen
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
            password.requestFocus();
            password.setError(getString(R.string.empty_error));
            return false;
        }
        /*else if(!val.matches("code here")){
            regPassword.requestFocus();
            regPassword.setError("le mot de passe est trop faible");
            return false;
            }
         */

        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }


  // bouton de login (connexion)
    public void letTheUserLoggedIn(View view) {
        ProgressBar.setVisibility(View.VISIBLE);
        //rappelle a la fonctions de verif de cnx
        if(isConnected(this)){
            /*rappelle de fonctions de verification*/
            //validation des information de connexion (login)
            if(!validateUsername() | !validatePassword()){
                ProgressBar.setVisibility(View.INVISIBLE);
                return;

            //point de rentre au home page**
            }else{
                //tester si l'utilisateur est   existant
                /* point dentre au activite d'accuil */
                isUser();
                //Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_LONG).show();
            }
        }else{
            //afficher la message d'erreur
            snackBarCnxAlert();
        } //end TestConnection function




    } //end of letTheUserLoggedin;


    //fonction pour tester si l'input est un User existant
    private void isUser() {
        ProgressBar.setVisibility(View.VISIBLE);
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        //nous besoin de creer une Firebase referenceb (to verify values with the
        //existing ones in data base)
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        /*this querry look for the username for all the users and match it with username
        entred by the current user*/
        /***idha kan fam user bnafs lesm danc 3ana des valeurs fel db stocker fel datasnapshot()
        w idha datasnapshot mawjouda nekhdhou pwd lmwjouda fel username hadheka w na3mlou 3liha
        test idha ken nafsha li dakhlha el user wala lee***/
        //username should be equal to the userEnteredPassword ?
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername );

        //verifier l'existance de l'utilisateur
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //the data is inside the snapshot
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                if(datasnapshot.exists()){

                    /*si l'utilisateur entrer les  donnes correspandants a
                    la deyxieme fois nous besoin de supprimer lerreur avecs setErrorEnable()*/
                    //to remove the error
                    username.setError(null);
                    //to remove the error space
                    username.setErrorEnabled(false);
                    //to get the data from DB
                    //we want to validate the userEnteredUsername
                    String passwordFromDB = datasnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    //test de comparaison de deux mots de passe
                    if(passwordFromDB.equals(userEnteredPassword)){
                        ProgressBar.setVisibility(View.INVISIBLE);
                        //to remove the error
                        password.setError(null);
                        //to remove the error space
                        password.setErrorEnabled(false);

                        //to get the data from DB el donnes tsajlet fel db mel userHelper class
                        // (fetch all the values inside the String variables ..FromDB)
                        String nameFromDB = datasnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = datasnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = datasnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = datasnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        //nadou l'activite li ba3dha =>menu (profile d'utilisateur)
                        Intent intent = new Intent(Login.this, UserProfile.class);


                        //appeler un intent qui transmisse les donnes
                        intentP = new Intent(Login.this, Profile.class);

                        Pair[] pairs = new Pair[3];
                        pairs[0] = new Pair<View, String>(image, "logo_image");
                        pairs[1] = new Pair<View, String>(logoText, "logo_text");
                        pairs[2] = new Pair<View, String>(callSignUp, "login_signup_tran");


                        /* n3adou les donnes mel bdd lel userProfile bach n7otouhom fel les
                        champs mte3hom */
                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phoneNo",phoneNoFromDB);
                        intent.putExtra("password",passwordFromDB);


                        //sending data to Profile class with a static intent
                        /* n3adou les donnes mel bdd lel userProfile bach n7otouhom fel les
                        champs mte3hom */
                        intentP.putExtra("name",nameFromDB);
                        intentP.putExtra("username",usernameFromDB);
                        intentP.putExtra("email",emailFromDB);
                        intentP.putExtra("phoneNo",phoneNoFromDB);
                        intentP.putExtra("password",passwordFromDB);

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                        startActivity(intent, options.toBundle());


                    }//idha ken mahich nafs el password li dakhalha el user
                    else{
                        //pour pointer sur le champ specifique automatiquement
                        password.requestFocus();
                        password.setError("mot de passe incorrect");
                        ProgressBar.setVisibility(View.INVISIBLE);
                    }
                }//idha ken el snapshot mafihech hata donnes danc famech hata user bel esm heka
                else{
                username.requestFocus();
                username.setError("L'utilisateur ne pas existe");
                ProgressBar.setVisibility(View.INVISIBLE);
                    }
            } //fin de la methode dataChange

            @Override
            //inside this we have the error methode
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    verifier
    la connexion
    internet
     */
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }
        else{
            return false;
        }
    }

// fct de message si la cnx ne pas etablir :
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this,R.style.AlertDialog);
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
            //startActivity(new Intent(getApplicationContext(),Login.class));
                dialogInterface.dismiss();
                ProgressBar.setVisibility(View.INVISIBLE);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //fermer l'application
    public void exitApp(View view){
        //initialiser une dialogue de anvertissement
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Login.this,R.style.AlertDialog);
        //defenir la titre de l'alert :
        builder.setTitle(R.string.exit_title);
        builder.setMessage(R.string.exit_app);
        //positionnez la bouton oui
        builder.setPositiveButton(R.string.exit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //progress Bar
                /*ProgressBar.setVisibility(View.VISIBLE);*/

                //ditruire l'activiter Main
                finishAffinity();

                /*pas important*/
                //quitter l'application
                //System.exit(0);

            }
        });
        //bouton non
        builder.setNegativeButton(R.string.exit_neg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
                dialogInterface.dismiss();
            }
        });
        //afficher le dialog
        builder.show();

    } //methode exit app closer

    @Override
    public void onBackPressed() {
        MainActivity.ma.finish();
        //super.onBackPressed();
        //initialiser une dialogue de anvertissement
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Login.this,R.style.AlertDialog);
        //defenir la titre de l'alert :
        builder.setTitle(R.string.exit_title);
        builder.setMessage(R.string.exit_app);

        //positionnez la bouton oui
        builder.setPositiveButton(R.string.exit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //progress Bar
                /*ProgressBar.setVisibility(View.VISIBLE);*/

                //ditruire l'activiter Main
                finishAffinity();

                /*pas important*/
                //quitter l'application
                //System.exit(0);

            }
        });
        //bouton non
        builder.setNegativeButton(R.string.exit_neg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
                dialogInterface.dismiss();
            }
        });
        //afficher le dialog
        builder.show();
    }

    private void snackBarCnxAlert(){
            ProgressBar.setVisibility(View.VISIBLE);
        final Snackbar snackbar = Snackbar.make(ProgressBar, R.string.veuille_reessayer, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.reessayer, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(Login.this)){
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
        }, 1500);   //1.5 seconds
        //}, 2750);   //2.7 seconds
    }

    //checkbox verification
    public void memorise(View view) {

        if(checkbox.isChecked()){
            Toast.makeText(this, "Votre compte sera Memorisé", Toast.LENGTH_SHORT).show();
        }else if(!checkbox.isChecked()){
            Toast.makeText(this, "Memorisation annulée", Toast.LENGTH_SHORT).show();
        }
    }

    //check box function
    /*private void isChecked(){
        if(checkbox.isChecked()){
            Toast.makeText(this, R.string.memoriser, Toast.LENGTH_SHORT).show();
        }
    }*/


} //appCompatActivity Methode end