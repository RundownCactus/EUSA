package com.salmanqureshi.eusa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginReceiveVerificationCode extends AppCompatActivity {
    MaterialButton verifycodelogin;
    ImageView loginverifybackbutton;
    EditText CodeByUser;
    String verCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_receive_verification_code);
        CodeByUser = findViewById(R.id.verificationcode);
        String phno = getIntent().getStringExtra("phno");
        sendCode(phno);


        loginverifybackbutton=findViewById(R.id.loginverifybackbutton);
        verifycodelogin=findViewById(R.id.verifycodelogin);
        verifycodelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = CodeByUser.getText().toString();
                if(code.isEmpty() || code.length()<6){
                    CodeByUser.setError("Wrong OTP");
                    CodeByUser.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
        loginverifybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void sendCode(String phno) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+92" + phno,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginReceiveVerificationCode.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential cred = PhoneAuthProvider.getCredential(verCode,code);
        signIn(cred);
    }

    private void signIn(PhoneAuthCredential cred) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(cred).addOnCompleteListener(LoginReceiveVerificationCode.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),BasicSearch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}