package com.example.utrixapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class uploadAnnounce extends AppCompatActivity {
    EditText upload;
    Button btnupload;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_announce);
        upload=findViewById(R.id.editA);
        btnupload=findViewById(R.id.uptext);
        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference().child("Announcements");
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        btnupload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                upData();

                Intent i=new Intent(getApplicationContext(),MainActivityAdmin.class);
                startActivity(i);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivityAdmin.class);
        startActivity(i);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void upData(){
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now=LocalDateTime.now();
        String currentDate=dtf.format(now);
        String s1=upload.getText().toString();


        Quick ann=new Quick(s1,currentDate);
        ref.push().setValue(ann);
        sendNotification();
    }

    public void sendNotification(){
        String body;
        try {
             body = upload.getText().toString().substring(0, 8) + "...";
        }catch(Exception e){
             body=upload.getText().toString();
        }
        FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all","utrix",body,getApplicationContext(),uploadAnnounce.this);

        notificationsSender.SendNotifications();
    }
}