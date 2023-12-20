package com.mydemo.elektra.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mydemo.elektra.MainActivity;
import com.mydemo.elektra.MyApplication;
import com.mydemo.elektra.PrimeActivity;
import com.mydemo.elektra.R;
import com.mydemo.elektra.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupDetailsFragment extends Fragment {


    Context context;

    protected EditText name, email, phone;

    protected Button submitButton;

    protected String fullName, mailId;

    protected LinearLayout successLayout, registerLayout, progressRegister;

    protected String mobileNumber;


    public SignupDetailsFragment() {
        // required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_details, container, false);

        name = view.findViewById(R.id.edit_name);
        email = view.findViewById(R.id.edit_email);
        phone = view.findViewById(R.id.edit_phone);

        submitButton = view.findViewById(R.id.submit_button);

        progressRegister = view.findViewById(R.id.progressbarRegister);
        successLayout = view.findViewById(R.id.register_success_layout);
        registerLayout = view.findViewById(R.id.signup_register_layout);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerLayout.setVisibility(View.VISIBLE);
        progressRegister.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetails();
            }
        });

    }

    private void updateDetails() {

        fullName = name.getText().toString().trim();
        mailId = email.getText().toString().trim();

        if (fullName.isEmpty()) {
            name.setError("Name Required");
            name.requestFocus();
            return;
        }

        if (!(mailId.isEmpty())) {
            if (!Patterns.EMAIL_ADDRESS.matcher(mailId).matches()) {
                email.setError("enter a valid email");
                email.requestFocus();
                return;
            }
        }






        registerLayout.setVisibility(View.GONE);
        progressRegister.setVisibility(View.VISIBLE);
        successLayout.setVisibility(View.GONE);



        checkExistingUser();



    }

    private void checkExistingUser() {

        Log.d("testing1","checking existing user");

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users/");

        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User userProfile;

                Log.d("testing1", "fetching details");

                if(dataSnapshot.exists()){

                    userProfile = dataSnapshot.getValue(User.class);
                    Log.d("testing1", userProfile.getName());

                    if(userProfile.getName().isEmpty()){

                        Log.d("testing1","createnewuser() called");

                        createNewUser();

                    }else{

                        if(!(userProfile.getName().equals(fullName)) || !(userProfile.getEmail().equals(mailId)) ){


                            Log.d("testing1", "provided data is different. updating result");
                            updateUser();

                        }else{

                            registerLayout.setVisibility(View.GONE);
                            progressRegister.setVisibility(View.GONE);
                            successLayout.setVisibility(View.VISIBLE);

                            delay();

                        }

                    }

                }else{

                    Log.d("testing1","no record found");
                    createNewUser();
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }

        });


    }



    private void createNewUser() {


        Log.d("testing1","creating new user");

        User user = new User(
                fullName,
                mailId,
                mobileNumber,
                0L
        );

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {

                    registerLayout.setVisibility(View.GONE);
                    progressRegister.setVisibility(View.GONE);
                    successLayout.setVisibility(View.VISIBLE);

                    Log.d("testing1","new user successfully created");
                    delay();

                } else {

                    registerLayout.setVisibility(View.VISIBLE);
                    progressRegister.setVisibility(View.GONE);
                    successLayout.setVisibility(View.GONE);

                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.e("testing1", "error in creating user" + task.getException().toString());
                }
            }
        });

    }

    private void updateUser(){


        Log.d("testing1","updating user");

        Log.d("testing1",fullName + "  " + mailId + "  " + mobileNumber);

        Map<String, Object> hashMap = new HashMap<>();

        hashMap.put("name", fullName);
        hashMap.put("email", mailId);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    registerLayout.setVisibility(View.GONE);
                    progressRegister.setVisibility(View.GONE);
                    successLayout.setVisibility(View.VISIBLE);

                    Log.d("testing1"," user update successfully");
                    delay();

                } else {

                    registerLayout.setVisibility(View.VISIBLE);
                    progressRegister.setVisibility(View.GONE);
                    successLayout.setVisibility(View.GONE);

                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.e("testing1", "error in updating" + task.getException().toString());
                }
            }
        });


    }

    private void delay(){

        Log.d("testing1","opening main activity");

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(getContext(), PrimeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 2000);
    }

}
