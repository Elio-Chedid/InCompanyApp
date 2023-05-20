package com.example.utrixapp;

import static com.example.utrixapp.MainActivity.sharedpref;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    Aadapter aadapter;
    ArrayList<Quick> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth=FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("ANNOUNCEMENTS");

        recyclerView=findViewById(R.id.announceid);
        databaseReference= FirebaseDatabase.getInstance().getReference("Announcements");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        list= new ArrayList<>();
        aadapter=new Aadapter(this,list);
        recyclerView.setAdapter(aadapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Quick quick=dataSnapshot1.getValue(Quick.class);
                    list.add(quick);
                }
                aadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main2_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logouti:
                mAuth.signOut();
                SharedPreferences s=getSharedPreferences(sharedpref,MODE_PRIVATE);
                SharedPreferences.Editor editor=s.edit();
                editor.putString("mailSave","null");
                editor.putString("passSave","null");
                editor.apply();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.newsi:
                Intent intent=new Intent(getApplicationContext(),MainActivity3.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}