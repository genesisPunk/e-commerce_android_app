package com.mydemo.elektra.ui.tools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.mydemo.elektra.R;

public class ToolsFragment extends Fragment {

    private ImageView whatsAppButton;
    private ImageView callButton;
    private ImageView mailButton;
    private FirebaseAuth firebaseAuth;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tools, container, false);


        whatsAppButton = root.findViewById(R.id.whatsapp_call);
        callButton = root.findViewById(R.id.call_button);
        mailButton = root.findViewById(R.id.mail_button);


        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        firebaseAuth = FirebaseAuth.getInstance();

        whatsAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri mUri = Uri.parse("smsto:+919404739347");
                Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                mIntent.setPackage("com.whatsapp");
                mIntent.putExtra("sms_body", "The text goes here");
                mIntent.putExtra("chat",true);
                startActivity(mIntent);

            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPermissionGranted()){
                    call_action();
                }

            }
        });


        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@elektracoatings.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, firebaseAuth.getCurrentUser().getPhoneNumber().toString());
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    private void call_action() {

        String phnum = "+919673731134";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phnum));
        startActivity(callIntent);

    }


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }



}