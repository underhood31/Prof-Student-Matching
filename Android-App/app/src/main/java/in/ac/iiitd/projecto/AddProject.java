package in.ac.iiitd.projecto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import in.ac.iiitd.projecto.Model.ProjectItem;

public class AddProject extends Fragment {
    EditText projectTitle, projectDescription,timeRequiredTextView, techStackTextView, requiredStudentsTextView, allocationStatusTextView;
    Button addProject;
    private int advisorID;
    private String res1,res2,res3;

    AddProject(int advisorID, String res1,String res2,String res3) {

    }

    AddProject() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_project, container, false);
        timeRequiredTextView = view.findViewById(R.id.timeRequiredTextView);
        techStackTextView = view.findViewById(R.id.techStackTextView);
        requiredStudentsTextView = view.findViewById(R.id.requiredStudentsTextView);
        allocationStatusTextView = view.findViewById(R.id.allocationStatusTextView);
        projectDescription=view.findViewById(R.id.projectDescriptionTextView);
        projectTitle = view.findViewById(R.id.projectTitle);
//        ProjectItem projectItem = new ProjectItem()
        addProject=view.findViewById(R.id.addProject);



        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createNewProject();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Unable to add project", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }



    void createNewProject() {
        Boolean status = false ? allocationStatusTextView.getText().toString().toLowerCase().equals("no") : true;
        ProjectItem projectItem = new ProjectItem(projectTitle.getText().toString(), projectDescription.getText().toString(),techStackTextView.getText().toString(),Integer.valueOf(timeRequiredTextView.getText().toString()), Integer.valueOf(requiredStudentsTextView.getText().toString()), status);
        // TODO: add project to the backend

        String postLink="https://prof-student-matching.herokuapp.com/project/";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title",projectItem.getProjectTitle());
            jsonObject.put("descr",projectItem.getProjectDescription());
            jsonObject.put("time_req",projectItem.getProjectTimeRequired());
            jsonObject.put("tech_stack",projectItem.getProjectTechStack());
            jsonObject.put("sel_stud",null);
            jsonObject.put("alloc_stat",projectItem.getProjectStatus());
            jsonObject.put("req_stu_no",projectItem.getProjectRequiredStudents());
//            jsonObject.put("advisor_id",);
//            jsonObject.put("res_interest1",res1);
//            jsonObject.put("res_interest2",res2);
//            jsonObject.put("res_interest3",res3);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
