package com.mydemo.elektra.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mydemo.elektra.R;

public class SignupMobileFragment extends Fragment {

    Context context;

    protected Button sendOtpButton;
    protected SignupVerifyFragment signupVerifyFragment;

    protected EditText editTextMobileNumber;

    protected LinearLayout progressLayout, screenLayout;



    public SignupMobileFragment() {
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

        View view = inflater.inflate(R.layout.fragment_signup_mobile, container, false);


        sendOtpButton = view.findViewById(R.id.send_otp_button);
        editTextMobileNumber = view.findViewById(R.id.edit_phone);
        progressLayout = view.findViewById(R.id.progressbar_mobile);
        screenLayout = view.findViewById(R.id.signup_mobile_layout);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        progressLayout.setVisibility(View.GONE);
        screenLayout.setVisibility(View.VISIBLE);

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyCredentials();
            }
        });

    }

    private void verifyCredentials() {

        String number = editTextMobileNumber.getText().toString().trim();

        if(number.isEmpty()){
            editTextMobileNumber.setError("Number is required");
            editTextMobileNumber.requestFocus();
            return;
        }

        if(number.length() != 10) {
            editTextMobileNumber.setError("Enter a valid phone number");
            editTextMobileNumber.requestFocus();
            return;
        }

        openOtpVerifyFragment("+91" + number);
    }


    private void openOtpVerifyFragment(String number) {

        progressLayout.setVisibility(View.VISIBLE);
        screenLayout.setVisibility(View.GONE);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        if (signupVerifyFragment == null) {
            signupVerifyFragment = new SignupVerifyFragment();
            signupVerifyFragment.number = number;
            transaction.replace(R.id.fragment_container, signupVerifyFragment, "otpverify");
            transaction.addToBackStack("otpverify");
            transaction.commit();
        } else {
            signupVerifyFragment.number = number;
            transaction.replace(R.id.fragment_container, signupVerifyFragment, "otpverify");
            transaction.addToBackStack("otpverify");
            transaction.commit();
        }

    }


}
