package com.example.sony.bookmycar;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by SONY on 24-10-2017.
 */
public class LogoutFragment extends Fragment implements View.OnClickListener {

    View myView;
    private Button buttonYes,buttonNo;
    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        firebaseAuth=FirebaseAuth.getInstance();
        myView=inflater.inflate(R.layout.logout_layout,container, false);

        buttonYes=(Button) myView.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(this);
        buttonNo=(Button) myView.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.buttonYes){
            firebaseAuth.signOut();
            Context context=v.getContext();
            Intent intent=new Intent(context,MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else if(v.getId()==R.id.buttonNo){
            Context context=v.getContext();
            Intent intent=new Intent(context,Passenger.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
