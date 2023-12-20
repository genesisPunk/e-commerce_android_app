package com.mydemo.elektra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mydemo.elektra.fragment.SignupMobileFragment;
import com.mydemo.elektra.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    protected FragmentManager fragmentManager;
    protected SignupMobileFragment signupMobileFragment;
    protected LinearLayout fragmentContainer;

    private EditText fullName, email, password, phoneNo;
    private Button signUpButton;
    private TextView loginInReturn;
    private RelativeLayout signUpLayout;
    private RelativeLayout formLayout;
    private LinearLayout progressBar;
    private LinearLayout success;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        fragmentManager = this.getSupportFragmentManager();

        signupMobileFragment = new SignupMobileFragment();

        fragmentContainer = findViewById(R.id.fragment_container);


        openMobileFragment();

        firebaseAuth = FirebaseAuth.getInstance();

//        progressBar.setVisibility(View.GONE);
//        formLayout.setVisibility(View.VISIBLE);
//        success.setVisibility(View.GONE);



//        loginInReturn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });





    }

    private void openMobileFragment() {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,signupMobileFragment,"mobile");
        transaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){
            //handle the already log in user
            Intent intent = new Intent(SignupActivity.this, PrimeActivity.class);
            startActivity(intent);
        }
    }

    private void registerUser() {

        final String nameEdit = fullName.getText().toString().trim();
        final String emailEdit = email.getText().toString().trim();
        String passwordEdit = password.getText().toString().trim();
        final String phoneNoEdit = phoneNo.getText().toString().trim();

        if(nameEdit.isEmpty()){
            fullName.setError("Name Required");
            fullName.requestFocus();
            return;
        }

        if(emailEdit.isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
            return;
        }

        if(passwordEdit.isEmpty()){
            password.setError("Password Required");
            password.requestFocus();
            return;
        }

        if(phoneNoEdit.isEmpty()){
            phoneNo.setError("Phone No Required");
            phoneNo.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailEdit).matches()){
            email.setError("enter a valid email");
            email.requestFocus();
            return;
        }

        if(passwordEdit.length() < 6) {
            password.setError("Password should be atleast 6 characters long");
            password.requestFocus();
            return;
        }

        if(phoneNoEdit.length() != 10) {
            phoneNo.setError("Enter a valid phone number");
            phoneNo.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        formLayout.setVisibility(View.GONE);
        success.setVisibility(View.GONE);

        firebaseAuth.createUserWithEmailAndPassword(emailEdit, passwordEdit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //we will store the additional fields in firebase database

                    User user = new User(
                            nameEdit,
                            emailEdit,
                            phoneNoEdit
                    );

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                progressBar.setVisibility(View.GONE);
                                formLayout.setVisibility(View.GONE);
                                success.setVisibility(View.VISIBLE);
                                delay();

                            }else{
                                Toast.makeText(SignupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                Log.e("test",task.getException().toString());

                                progressBar.setVisibility(View.GONE);
                                formLayout.setVisibility(View.VISIBLE);
                                success.setVisibility(View.GONE);
                            }
                        }
                    });





                }else{
                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                    formLayout.setVisibility(View.VISIBLE);
                    success.setVisibility(View.GONE);
                }
            }
        });


    }

    private void delay(){
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
                Intent intent = new Intent(SignupActivity.this, PrimeActivity.class);
                startActivity(intent);
            }

        }, 2000L);
    }


}
