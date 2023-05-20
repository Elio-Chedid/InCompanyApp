 package com.example.utrixapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 public class clicked_item extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> list;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);
        recyclerView=findViewById(R.id.imid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String []str=Nadapter.urlim.split("\n");
        list=new ArrayList<>();
        list= Arrays.asList(str);
        myAdapter=new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity3.class);
        startActivity(i);
        finish();
    }
}