package in.ac.iiitd.projecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {
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
        System.out.println("YOOHOOO : "+userEmailAddress);

        String rollNo=extractRollNo(userEmailAddress);
        Toast.makeText(getApplicationContext(), "Logged in for "+rollNo, Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutFragment, new student_profile_fragment())
                .addToBackStack(null)
                .commit();
    }
}