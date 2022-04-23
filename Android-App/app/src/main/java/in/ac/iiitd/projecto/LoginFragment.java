package in.ac.iiitd.projecto;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {


    private TextView userSignUpBtn;
    private EditText userEmailAddress, userPassword;
    private Button userSignInBtn;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=  inflater.inflate(R.layout.fragment_login, container, false);
        userEmailAddress = (EditText) view.findViewById(R.id.userEmailAddress);
        userPassword = view.findViewById(R.id.userPassword);
        userSignInBtn = view.findViewById(R.id.userSignInBtn);
        userSignUpBtn = view.findViewById(R.id.userSignUpBtn);




        userSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),StudentActivity.class);
                if(userEmailAddress.getText().toString().compareTo("peeyush")==0){
                    Intent intent1= new Intent(getActivity(),ProfessorActivity.class);
                    startActivity(intent1);
                }
                if (!isEmailValid(userEmailAddress.getText().toString())) {
                    Toast toast = Toast.makeText(getContext(), "Invalid Email ID",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (userPassword.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getContext(), "Please enter a password",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                System.out.println("Is this happening : "+userEmailAddress.getText().toString());
                intent.putExtra("userEmailAddress", userEmailAddress.getText().toString());
                startActivity(intent);
            }
        });
        return view;
    }
}