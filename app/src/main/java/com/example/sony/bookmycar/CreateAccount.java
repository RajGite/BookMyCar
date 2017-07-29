package com.example.sony.bookmycar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextAddress;
    private RadioGroup rg;
    private RadioButton rb;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataBaseReference=firebaseDatabase.getReference();

        progressDialog=new ProgressDialog(this);

        editTextName= (EditText) findViewById(R.id.editTextName);
        editTextAddress= (EditText) findViewById(R.id.editTextAddress);
        //rg=(RadioGroup)findViewById(R.id.rg);
        //rb= (RadioButton)findViewById(rg.getCheckedRadioButtonId());
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        buttonSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //String User= rb.getText().toString().trim();
        final String name= editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
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

        /* if all feilds are filled register user by transfering all details to firebase server// */
       progressDialog.setMessage("Registering User ...");
       progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {

                            Toast.makeText(CreateAccount.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            //add details of user to firebase
                            //if(User.equals("Passenger")) {
                                //add details to passenger node in firebase
                                UserInformation u=new UserInformation(name,email);
                                pushData(u);
                              /* finish();
                                startActivity(new Intent(getApplicationContext(), Passenger.class));
                            }
                            else if(User.equals("Driver")){*/
                                //add details to driver node in firebase
                                finish();
                                startActivity(new Intent(getApplicationContext(), Driver.class));
                            //}

                        }
                        else
                        {
                            Toast.makeText(CreateAccount.this,"Couldn't Register.Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
    public void pushData(UserInformation u)
    {
        dataBaseReference.child(firebaseAuth.getCurrentUser().getUid().toString()).setValue(u);
    }
}
