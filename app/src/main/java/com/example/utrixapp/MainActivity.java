package com.example.utrixapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class MainActivity extends AppCompatActivity {
    Button login;
    FirebaseAuth mAuth;
    EditText mail,pass;
    public static final String sharedpref="userSave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.login);
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        mAuth= FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("UTRIX");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mail.getText().toString().equalsIgnoreCase("admin@utrix.com")&&pass.getText().toString().equalsIgnoreCase("utrixadministrator")){
                    Intent i=new Intent(getApplicationContext(),MainActivityAdmin.class);
                    startActivity(i);
                    finish();
                }else
                    loginUser();



            }
        });
    }

    private void loginUser() {
        if(mail.getText().toString().trim().equals("")||pass.getText().toString().trim().equals(""))
            Toast.makeText(getApplicationContext(),"empty field",Toast.LENGTH_SHORT).show();
        else {
            mAuth.signInWithEmailAndPassword(mail.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           Toast.makeText(getApplicationContext(),"login Succefull",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),MainActivity2.class);
                            saveUser();
                            startActivity(i);
                            finish();
                        }else
                            Toast.makeText(getApplicationContext(),"error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences=getSharedPreferences(sharedpref,MODE_PRIVATE);
        if(sharedPreferences.contains("mailSave")&&sharedPreferences.contains("passSave")) {
            mAuth.signInWithEmailAndPassword(sharedPreferences.getString("mailSave", "def"), sharedPreferences.getString("passSave", "def")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }

        }
    public void saveUser(){
        SharedPreferences s=getSharedPreferences(sharedpref,MODE_PRIVATE);
        SharedPreferences.Editor editor=s.edit();

        editor.putString("mailSave",mail.getText().toString());
        editor.putString("passSave",pass.getText().toString());
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}