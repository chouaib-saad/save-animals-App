package com.example.saveanimals.items;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.R;
import com.example.saveanimals.UserProfile;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Veterenaires extends AppCompatActivity implements OnMapReadyCallback {

    Button btTrouve;
    Spinner spType;
    GoogleMap mGoogleMap;

    boolean isPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterenaires);
        //pour cacher la bar statuts de l'ecran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //verifier les permissions
        checkMyPermission();

        //location activation
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //verifier l'activation de gps
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showCustomDialog();
        }


        if(isPermissionGranted){
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            supportMapFragment.getMapAsync(this);
        }

        //hooks for the elements
        btTrouve = findViewById(R.id.bt_trouve);
        spType = findViewById(R.id.sp_type);

        //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            //array of place type
            //String[] placeTypeList = {"\"G26R+QP Mahdia\""};

            //array of place name
            String[] placeNameList = {"veterenaires","cabines","Clinique vétérinaire"};
            //set adapter on spinner
            spType.setAdapter(new ArrayAdapter<>(Veterenaires.this, android.R.layout.simple_spinner_dropdown_item, placeNameList));

        btTrouve.setOnClickListener(this::geoLocate);

    } //onCreate methode end






    private void geoLocate(View view) {
        Spinner spinner = (Spinner)findViewById(R.id.sp_type);
        String location = spinner.getSelectedItem().toString();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String locationName= null;
        switch (location){
            case "veterenaires":
                locationName = "G26R+QP Mahdia";
                break;
            case "cabines":
                locationName = "JVWR+5W Ksar Hellal";
                break;
            case "Clinique vétérinaire":
                locationName = "G25W+5R Mahdia";
                break;
        }
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName,1);
            if(addressList.size()>0){
                Address address = addressList.get(0);
                gotoLocation(address.getLatitude(),address.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
                Toast.makeText(this, address.getLocality(),Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void gotoLocation(double latitude, double longitude) {
        LatLng LatLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng,18);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                Toast.makeText(Veterenaires.this,R.string.gps_permission, Toast.LENGTH_SHORT).show();

                isPermissionGranted = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
            permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    @Override
    public void onBackPressed() {
        retourAlert();
    }


    private void retourAlert(){
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(Veterenaires.this,R.style.AlertDialog);
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
                Veterenaires.super.onBackPressed();
                Animatoo.animateFade(Veterenaires.this);
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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setPadding(5,2000,0,5);
        //location activation
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //verifier l'activation de gps
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showCustomDialog();
        }
        //mGoogleMap.addMarker(new MarkerOptions());


    }


    //gps activation dialog
    private void showCustomDialog(){
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(Veterenaires.this,R.style.AlertDialog);
        //defenir la titre de l'alert :
        builder.setTitle("ACTIVER VOTRE GPS");
        builder.setMessage("le service gps ne pas activée !");
        //positionnez la bouton oui
        builder.setPositiveButton("Active",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //ouvrir les parametres wifi :
                Intent a = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(a,1);
            }
        });
        //bouton non
        builder.setNegativeButton("Annulée",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cacher le dialog
                //dialogInterface.dismiss();
                builder.setCancelable(false);


                ///location set
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    showCustomDialog();
                }
            }
        });
        //afficher le dialog
        builder.show();
    }

} //AppCompatActivity class end
