package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
//#import com.google.firebase.auth.FirebaseAuth;
//#import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
//import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    /*Button usersignup;
    TextView textViewLogin;
    ImageView imageViewBackArrow;
    private FirebaseAuth.AuthStateListener FirebaseAuthListener;
    */

    private FirebaseAuth mAuth;

    TextInputEditText fname,lname,email;
    private FirebaseDatabase rootnode;
    private DatabaseReference myref;

    MaterialButton donebuttonstep1,skipbuttonstep1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname = findViewById(R.id.getfirstname);
        lname = findViewById(R.id.getlastname);
        email = findViewById(R.id.getemail);
        donebuttonstep1=findViewById(R.id.donebuttonstep1);
        skipbuttonstep1=findViewById(R.id.skipbuttonstep1);
        skipbuttonstep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,BasicSearch.class);
                startActivity(intent);
            }
        });
        donebuttonstep1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                if(!validateEmail() | !validateFname() | !validateLname()){
                    return;
                }
                String phno = getIntent().getStringExtra("phno");
                String first = fname.getText().toString();
                String sec = lname.getText().toString();
                String emal = email.getText().toString();
                Contact newUser = new Contact(first,sec,phno,emal);
                rootnode = FirebaseDatabase.getInstance();
                myref = rootnode.getReference().child("Users").child("Customers").child(phno);
                myref.setValue(newUser);
                Intent intent=new Intent(MainActivity.this,BasicSearch.class);
                startActivity(intent);
            }
        });

        /*mAuth = FirebaseAuth.getInstance();
        FirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, BasicSearch.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        usersignup = findViewById(R.id.usersignup);
        textViewLogin = findViewById(R.id.textViewLogin);
        imageViewBackArrow = findViewById(R.id.imageViewBackArrow);
        email = findViewById(R.id.useremail1);
        pass = findViewById(R.id.userpassword1);
        fname = findViewById(R.id.userfirstname);
        lname = findViewById(R.id.userlastname);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        usersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootnode =  FirebaseDatabase.getInstance();
                ref = rootnode.getReference("User");



                String Useremail = email.getText().toString();
                String Userpass = pass.getText().toString();
                String Fname= fname.getText().toString();
                String Lname = lname.getText().toString();

                Log.d("BHECNHOD", Useremail);
                Log.d("BHECNHOD", Userpass);

                mAuth.createUserWithEmailAndPassword(Useremail, Userpass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("BHECNHOD", "SUCCES BHECNHIOD");
                            Toast.makeText(MainActivity.this, "Sign Up SUCCESS", Toast.LENGTH_SHORT).show();
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference curr_user_db = FirebaseDatabase.getInstance().getReference().child("User").child("Customers").child(user_id);
                            curr_user_db.setValue(true);
                        }
                    }
                });
                Intent intent = new Intent(MainActivity.this, BasicSearch.class);
                startActivity(intent);
            }
        });
        imageViewBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

    }

    private boolean validateFname(){
        String fn = fname.getText().toString();
        String nowhitespace = "(?=\\s+$)";

        if(fn.isEmpty()){
            fname.setError("Please Enter a Valid Name");
            return false;
        }
        else if(fn.matches(nowhitespace)){
            fname.setError("Name Cannot Have WhiteSpace");
            return false;
        }else{
            fname.setError(null);
            return true;
        }
    };

    private boolean validateEmail(){
        String emal = email.getText().toString();
        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(emal.isEmpty()){
            email.setError("Please Enter a Valid Email");
            return false;
        }
        else if(!emal.matches(pattern)){
            email.setError("Incorrect Email Provided");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    };

    private boolean validateLname(){
        String fn = lname.getText().toString();
        String nowhitespace = "(?=\\s+$)";

        if(fn.isEmpty()){
            lname.setError("Please Enter a Valid Name");
            return false;
        }
        else if(fn.matches(nowhitespace)){
            lname.setError("Name Cannot Have WhiteSpace");
            return false;
        }else{
            lname.setError(null);
            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(FirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.removeAuthStateListener(FirebaseAuthListener);
    }
}

