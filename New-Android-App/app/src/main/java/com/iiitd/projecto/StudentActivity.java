package com.iiitd.projecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();

        ((Button)findViewById(R.id.doit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.name)).setText(currentUser.getDisplayName());
                DatabaseReference myRef = firebaseDatabase.getReference(currentUser.getUid()+"/something");
                myRef.setValue("Something Again");
                signOut();
            }
        });



    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}