package in.ac.iiitd.projecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        String userEmailAddress = getIntent().getExtras().getString("userEmailAddress");
        System.out.println("YOOHOOO : "+userEmailAddress);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutFragment, new student_profile_fragment())
                .addToBackStack(null)
                .commit();
    }
}