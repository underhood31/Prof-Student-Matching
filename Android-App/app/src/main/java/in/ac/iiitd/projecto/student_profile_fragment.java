package in.ac.iiitd.projecto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import in.ac.iiitd.projecto.Model.Student;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class student_profile_fragment extends Fragment {

    private Button studentUploadResumeBtn, studentDownloadResumeBtn, studentApplyProjectBtn ;
    private TextView studentName, studentStream, studentDegree;
    private RequestQueue requestQueue;
    private static final String TAG = "VolleyActivity";

    public student_profile_fragment() {
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
        View view= inflater.inflate(R.layout.fragment_student_profile_fragment, container, false);
        Student student;
        student = new Student();

        String rollno=getArguments().getString("rollno");
        System.out.println("The roll number is : "+ rollno);
        studentApplyProjectBtn = view.findViewById(R.id.studentApplyProjectBtn);
        studentUploadResumeBtn = view.findViewById(R.id.studentUploadResumeBtn);
        studentDownloadResumeBtn = view.findViewById(R.id.studentDownloadResumeBtn);

        studentName = view.findViewById(R.id.studentName);
        studentStream = view.findViewById(R.id.studentStream);
        studentDegree = view.findViewById(R.id.studentDegree);



        requestQueue = Volley.newRequestQueue(getContext());
        StringBuilder urlBuild = new StringBuilder("https://prof-student-matching.herokuapp.com/students/");
        urlBuild.append(rollno);
        String url = urlBuild.toString();
        fetchData(url,student);


        studentApplyProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layoutFragment,new Student_list_of_project())
                .commit();
            }
        });
        return view;
    }


    private void fetchData(String url, Student student){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                System.out.println("this is the response : " + response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    System.out.println("hello");
                    StringBuilder sb = new StringBuilder();
                    sb.append(jsonObject.getString("first_name"));
                    sb.append(" ");
                    sb.append(jsonObject.getString("sec_name"));
                    student.setStudentName(sb.toString());
                    student.setStudentStream(jsonObject.getString("spec"));
                    student.setStudentDegree(jsonObject.getString("deg_type"));
                    studentName.setText(student.getStudentName());
                    studentStream.setText(student.getStudentStream());
                    studentDegree.setText(student.getStudentDegree());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i(TAG, "volley error: " + error.getMessage());
            }
        });

        requestQueue.add(request); //Execution
    }



}