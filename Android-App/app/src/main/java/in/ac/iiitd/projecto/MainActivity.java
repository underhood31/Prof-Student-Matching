package in.ac.iiitd.projecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void buttonClick(View v){
//        switch(v.getId()) {
//            case R.id.userSignInBtn:
//                Intent myIntent = new Intent();
//                myIntent.setClassName(this, String.valueOf(StudentActivity.class));
//                // for ex: your package name can be "com.example"
//                // your activity name will be "com.example.Contact_Developer"
//                startActivity(myIntent);
//                break;
//        }
//    }
}