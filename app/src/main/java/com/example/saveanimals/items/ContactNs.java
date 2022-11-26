package com.example.saveanimals.items;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.R;
import com.example.saveanimals.UserProfile;

public class ContactNs extends AppCompatActivity {
private WebView webView;
ProgressDialog progressDialog;
ProgressBar progressBar;
String loadUrl="https://www.linkedin.com/in/chouaib-saad-bb4106219/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_ns);

        webView = (WebView) findViewById(R.id.wv);
        progressBar = (ProgressBar) findViewById(R.id.pbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.chargement_en_cour));
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(loadUrl);
        progressBar.setProgress(0);
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                /*changer la progression de la progress
                 chaque fois la progg de chrome changee*/
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                setTitle(getString(R.string.chargement));
                progressDialog.show();
                if(newProgress==100) {
                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                    progressDialog.dismiss();

                }


                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            loadUrl="https://www.linkedin.com/in/chouaib-saad-bb4106219/";
            retourAlert();
        }
    }


    private void retourAlert(){
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactNs.this,R.style.AlertDialog);
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
                ContactNs.super.onBackPressed();
                Animatoo.animateFade(ContactNs.this);
                UserProfile.navigationView.setCheckedItem(R.id.nav_home);
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