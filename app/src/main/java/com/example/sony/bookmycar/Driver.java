package com.example.sony.bookmycar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Driver extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewWelcome;
    private Button buttonSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();

        textViewWelcome=(TextView)findViewById(R.id.textViewWelcome);
        buttonSignout=(Button)findViewById(R.id.buttonSignout);

        textViewWelcome.setText("Welcome Driver "+user.getEmail());
        buttonSignout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
