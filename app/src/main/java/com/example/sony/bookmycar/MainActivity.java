package com.example.sony.bookmycar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //start passenger or driver activity accordingly
            finish();
            startActivity(new Intent(getApplicationContext(),Passenger.class));
        }
        progressDialog=new ProgressDialog(this);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        buttonSignin=(Button)findViewById(R.id.buttonSignin);
        textViewSignup=(TextView) findViewById(R.id.textVeiwSignup);

        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonSignin)
        {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Plese Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password))
            {
                Toast.makeText(this, "Plese Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Signing in ...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                           if(task.isSuccessful())
                           {
                               //start passenger or driver activity
                               finish();
                               startActivity(new Intent(getApplicationContext(),Passenger.class));
                           }
                            else
                           {
                               Toast.makeText(MainActivity.this,"Account not found.Please create an account",Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
        }
        if(v==textViewSignup)
        {
            //call create_account activity
            finish();
            startActivity(new Intent(this,CreateAccount.class));
        }
    }
}
