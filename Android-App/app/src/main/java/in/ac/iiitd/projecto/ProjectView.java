package in.ac.iiitd.projecto;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProjectView extends Fragment {

    private TextView projectTitle, advisorName, projectDescriptionTextView, timeRequiredTextView, techStackTextView, requiredStudentsTextView, allocationStatusTextView;

    public ProjectView() {
        // Required empty public constructor
    }


    public static ProjectView newInstance(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        ProjectView fragment = new ProjectView();
        Bundle args = new Bundle();
        args.putString("title", s1);
        args.putString("advisorName", s2);

        args.putString("description", s3);
        args.putString("timeRequired", s4);
        args.putString("techStack", s5);
        args.putString("requiredStudents", s6);
        args.putString("allocationStatus", s7);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_view, container, false);

        projectTitle = view.findViewById(R.id.projectTitle);
        advisorName = view.findViewById(R.id.advisorName);
        projectDescriptionTextView = view.findViewById(R.id.projectDescriptionTextView);
        timeRequiredTextView = view.findViewById(R.id.timeRequiredTextView);
        techStackTextView = view.findViewById(R.id.techStackTextView);
        timeRequiredTextView = view.findViewById(R.id.timeRequiredTextView);
        requiredStudentsTextView = view.findViewById(R.id.requiredStudentsTextView);
        allocationStatusTextView = view.findViewById(R.id.allocationStatusTextView);

        projectTitle.setText(getArguments().getString("title"));
        advisorName.setText(getArguments().getString("advisorName"));
        projectDescriptionTextView.setText(getArguments().getString("description"));
        timeRequiredTextView.setText(getArguments().getString("timeRequired"));
        techStackTextView.setText(getArguments().getString("techStack"));
        requiredStudentsTextView.setText(getArguments().getString("requiredStudents"));
        allocationStatusTextView.setText(getArguments().getString("allocationStatus"));
        return view;
    }
}