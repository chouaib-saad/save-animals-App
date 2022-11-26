package com.example.saveanimals.items;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.R;
import com.example.saveanimals.UserProfile;
import com.example.saveanimals.items.ConseilsItems.AvantagesFragment;
import com.example.saveanimals.items.ConseilsItems.GestesFragment;
import com.example.saveanimals.items.ConseilsItems.ProtectionFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Conseils extends AppCompatActivity {
ChipNavigationBar chipNavigationBar;
ImageView back;
TextView detailsText;
LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conseils);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //back button hook
        back = findViewById(R.id.back);
    //nav hook
    chipNavigationBar = findViewById(R.id.bottom_nav_menu);
    //ensure that the first button   is selected hightlighted..

        //ensure that the first one opened is selected
    chipNavigationBar.setItemSelected(R.id.protection,true);

    chipNavigationBar.showBadge(R.id.protection);
    chipNavigationBar.showBadge(R.id.gestes,5);
    chipNavigationBar.showBadge(R.id.avantages,99);

        //...
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProtectionFragment()).commit();
        bottomMenu();



        //back function
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SignUp.this,Login.class));
                //finish();
                Conseils.super.onBackPressed();
                //retirer la bar progressive
                /*Login.ProgressBar.setVisibility(View.INVISIBLE);*/

            }
        }); //back butt end

    }// on create methode end



    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.protection:
                        fragment = new ProtectionFragment();
                        break;
                    case R.id.gestes:
                        fragment = new GestesFragment();
                        break;
                    case R.id.avantages:
                        fragment = new AvantagesFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        retourAlert();
    }

    private void retourAlert(){
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(Conseils.this,R.style.AlertDialog);
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
                Conseils.super.onBackPressed();
                Animatoo.animateFade(Conseils.this);
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


} // class conseils end