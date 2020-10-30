package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    Integer REQUEST_CAMERA=1, SELECT_IMAGE=0, REQUEST_FIRSTNAME=2,REQUEST_LASTNAME=3,REQUEST_PHONE=4,REQUEST_EMAIL=5;
    ImageView imageViewBackArrowEditprofile,accountimage,circleImageView;
    TextView firstnameeditprofile,lastnameeditprofile,phoneeditprofile,emaileditprofile,passwordeditprofile;
    String firstname,lastname,phone,email,password;
    FirebaseDatabase rootnode;
    DatabaseReference myref;
    ArrayList<String> data;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imageViewBackArrowEditprofile=findViewById(R.id.imageViewBackArrowEditprofile);

        rootnode = FirebaseDatabase.getInstance();
        myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid());

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                firstnameeditprofile.setText(snapshot.child("fname").getValue().toString());
                lastnameeditprofile.setText(snapshot.child("lname").getValue().toString());
                phoneeditprofile.setText(snapshot.child("phone").getValue().toString());
                emaileditprofile.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageViewBackArrowEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        firstnameeditprofile=findViewById(R.id.firstnameeditprofile);
        lastnameeditprofile=findViewById(R.id.lastnameeditprofile);
        firstnameeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,UpdateFirstName.class);
                startActivityForResult(intent,REQUEST_FIRSTNAME);
            }
        });
        lastnameeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,UpdateLastName.class);
                startActivityForResult(intent,REQUEST_LASTNAME);
            }
        });
        phoneeditprofile=findViewById(R.id.phoneeditprofile);
        phoneeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,UpdatePhoneNumber.class);
                startActivityForResult(intent,REQUEST_PHONE);
            }
        });
        emaileditprofile=findViewById(R.id.emaileditprofile);
        emaileditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,UpdateEmailAddress.class);
                startActivityForResult(intent,REQUEST_EMAIL);
            }
        });
        passwordeditprofile=findViewById(R.id.passwordeditprofile);
        passwordeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,UpdatePassword.class);
                startActivity(intent);
            }
        });
        accountimage=findViewById(R.id.accountimage);
        circleImageView=findViewById(R.id.circleImageView);
        accountimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

    }

    private void SelectImage()
    {
        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera"))
                {
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);
                }
                else if(items[i].equals("Gallery"))
                {
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent,SELECT_IMAGE);

                } else if (items[i].equals("Cancel"))
                {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            if(requestCode==REQUEST_CAMERA)
            {
                Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                circleImageView.setImageBitmap(bitmap);
                accountimage.setVisibility(View.GONE);
            }
            else if(requestCode==SELECT_IMAGE)
            {
                Uri selectImage=data.getData();
                circleImageView.setImageURI(selectImage);
                accountimage.setVisibility(View.GONE);
            }
            else if(requestCode==REQUEST_FIRSTNAME)
            {
                firstname=data.getStringExtra("firstname");
                firstnameeditprofile.setText(firstname);
                myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid()).child("fname");
                myref.setValue(firstname);

            }
            else if(requestCode==REQUEST_LASTNAME)
            {
                lastname=data.getStringExtra("lastname");
                lastnameeditprofile.setText(lastname);
                myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid()).child("lname");
                myref.setValue(lastname);
            }
            else if(requestCode==REQUEST_PHONE)
            {
                phone=data.getStringExtra("phone");
                phoneeditprofile.setText(phone);
                myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid()).child("phone");
                myref.setValue(phone);
            }
            else if(requestCode==REQUEST_EMAIL)
            {
                email=data.getStringExtra("email");
                emaileditprofile.setText(email);
                myref = rootnode.getReference().child("Users").child("Customers").child(mAuth.getInstance().getCurrentUser().getUid()).child("email");
                myref.setValue(email);
            }
        }
    }
}