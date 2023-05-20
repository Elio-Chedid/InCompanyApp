package com.example.utrixapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class uploadNews extends AppCompatActivity {
    Button browse,upload,uploadobj;
    RecyclerView images;
    EditText news;
    TextView progress;
    public static final int IMAGE_CODE=1;
    List<ModalClass> modalClassList;
    CustomAdapter customAdapter;
    StorageReference mStorageRef;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    Uri imageuri;
    int totalitem;
    ArrayList<String> imLinks;
    ArrayList<Uri> urilist;
    int counter;
    int percentage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_news);
        progress=findViewById(R.id.progress);
        browse=findViewById(R.id.browse);
        upload=findViewById(R.id.submit);
        images=findViewById(R.id.images);
        news=findViewById(R.id.newsText);
        modalClassList=new ArrayList<>();
        urilist=new ArrayList<>();
        progressDialog=new ProgressDialog(getApplicationContext());
        imLinks=new ArrayList<>();
        images.setHasFixedSize(true);
        images.setLayoutManager(new LinearLayoutManager(this));
        mStorageRef= FirebaseStorage.getInstance().getReference().child("image");
        databaseReference= FirebaseDatabase.getInstance().getReference("news");
        uploadobj=findViewById(R.id.subnews);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent,IMAGE_CODE);
            }
        });

        uploadobj.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(totalitem==-1){
                    Calendar calendar=Calendar.getInstance();
                    String currentDate= DateFormat.getInstance().format(calendar.getTime());
                    String data=news.getText().toString();
                    News n1=new News(data,currentDate,"\n");
                    String uploadId=databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(n1);
                }else{
                    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime now=LocalDateTime.now();
                    String currentDate=dtf.format(now);
                    String data=news.getText().toString();
                    String s="";
                    for(int i=0;i<imLinks.size();i++){
                        s+=imLinks.get(i)+"\n";
                    }
                    News n=new News(data,currentDate,s);
                    String uploadId=databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(n);
                    Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(getApplicationContext(),MainActivityAdmin.class);
                startActivity(intent);
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalitem==-1){
                    Toast.makeText(getApplicationContext(),"select image",Toast.LENGTH_SHORT).show();
                }else{
                    progress.setText("uploading");
                    progress.setTextColor(Color.BLUE);
                    UploadImage();

                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==IMAGE_CODE && resultCode==RESULT_OK &&(data.getData()!=null||data.getClipData()!=null)){
                if(data.getClipData()!=null)
                    totalitem=data.getClipData().getItemCount();
                else
                    totalitem=1;
                for(int i=0;i<totalitem;i++){
                    if(totalitem>1){
                        imageuri= data.getClipData().getItemAt(i).getUri();
                        urilist.add(imageuri);
                        String imagename= getFileName(imageuri);
                        ModalClass modalClass=new ModalClass(imagename);
                        modalClassList.add(modalClass);
                    }else{
                        imageuri=data.getData();
                        urilist.add(imageuri);
                        String imagename=getFileName(imageuri);
                        ModalClass modalClass=new ModalClass(imagename);
                        modalClassList.add(modalClass);
                    }


                    customAdapter=new CustomAdapter(getApplicationContext(),modalClassList);
                    images.setAdapter(customAdapter);


                }




        }else{
            totalitem=-1;
        }
    }


    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadImage() {

        for( counter=0;counter<urilist.size();counter++){



            StorageReference storageReference=mStorageRef.child(System.currentTimeMillis()+"."+GetFileExtension(imageuri));
            storageReference.putFile(urilist.get(counter))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            final Task<Uri> firebaseuri=taskSnapshot.getStorage().getDownloadUrl();
                            firebaseuri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String download=uri.toString();
                                    imLinks.add(download);
                                    if(imLinks.size()==urilist.size()){
                                        progress.setText("uploaded");
                                        progress.setTextColor(Color.RED);
                                    }
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {


                                }
                            });
                        }
                    });

        }
    }


    @SuppressLint("Range")
    public String getFileName(Uri uri){
        String result=null;
        if(uri.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(uri,null,null,null,null);
            try{
                if(cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result==null){
            result=uri.getPath();
            int cut=result.lastIndexOf('/');
            if(cut !=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }
}