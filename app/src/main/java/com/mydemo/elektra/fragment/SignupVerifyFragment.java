package com.mydemo.elektra.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mydemo.elektra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupVerifyFragment extends Fragment {


    Context context;

    private LinearLayout progressBar;
    private LinearLayout successLayout, containerLayout;
    protected ProgressBar resendProgress;
    protected TextView resendButton;

    protected Button verifyButton;
    protected TextView message;

    protected EditText editTextCode;

    private FirebaseAuth firebaseAuth;

    protected String number;
    private String verificationId;

    protected SignupDetailsFragment signupDetailsFragment;



    public SignupVerifyFragment() {
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

        View view = inflater.inflate(R.layout.fragment_signup_verify, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        verifyButton = view.findViewById(R.id.verify_button);
        editTextCode = view.findViewById(R.id.edit_code);
        message = view.findViewById(R.id.verify_message);

        resendButton = view.findViewById(R.id.text_resend_button);
        resendProgress = view.findViewById(R.id.simpleProgressBar);

        progressBar = view.findViewById(R.id.progressbar_verify);
        successLayout = view.findViewById(R.id.verify_success_layout);
        containerLayout = view.findViewById(R.id.container_verify_layout);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        resendButton.setVisibility(View.VISIBLE);
        resendProgress.setVisibility(View.GONE);


        progressBar.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);
        containerLayout.setVisibility(View.VISIBLE);

        message.setText(number);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editTextCode.getText().toString().trim();

                if(code == null || code.length()<6){

                    editTextCode.setError("Please enter valid OTP");
                    editTextCode.requestFocus();
                    return;
                }

                verifyOtp(code);
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendButton.setVisibility(View.GONE);
                resendProgress.setVisibility(View.VISIBLE);
                delay();
                sendOtp(number);
            }
        });

        sendOtp(number);
    }

    private void verifyOtp(String code) {

        progressBar.setVisibility(View.VISIBLE);
        successLayout.setVisibility(View.GONE);
        containerLayout.setVisibility(View.GONE);

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredentials(credential);
        //let user sign in after this

    }

    private void signInWithCredentials(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    successLayout.setVisibility(View.VISIBLE);
                    containerLayout.setVisibility(View.GONE);
                    delay1();
                }else {
                    progressBar.setVisibility(View.GONE);
                    successLayout.setVisibility(View.GONE);
                    containerLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void openDetailsFragment() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if(signupDetailsFragment == null) {
            signupDetailsFragment = new SignupDetailsFragment();
            signupDetailsFragment.mobileNumber = number;
            transaction.replace(R.id.fragment_container, signupDetailsFragment, "details");
            transaction.commit();
        }else{

            signupDetailsFragment.mobileNumber = number;
            transaction.replace(R.id.fragment_container, signupDetailsFragment, "details");
            transaction.commit();
        }

    }

    private void sendOtp(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                editTextCode.setText(code);
                verifyOtp(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void delay(){
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                resendButton.setVisibility(View.VISIBLE);
                resendProgress.setVisibility(View.GONE);
            }
        }, 30000);
    }

    private void delay1(){
        openDetailsFragment();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                openDetailsFragment();
            }
        }, 2000);
    }



}




