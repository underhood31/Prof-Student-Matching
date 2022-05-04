package in.ac.iiitd.projecto;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button googleSignIn;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private FirebaseAuth mAuth;
    private static final int REQ_ONE_TAP = 2;
    private AppCompatActivity thisActivity = this;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!=null) {
            //update UI
            Log.d("User Login", "Got ");
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.getReference("registered").child(currentUser.getEmail().split("@")[0]).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        String type=(String) task.getResult().getValue();
                        if (type.equals("student")) {
                            Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                            intent.putExtra("userEmailAddress", currentUser.getEmail());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(thisActivity, "Professor part not implemented yet.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(thisActivity, "Database Task Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Log.d("User Login", "Null ");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RequestCode", "onActivityResult: "+requestCode);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    Log.d("HERE", "onActivityResult: HERE");
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d("idToken", "Got ID token.");
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        firebaseDatabase.getReference("registered").child(username.split("@")[0]).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d("GetResult", "onComplete: "+task.getResult());
                                    if (task.getResult().getValue()!=null) {
                                        boolean isIIITD =username.split("@")[1].equals("iiitd.ac.in");
                                        Log.d("userID", "onActivityResult: "+ username +"\t"+isIIITD);

//                                        if (!isIIITD){
//                                            Toast.makeText(thisActivity, "Cannot login: The user is not IIITDian", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
                                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                                        mAuth.signInWithCredential(firebaseCredential)
                                                .addOnCompleteListener(thisActivity, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {

                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d("Signin", "signInWithCredential:success");
                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.w("Signin", "signInWithCredential:failure", task.getException());
                                                            updateUI(null);
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(thisActivity, "Cannot login: User not registered in the database", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(thisActivity, "Database Task Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d("password", "Got password.");
                    }

                } catch (ApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createRequest();
        setContentView(R.layout.activity_main);



        // --------------- Accessing Variable ------------------- //
        googleSignIn=findViewById(R.id.google_sign_in);

        // ----------- Setting on click listeners etc.. --------- //
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        createRequest();

    }

    private void createRequest() {
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.something))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
        mAuth = FirebaseAuth.getInstance();

    }

    private void signIn() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("TAG", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d("onFailure", e.getLocalizedMessage());
                    }
                });
    }


}