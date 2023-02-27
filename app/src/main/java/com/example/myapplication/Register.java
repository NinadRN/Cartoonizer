package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText mFullName, mEmail, mPassword, mConfirmPW;
    Button mRegisterButton;
    TextView mLoginbutton;
    ProgressBar mprogressBar;

    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.editTextTextPersonName);
        mEmail = findViewById(R.id.EmailAddress);
        mPassword = findViewById(R.id.Inputpassword);
        mConfirmPW = findViewById(R.id.Confirmpassword);
        mRegisterButton= findViewById(R.id.Registerbutton);
        mLoginbutton = findViewById(R.id.AlreadyHaveAccount);
        mprogressBar = findViewById(R.id.progressbar);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        if (fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), LoginDashboard.class));
            finish();
        }

        mLoginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullname = mFullName.getText().toString();
                String confirmpw =   mConfirmPW.toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }


                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length()<6){

                    mPassword.setError("Password is too short");
                    return;
                }


                if (TextUtils.isEmpty(confirmpw)){
                    mConfirmPW.setError(("Confirm the password"));
                    return;
                }
                if (confirmpw == password){
                    Toast.makeText(Register.this, "You have been registered", Toast.LENGTH_SHORT).show();
                    return;
                }
                mprogressBar.setVisibility(View.VISIBLE);



                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Registered Successfull", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"Onfailure: Email Not sent"+ e.getMessage());
                                }
                            });

                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("user").document(userID);
                            Map<String,Object> user =new HashMap<>();
                            user.put("fName",fullname);
                            user.put("email",email);
                            user.put("password",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"on success: user profile is created for" + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"on  Failure: " + e.toString());

                                }
                            });

                            startActivity(new Intent(getApplicationContext(),LoginDashboard.class));
                        }
                        else {

                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mprogressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



    }
}