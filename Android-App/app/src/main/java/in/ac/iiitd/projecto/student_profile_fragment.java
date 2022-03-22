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
    private TextView studentName;
    private RequestQueue requestQueue;
    private static final String TAG = "VolleyActivity";

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

//        String name = savedInstanceState.getString("userEmailAddress");
        studentApplyProjectBtn = view.findViewById(R.id.studentApplyProjectBtn);
        studentUploadResumeBtn = view.findViewById(R.id.studentUploadResumeBtn);
        studentDownloadResumeBtn = view.findViewById(R.id.studentDownloadResumeBtn);
        studentName = view.findViewById(R.id.studentName);
        requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://prof-student-matching.herokuapp.com/students/2018248/";
        fetchData(url);

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


    private void fetchData(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    Student student;
                    if (jsonObject.get("status").toString().trim().equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject empObj = jsonArray.getJSONObject(i);
                            student = new Student();
                            student.setStudentName(empObj.getString("first_name"));
                            studentName.setText(student.getStudentName());
//                            employee.setName(empObj.getString("employee_name"));
//                            employee.setSalary(empObj.getInt("employee_salary"));
//                            employee.setAge((byte) empObj.getInt("employee_age"));

//                            employeeArrayList.add(employee);
                        }
//                        recyclerView.setAdapter(employeesAdapter);
                    }
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