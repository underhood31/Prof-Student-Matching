package in.ac.iiitd.projecto;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class student_profile_fragment extends Fragment {

    private Button studentUploadResumeBtn, studentDownloadResumeBtn, studentApplyProjectBtn ;

    public student_profile_fragment() {
        // Required empty public constructor
    }


//    public static student_profile_fragment newInstance(String param1, String param2) {
//        student_profile_fragment fragment = new student_profile_fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_profile_fragment, container, false);
        studentApplyProjectBtn = view.findViewById(R.id.studentApplyProjectBtn);
        studentUploadResumeBtn = view.findViewById(R.id.studentUploadResumeBtn);
        studentDownloadResumeBtn = view.findViewById(R.id.studentDownloadResumeBtn);
        studentApplyProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layoutFragment,new Student_list_of_project())
                .addToBackStack(null)
                .commit();
            }
        });
        return view;
    }


}