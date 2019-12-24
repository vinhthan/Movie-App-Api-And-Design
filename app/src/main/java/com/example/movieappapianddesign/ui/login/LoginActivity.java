package com.example.movieappapianddesign.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.movieappapianddesign.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLogin;
    private ProgressBar progressBarLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        auth = FirebaseAuth.getInstance();




    }

    private void initView() {
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        progressBarLogin = findViewById(R.id.progressBarLogin);
    }
}
