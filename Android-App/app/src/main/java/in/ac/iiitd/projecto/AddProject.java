package in.ac.iiitd.projecto;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import in.ac.iiitd.projecto.Model.ProjectItem;

public class AddProject extends Fragment {
    EditText projectTitle, projectDescription,timeRequiredTextView, techStackTextView, requiredStudentsTextView, allocationStatusTextView;
    Button addProject;
    private int advisorID;
    private String res1,res2,res3;

    AddProject(int advisorID, String res1,String res2,String res3) {
        this.advisorID=advisorID;
        this.res1=res1;
        this.res2=res2;
        this.res3=res3;
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

        String URL="https://prof-student-matching.herokuapp.com/project/";
        JSONObject jsonObject = new JSONObject();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            jsonObject.put("title",projectItem.getProjectTitle());
            jsonObject.put("descr",projectItem.getProjectDescription());
            jsonObject.put("time_req",projectItem.getProjectTimeRequired());
            jsonObject.put("tech_stack",projectItem.getProjectTechStack());
            jsonObject.put("sel_stud",null);
            jsonObject.put("alloc_stat",projectItem.getProjectStatus());
            jsonObject.put("req_stu_no",projectItem.getProjectRequiredStudents());
            jsonObject.put("advisor_id",Integer.toString(advisorID));
            jsonObject.put("res_interest1",Integer.parseInt(res1));
            jsonObject.put("res_interest2",Integer.parseInt(res2));
            jsonObject.put("res_interest3",Integer.parseInt(res3));
            final String requestBody = jsonObject.toString();
            Log.d("TAG", "createNewProject: "+requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast.makeText(getContext(),"Project Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }

            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
