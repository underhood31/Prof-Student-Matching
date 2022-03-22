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
                System.out.println("Is this happening : "+userEmailAddress.getText().toString());
                intent.putExtra("userEmailAddress", userEmailAddress.getText().toString());
                startActivity(intent);

            }
        });
        return view;
    }
}