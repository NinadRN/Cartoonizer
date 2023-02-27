package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    EditText mPasscode, mEmail;
    Button mLoginButton;
    TextView mAlreadyAcc;
    ProgressBar mProgressBar;


    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editTextPerson);
        mPasscode = findViewById(R.id.loginpassword);
        mEmail = findViewById(R.id.editTextPerson);
        mLoginButton = findViewById(R.id.loginbutton);
        mAlreadyAcc = findViewById(R.id.forgotpassword);
        mProgressBar = findViewById(R.id.progressbar);


        fAuth = FirebaseAuth.getInstance();


        mAlreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginDashboard.class));
                finish();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = mEmail.getText().toString().trim();

                String password = mPasscode.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Email is requires");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPasscode.setError("Password  is requires");
                    return;
                }

                if (password.length() < 6) {
                    mPasscode.setError("Password is too short");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginDashboard.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}