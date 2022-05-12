package in.ac.iiitd.projecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import in.ac.iiitd.projecto.Model.ProjectItem;

public class AddProject extends Fragment {
    EditText projectTitle, timeRequiredTextView, techStackTextView, requiredStudentsTextView, allocationStatusTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_project, container, false);
        timeRequiredTextView = view.findViewById(R.id.timeRequiredTextView);
        techStackTextView = view.findViewById(R.id.techStackTextView);
        requiredStudentsTextView = view.findViewById(R.id.requiredStudentsTextView);
        allocationStatusTextView = view.findViewById(R.id.allocationStatusTextView);
        projectTitle = view.findViewById(R.id.projectTitle);
        Boolean status = false ? allocationStatusTextView.getText().toString().toLowerCase().equals("no") : true;
        ProjectItem projectItem = new ProjectItem(projectTitle.getText().toString(), techStackTextView.getText().toString(),Integer.valueOf(timeRequiredTextView.getText().toString()), Integer.valueOf(requiredStudentsTextView.getText().toString()), status);
//        ProjectItem projectItem = new ProjectItem()
        return view;
    }
}
