package com.example.saveanimals.items;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.Login;
import com.example.saveanimals.R;
import com.example.saveanimals.UserProfile;
import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {
    ImageView back;
    TextInputLayout fullName,email,phoneNo,password;
    TextView fullNameLabel,usernameLabel;
    Button shere_butt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    //back hook
        back = findViewById(R.id.back);

   //hooks for all the variables:
        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNo);
        password = findViewById(R.id.password);
        fullNameLabel = findViewById(R.id.full_name);
        usernameLabel = findViewById(R.id.username);

        //shere buton hook
        shere_butt = findViewById(R.id.shere_butt);

        //show all data func
        showAllUserData();

        //shere function
        /*we gonna change this shere button with update button soon...*/
        shere_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //utilise le methode pour formaliser votre text
                //Html.fromHtml();
                String appName="<i>'Help Animals'</i>";
                String user_name = Login.intentP.getStringExtra("name");
                String nom = "<i>"+user_name+"</i>";
                shereText("Salut , j'utilise maintenant une application qui s'appelle "+ Html.fromHtml(appName)+" sous le nom '"+Html.fromHtml(nom)+"', Rejoignez-nous !\n" +
                        "Il y a beaucoup d'animaux Besoin d'aide !");
            }
        });


        //back function
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SignUp.this,Login.class));
                //finish();
                UserProfile.navigationView.setCheckedItem(R.id.nav_home);
                Profile.super.onBackPressed();
                Animatoo.animateFade(Profile.this);
                //retirer la bar progressive
                /*Login.ProgressBar.setVisibility(View.INVISIBLE);*/

            }
        }); //back butt end




    } //onCreate end methode

    private void shereText(String s) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,s);
        intent.setType("text/plain");
        if(intent.resolveActivity(getPackageManager()) != null){
            Toast.makeText(getApplicationContext(), R.string.partage, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), R.string.partage_error_text, Toast.LENGTH_SHORT).show();
        }
    }

    //un methode generer avec moi
    private void showAllUserData() {
        String user_username = Login.intentP.getStringExtra("username");
        String user_name = Login.intentP.getStringExtra("name");
        String user_email = Login.intentP.getStringExtra("email");
        String user_phoneNo = Login.intentP.getStringExtra("phoneNo");
        String user_password = Login.intentP.getStringExtra("password");

        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);

    }


    @Override
    public void onBackPressed() {
        retourAlert();
    }

    private void retourAlert(){
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this,R.style.AlertDialog);
        //defenir la titre de l'alert :
        builder.setTitle(R.string.retour_title);
        builder.setMessage(R.string.retour_msg);
        //positionnez la bouton oui
        builder.setPositiveButton(R.string.oui,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //attendre lactiviter et retourne a l'activiter main
                /*finishAffinity();*/

                //quitter l'application
                /*System.exit(0);*/

                //supprimer juste cette activiter
                //Login.fa.finish();
                //startActivity(new Intent(getApplicationContext(),Login.class));
                //Toast.makeText(getApplicationContext(), "deconnexion..", Toast.LENGTH_LONG).show();
                //finish();
                UserProfile.navigationView.setCheckedItem(R.id.nav_home);
                Profile.super.onBackPressed();
                Animatoo.animateFade(Profile.this);
            }
        });
        //bouton non
        builder.setNegativeButton(R.string.non,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
                dialogInterface.dismiss();
            }
        });
        //afficher le dialog
        builder.show();
    }

}