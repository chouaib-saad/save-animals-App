package com.example.saveanimals.items;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.saveanimals.R;
import com.example.saveanimals.UserProfile;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Events extends AppCompatActivity {
CompactCalendarView compactCalendarView;
Button rappelle_butt;
private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //***                   a verifier                ***
        final FirebaseDatabase[] rootNode = new FirebaseDatabase[1]; //el table lkbir
        final DatabaseReference[] reference = new DatabaseReference[1]; //subElements of rootNode (les colonnes li fih)
        rootNode[0] = FirebaseDatabase.getInstance();
        reference[0] = rootNode[0].getReference("events");

        //rappelle_butt hook
        rappelle_butt = findViewById(R.id.rappelle_butt);
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), UserProfile.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Nous vous informerons lorsque de nouveaux événements arriveront !");
        bigText.setBigContentTitle("Évènements à venir");
        bigText.setSummaryText("Événements");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Nouvel évènement");
        mBuilder.setContentText("Voir..");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());


    final ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
    actionBar.setTitle(null);


    compactCalendarView = findViewById(R.id.compactcalendar_view);
    compactCalendarView.setAnimation(compactCalendarView.getAnimation());
    compactCalendarView.setLocale(TimeZone.getTimeZone("FRANCE"),Locale.FRENCH);
    compactCalendarView.setUseThreeLetterAbbreviation(true);

    //set title of action bar
        Date date = new Date();
        actionBar.setTitle(dateFormatMonth.format(date));


        //set an event for Journée mondiale des animaux 2022 Day (exemple)

         //add upcomming events
        Event ev1 = new Event(Color.BLUE,1664842003000L,"Journée mondiale des animaux 2022");
         compactCalendarView.addEvent(ev1);

        Event ev2 = new Event(Color.GREEN,1650240403000L,"Better Trade for Safer Food");
        compactCalendarView.addEvent(ev2);
        Event ev3 = new Event(Color.YELLOW,1651104403000L,"Le jour de l'adoption des animaux à Mahdia");
        compactCalendarView.addEvent(ev3);


         compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
             @Override
             public void onDayClick(Date dateClicked) {
                 Context context = getApplicationContext();
                 if(dateClicked.toString().compareTo("Tue Oct 04 01:00:00 GMT+01:00 2022")==0){
                     Toast.makeText(getApplicationContext(), "Journée mondiale des animaux 2022", Toast.LENGTH_SHORT).show();
                 }
                 else if(dateClicked.toString().compareTo("Mon Apr 18 01:00:00 GMT+01:00 2022")==0){
                     Toast.makeText(getApplicationContext(), "Better Trade for Safer Food", Toast.LENGTH_SHORT).show();}
                 else if(dateClicked.toString().compareTo("Thu Apr 28 01:00:00 GMT+01:00 2022")==0){
                     Toast.makeText(getApplicationContext(), "Le jour de l'adoption des animaux à Mahdia", Toast.LENGTH_SHORT).show();}
                 else {
                     Toast.makeText(getApplicationContext(), "aucun événement prévu ce jour-là", Toast.LENGTH_SHORT).show();
                     //Toast.makeText(getApplicationContext(), dateClicked.toString(), Toast.LENGTH_LONG).show();
                 }

                 //butt fonction here:
                 rappelle_butt.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Context context = getApplicationContext();
                         if(dateClicked.toString().compareTo("Tue Oct 04 01:00:00 GMT+01:00 2022")==0){
                             reference[0].child(String.valueOf(dateClicked)).setValue("1664842003000L");

                             //data base call
                             //rootNode[0] = FirebaseDatabase.getInstance();
                             //reference[0] = rootNode[0].getReference("events");


                             NotificationManager mNotificationManager;

                             NotificationCompat.Builder mBuilder =
                                     new NotificationCompat.Builder(Events.this, "notify_001");
                             Intent ii = new Intent(Events.this, UserProfile.class);
                             PendingIntent pendingIntent = PendingIntent.getActivity(Events.this, 0, ii, 0);

                             NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                             bigText.bigText("Vous serez rappelé de l'événement quand il arrivera!");
                             bigText.setBigContentTitle("Rappeler");
                             bigText.setSummaryText("Événements");

                             mBuilder.setContentIntent(pendingIntent);
                             mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                             mBuilder.setContentTitle("Nouvel évènement");
                             mBuilder.setContentText("Voir..");
                             mBuilder.setPriority(Notification.PRIORITY_MAX);
                             mBuilder.setStyle(bigText);

                             mNotificationManager =
                                     (NotificationManager) Events.this.getSystemService(Context.NOTIFICATION_SERVICE);

                             // === Removed some obsoletes
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                             {
                                 String channelId = "Your_channel_id";
                                 NotificationChannel channel = new NotificationChannel(
                                         channelId,
                                         "Channel human readable title",
                                         NotificationManager.IMPORTANCE_HIGH);
                                 mNotificationManager.createNotificationChannel(channel);
                                 mBuilder.setChannelId(channelId);
                             }

                             mNotificationManager.notify(0, mBuilder.build());

                         }
                         else if(dateClicked.toString().compareTo("Mon Apr 18 01:00:00 GMT+01:00 2022")==0){
                             reference[0].child(String.valueOf(dateClicked)).setValue("1650240403000L");
                             NotificationManager mNotificationManager;

                             NotificationCompat.Builder mBuilder =
                                     new NotificationCompat.Builder(Events.this, "notify_001");
                             Intent ii = new Intent(Events.this, UserProfile.class);
                             PendingIntent pendingIntent = PendingIntent.getActivity(Events.this, 0, ii, 0);

                             NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                             bigText.bigText("Vous serez rappelé de l'événement quand il arrivera!");
                             bigText.setBigContentTitle("Rappeler");
                             bigText.setSummaryText("Événements");

                             mBuilder.setContentIntent(pendingIntent);
                             mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                             mBuilder.setContentTitle("Nouvel évènement");
                             mBuilder.setContentText("Voir..");
                             mBuilder.setPriority(Notification.PRIORITY_MAX);
                             mBuilder.setStyle(bigText);

                             mNotificationManager =
                                     (NotificationManager) Events.this.getSystemService(Context.NOTIFICATION_SERVICE);

                             // === Removed some obsoletes
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                             {
                                 String channelId = "Your_channel_id";
                                 NotificationChannel channel = new NotificationChannel(
                                         channelId,
                                         "Channel human readable title",
                                         NotificationManager.IMPORTANCE_HIGH);
                                 mNotificationManager.createNotificationChannel(channel);
                                 mBuilder.setChannelId(channelId);
                             }

                             mNotificationManager.notify(0, mBuilder.build());

                         }
                         else if(dateClicked.toString().compareTo("Thu Apr 28 01:00:00 GMT+01:00 2022")==0){
                             reference[0].child(String.valueOf(dateClicked)).setValue("1651104403000L");
                             NotificationManager mNotificationManager;

                             NotificationCompat.Builder mBuilder =
                                     new NotificationCompat.Builder(Events.this, "notify_001");
                             Intent ii = new Intent(Events.this, UserProfile.class);
                             PendingIntent pendingIntent = PendingIntent.getActivity(Events.this, 0, ii, 0);

                             NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                             bigText.bigText("Vous serez rappelé de l'événement quand il arrivera!");
                             bigText.setBigContentTitle("Rappeler");
                             bigText.setSummaryText("Événements");

                             mBuilder.setContentIntent(pendingIntent);
                             mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                             mBuilder.setContentTitle("Nouvel évènement");
                             mBuilder.setContentText("Voir..");
                             mBuilder.setPriority(Notification.PRIORITY_MAX);
                             mBuilder.setStyle(bigText);

                             mNotificationManager =
                                     (NotificationManager) Events.this.getSystemService(Context.NOTIFICATION_SERVICE);

                             // === Removed some obsoletes
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                             {
                                 String channelId = "Your_channel_id";
                                 NotificationChannel channel = new NotificationChannel(
                                         channelId,
                                         "Channel human readable title",
                                         NotificationManager.IMPORTANCE_HIGH);
                                 mNotificationManager.createNotificationChannel(channel);
                                 mBuilder.setChannelId(channelId);
                             }

                             mNotificationManager.notify(0, mBuilder.build());

                         }
                         else {
                             Toast.makeText(getApplicationContext(), "aucun événement dans ce jour !", Toast.LENGTH_SHORT).show();
                             //Toast.makeText(getApplicationContext(), dateClicked.toString(), Toast.LENGTH_LONG).show();
                         }
                     }
                 });


             }

             @Override
             public void onMonthScroll(Date firstDayOfNewMonth) {
                 actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));

             }
         });


    }// onCreate meth end













    @Override
    public void onBackPressed() {
        retourAlert();
    }

    private void retourAlert(){
        //initialiser une dialogue de anvertissement
        AlertDialog.Builder builder = new AlertDialog.Builder(Events.this,R.style.AlertDialog);
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
                Events.super.onBackPressed();
                Animatoo.animateFade(Events.this);
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


    public void rapelleMoi(View view) {
        Date dateClicked = new Date();
        Context context = getApplicationContext();
        if(dateClicked.toString().compareTo("Tue Oct 04 01:00:00 GMT+01:00 2022")==0){

            NotificationManager mNotificationManager;

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
            Intent ii = new Intent(this.getApplicationContext(), UserProfile.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Vous serez rappelé de l'événement quand il arrivera!");
            bigText.setBigContentTitle("Rappeler");
            bigText.setSummaryText("Événements");

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setContentTitle("Your Title");
            mBuilder.setContentText("Your text");
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);

            mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            // === Removed some obsoletes
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                String channelId = "Your_channel_id";
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }

            mNotificationManager.notify(0, mBuilder.build());

        }
        else if(dateClicked.toString().compareTo("Mon Apr 18 01:00:00 GMT+01:00 2022")==0){

        }
        else if(dateClicked.toString().compareTo("Thu Apr 28 01:00:00 GMT+01:00 2022")==0){

        }
        else {
            Toast.makeText(getApplicationContext(), "aucun événement dans ce jour !", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), dateClicked.toString(), Toast.LENGTH_LONG).show();
        }
    }
} //class end