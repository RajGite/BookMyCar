package com.example.sony.bookmycar;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataBaseReference = firebaseDatabase.getReference();

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
            progressDialog.setMessage("Signing in ...");
            progressDialog.show();

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password))
            {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               /*Finds the signed in user node in firebase*/
                               dataBaseReference.child(firebaseAuth.getCurrentUser().getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot snapshot) {

                                       try{
                                           /*Extracts the user type from the node found*/
                                           String user=(String)snapshot.child("user").getValue();

                                           progressDialog.dismiss();

                                           if(user.equals("Passenger")) {
                                               startActivity(new Intent(getApplicationContext(), Passenger.class));
                                               finish();
                                           }
                                           else if(user.equals("Driver")) {
                                               startActivity(new Intent(getApplicationContext(), Driver.class));
                                               finish();
                                           }

                                       } catch (Throwable e) {
                                           System.out.println("Some Problem");
                                           e.printStackTrace();
                                       }
                                   }
                                   @Override public void onCancelled(DatabaseError error) { }
                               });
                           }
                            else
                           {
                               progressDialog.dismiss();
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

