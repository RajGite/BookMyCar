package com.example.sony.bookmycar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by SONY on 30-10-2017.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference;
    private FirebaseDatabase firebaseDatabase;

    private TextView name,address,mobno,email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_layout);
        name=(TextView)findViewById(R.id.name);
        address=(TextView)findViewById(R.id.address);
        mobno=(TextView)findViewById(R.id.mobno);
        email=(TextView)findViewById(R.id.email);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataBaseReference = firebaseDatabase.getReference();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            dataBaseReference.child(firebaseAuth.getCurrentUser().getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    try{
                        name.setText((String)snapshot.child("name").getValue());
                        address.setText((String)snapshot.child("address").getValue());
                        mobno.setText((String)snapshot.child("contact_no").getValue());
                        email.setText((String)snapshot.child("email").getValue());
                    } catch (Throwable e) {
                        System.out.println("Some Problem");
                        e.printStackTrace();
                    }
                }
                @Override public void onCancelled(DatabaseError error) { }
            });

        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            android.R.id.home:

                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
