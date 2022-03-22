package in.ac.iiitd.projecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {
    private BroadcastReceiver bReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
            ProjectView projectView = new ProjectView();
            fragmentTransaction.replace(R.id.layoutFragment, projectView)
                    .addToBackStack(projectView.getClass().getName())
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
            if (Character.isDigit(email.charAt(i))) {
                index=i;
                break;
            }
        }
        rollno="20"+email.substring(index,index+5);
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
                .addToBackStack(studentProfileFragment.getClass().getName())
                .commit();
    }
}