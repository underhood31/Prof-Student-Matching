package in.ac.iiitd.projecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        /**********Viewing and Uploading Resume***********************************/
        /****************************************************************************/
        studentUploadResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setTitle("Upload Resume");
                alert.setMessage("Please paste a Google drive link of your resume here");
                final EditText input=new EditText(getContext());
                alert.setView(input);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        student.setStudentResumeLink(input.getText().toString());

                        Log.i("Alert Box",student.getStudentResumeLink());
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),"No link added for Resume",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();

            }
        });
        //Log.i("Alert Box",student.getStudentResumeLink());
        studentDownloadResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(Intent.ACTION_VIEW);
//                String link="https://drive.google.com/file/d/1VwGdr4KFGsTpeQzVCPUWlbOo5wcsRHQj/view?usp=sharing";
//                myIntent.setData(Uri.parse(student.getStudentResumeLink()));
                myIntent.setType("*/*");
                myIntent.setData(Uri.parse(student.getStudentResumeLink()));
                //myIntent.setData(Uri.parse(link));
                //myIntent.setDataAndType(Uri.parse(link), "/");
                Intent intent = Intent.createChooser(myIntent, "Choose an application to open with:");
                getContext().startActivity(intent);
            }
        });
        /****************************************************************************/

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
                Student_list_of_project studentListOfProject = new Student_list_of_project();
                fragmentTransaction.replace(R.id.layoutFragment,studentListOfProject)
                        .addToBackStack(studentListOfProject.getClass().getName())
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
                    student.setStudentResumeLink(jsonObject.getString("resume_link"));


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