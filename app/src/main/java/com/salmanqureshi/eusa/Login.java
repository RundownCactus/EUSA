package com.salmanqureshi.eusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {
    /*Button userlogin;
    TextView textViewSignup1;
    TextInputEditText email,pass;
    ImageView imageViewBackArrow1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener FirebaseAuthListener;*/

    TextInputEditText phno;

    ImageView phonenobackbuttonlogin;
    MaterialButton sendverificationcodelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phno = findViewById(R.id.verifyphonenumberlogin);
        phonenobackbuttonlogin=findViewById(R.id.phonenobackbuttonlogin);
        sendverificationcodelogin=findViewById(R.id.sendverificationcodelogin);
        phonenobackbuttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sendverificationcodelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validatePhno()){
                    return;
                }
                String ph = phno.getText().toString();
                Intent intent=new Intent(Login.this,LoginReceiveVerificationCode.class);
                intent.putExtra("phno",ph);
                startActivity(intent);
            }
        });

        /*userlogin=findViewById(R.id.userlogin);
        textViewSignup1=findViewById(R.id.textViewSignup1);
        imageViewBackArrow1=findViewById(R.id.imageViewBackArrow1);
        email = findViewById(R.id.loginemail1);
        pass = findViewById(R.id.loginpassword1);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(Login.this, BasicSearch.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Useremail = email.getText().toString();
                final String Userpass = pass.getText().toString();
                mAuth.signInWithEmailAndPassword(Useremail,Userpass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intent=new Intent(Login.this,BasicSearch.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });
        textViewSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });
        imageViewBackArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        */
    }

    //Phone number validation function
    private boolean validatePhno(){
        String ph = phno.getText().toString();
        String nowhitespace = "(?=\\s+$)";

        if(ph.isEmpty()){
            phno.setError("Please Enter a Valid Phone Number");
            return false;
        }
        else if(ph.length()>11) {
            phno.setError("Please Enter a Valid Phone Number");
            return false;
        }else if(ph.length()<10){
                phno.setError("Please Enter a Valid Phone Number");
                return false;
        }else if(ph.matches(nowhitespace)){
            phno.setError("Phone Number Cannot be Null");
            return false;
        }else{
            phno.setError(null);
            return true;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.addAuthStateListener(FirebaseAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.removeAuthStateListener(FirebaseAuthListener);
    }
}