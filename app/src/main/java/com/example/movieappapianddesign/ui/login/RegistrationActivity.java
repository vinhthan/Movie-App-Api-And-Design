package com.example.movieappapianddesign.ui.login;

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
import android.widget.Toast;

import com.example.movieappapianddesign.R;
import com.example.movieappapianddesign.ui.movies.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });


    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        String email, password;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6){
            Toast.makeText(this, "Password Must be >= 6 Characters", Toast.LENGTH_SHORT).show();
            return;
        }


        //create account
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegistrationActivity.this, "Registration failed! Please try again", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
    }
}
