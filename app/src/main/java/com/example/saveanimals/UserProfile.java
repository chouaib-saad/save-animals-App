package com.example.saveanimals;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.items.Conseils;
import com.example.saveanimals.items.ContactNs;
import com.example.saveanimals.items.Events;
import com.example.saveanimals.items.NumUrg;
import com.example.saveanimals.items.OrgsSocial;
import com.example.saveanimals.items.Profile;
import com.example.saveanimals.items.Veterenaires;
import com.google.android.material.navigation.NavigationView;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//fetch data variables
TextView nom_dutilisateur;
//TextView userName2,mail2;

    //Variables nav drawer
    DrawerLayout drawerLayout;
    public static NavigationView navigationView;
    Toolbar toolbar;

    //progress bar
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setting the progress bar (hook)
        /*progressBar = findViewById(R.id.ProgressBar2);*/

        //pour cacher la bar statuts de l'ecran
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //userNameHook and navBar inf hooks
        nom_dutilisateur = findViewById(R.id.nom_dutilisateur);
        //mail and userN header
        /*
        userName2 =findViewById(R.id.userName2);
        mail2 = findViewById(R.id.mail2);
        */


        //ShowDataFunc (user name)
        showUserName();
        //Show nav bar inforations func
        /*showNavbarInf();*/

        /*----------------------Hooks------------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        /*---------------------Tool Bar----------------------------*/
        setSupportActionBar(toolbar);
        /*----------------Navigation Drawer Menu-------------------*/
        //drawerLayout.closeDrawer(GravityCompat.START);

        //hold clicked items suite
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //hold clicked items
        navigationView.setNavigationItemSelectedListener(this);

        //l'acceuil est litem selectioner par defaut
        navigationView.setCheckedItem(R.id.nav_home);

        //cacher ou afficher les ithmes ::
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        //etc..
        //menu.findItem(R.id.nav_profile).setVisible(false);

    }  //OnCreate methode end




    private void showUserName() {
        Intent intent =getIntent();
        String userName = intent.getStringExtra("name");
        nom_dutilisateur.setText(userName); }

    /*private void showNavbarInf() {
        Intent intent =getIntent();
        String userName = intent.getStringExtra("name");
        String mail = intent.getStringExtra("email");
        userName2.setText(userName);
        mail2.setText(mail);} */


    //close nadDraw au lieu de quitter si le navDraw est ouvrir et retour button clicked
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            appCloseMessage();
        }
    }

    //hold and select clicked items..
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.veterenaires:
                Intent intent1 = new Intent(UserProfile.this, Veterenaires.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                Animatoo.animateFade(this);
                break;
            case R.id.conseils:
                Intent intent2 = new Intent(UserProfile.this, Conseils.class);
                startActivity(intent2);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Animatoo.animateFade(this);
                break;
            case R.id.organismes:
                Intent intent5 = new Intent(UserProfile.this,OrgsSocial.class);
                startActivity(intent5);
                Animatoo.animateFade(this);
                break;
            case R.id.camp_daide:
                Intent intent6 = new Intent(UserProfile.this,Events.class);
                startActivity(intent6);
                Animatoo.animateFade(this);
                break;
            case R.id.numeros_urg:
                Intent intent7 = new Intent(UserProfile.this, NumUrg.class);
                startActivity(intent7);
                Animatoo.animateFade(this);
                break;
            /*
            case R.id.nav_login:
                Intent intent8 = new Intent(UserProfile.this, );
                startActivity(intent8);
                Animatoo.animateFade(this);
                break; */

            case R.id.nav_profile:
                Intent intent3 = new Intent(UserProfile.this, Profile.class);
                startActivity(intent3);
                Animatoo.animateFade(this);
                break;
            case R.id.nav_logout:
                deconnectMsg();
                break;
            case R.id.contactez_nous:
                //Toast.makeText(getApplicationContext(), "Bientôt disponible..", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(UserProfile.this, ContactNs.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                Animatoo.animateFade(this);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void deconnectMsg() {
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this,R.style.AlertDialog);
        //defenir la titre de l'alert :
        builder.setTitle(R.string.deconnexion);
        builder.setMessage(R.string.dec_msg);
        //positionnez la bouton oui
        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
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
                UserProfile.super.onBackPressed();
            }
        });
        //bouton non
        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
                dialogInterface.dismiss();
            }
        });
        //afficher le dialog
        builder.show();
    }

    private void appCloseMessage(){
        //Login.fa.finish();
        //MainActivity.ma.finish();
        //initialiser une dialogue de anvertissement
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(UserProfile.this,R.style.AlertDialog);
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

} //app compat func end