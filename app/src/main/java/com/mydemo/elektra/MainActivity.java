package com.mydemo.elektra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView signupButton;

    private EditText editUsername, editPassword;

    private LinearLayout progressBar;
    private RelativeLayout loginForm;
    private LinearLayout successLayout;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        editUsername = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressbar);
        loginForm = findViewById(R.id.loginForm);
        successLayout = findViewById(R.id.success_layout);

        setButtonOnClick();

        firebaseAuth = FirebaseAuth.getInstance();


        progressBar.setVisibility(View.GONE);
        loginForm.setVisibility(View.VISIBLE);
        successLayout.setVisibility(View.GONE);

    }

    private void setButtonOnClick() {


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    private void userLogin() {

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        if (username.isEmpty()) {
            editUsername.setError("Email Required");
            editUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password Required");
            editPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        loginForm.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    loginForm.setVisibility(View.GONE);
                    successLayout.setVisibility(View.VISIBLE);
                    delay();


                } else {
                    Toast.makeText(MainActivity.this, "Wrong password or Username!", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                    loginForm.setVisibility(View.VISIBLE);
                    successLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){
            //handle the already log in user
        Intent intent = new Intent(MainActivity.this, PrimeActivity.class);
        startActivity(intent);
        }
    }

    public void openSignupActivity(View view) {

        finish();
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);

    }

    private void delay(){
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                finish();
                Intent intent = new Intent(MainActivity.this, PrimeActivity.class);
                startActivity(intent);
            }

        }, 1000L);
    }
}
