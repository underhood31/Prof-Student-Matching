package in.ac.iiitd.projecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu_student, menu);
        return true;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        onBackPressed();
        /*
         * TODO:
         * 1) Delete the downloaded profile picture
         * 2) Delete the downloaded resume
         * */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutButton:
                signOut();
                return true;
            case R.id.projSelected:
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StudentSelectedProjectFragment studentListOfProject = new StudentSelectedProjectFragment(currentUser.getEmail().split("@")[0]);
                fragmentTransaction.replace(R.id.layoutFragment,studentListOfProject)
                        .addToBackStack(studentListOfProject.getClass().getName())
                        .commit();
        }
        return super.onOptionsItemSelected(item);

    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
            ProjectView projectView = new ProjectView();
            ProjectView projectView1 = ProjectView.newInstance(intent.getStringExtra("title"),
                    intent.getStringExtra("advisorName"),
                    intent.getStringExtra("description"),
                    String.valueOf(intent.getStringExtra("timeRequired")),
                    intent.getStringExtra("techStack"),
                    String.valueOf(intent.getStringExtra("requiredStudents")),
                    intent.getStringExtra("allocationStatus")
                    );

            fragmentTransaction.replace(R.id.layoutFragment, projectView1)
                    .addToBackStack(projectView1.getClass().getName())
                    .commit();

        }
    };
    @Override
    public void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bReceiver, new IntentFilter("ProjectViewFragment"));

    }
    private String extractRollNo(String email) {
        String rollno="";
        int index=-1;
        for (int i=0; i<email.length(); ++i) {
            if (email.charAt(i)=='@') {
                index=i;
                break;
            }
        }
        rollno=email.substring(0,index);
        return rollno;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        String userEmailAddress = getIntent().getExtras().getString("userEmailAddress");

        String rollNo=extractRollNo(userEmailAddress);
        Toast.makeText(getApplicationContext(), "Logged in for "+rollNo, Toast.LENGTH_SHORT).show();
        Bundle bundle=new Bundle();
        bundle.putString("rollno",rollNo);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        student_profile_fragment studentProfileFragment = new student_profile_fragment();
        studentProfileFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layoutFragment, studentProfileFragment)
                .commit();
    }
}