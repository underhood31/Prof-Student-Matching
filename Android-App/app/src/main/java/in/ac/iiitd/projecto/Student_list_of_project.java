package in.ac.iiitd.projecto;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import in.ac.iiitd.projecto.Adapter.ProjectAdapter;
import in.ac.iiitd.projecto.Model.ProjectItem;


public class Student_list_of_project extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ProjectItem> projectItemArrayList;
    private ProjectAdapter projectAdapter;
    private RequestQueue requestQueue;
    private TextView sortText,filterText,searchText;

    private static final String TAG = "VolleyProjectActivity";

    public Student_list_of_project() {
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
        View view= inflater.inflate(R.layout.fragment_student_list_of_project, container, false);

        this.recyclerView = view.findViewById(R.id.availableProjectRecyclerView);
        projectItemArrayList = new ArrayList<>();
        projectAdapter =  new ProjectAdapter(projectItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://prof-student-matching.herokuapp.com/project/";
        fetchData(url);

        return view;
    }
    public String removeExtra(String str){
        str = str.replace("{","");
        str = str.replace(":","");
        str = str.replace("1","");
        str = str.replace("}","");
        return str;
    }
    public void fillData(ProjectItem projectItem, JSONObject jsonObject1) throws JSONException {
        Log.i("DEBUG","Json Object");

        projectItem.setProjectTitle(jsonObject1.getString("title"));

        String advisors = jsonObject1.getString("advisor_id");
        advisors = removeExtra(advisors);
        projectItem.setProjectAdvisorName(advisors);

        String tech_stack = jsonObject1.getString("tech_stack");
//        tech_stack = removeExtra(tech_stack);
        projectItem.setProjectTechStack(tech_stack);

        projectItem.setProjectStatus(jsonObject1.getBoolean("alloc_stat"));
        projectItem.setProjectDescription(jsonObject1.getString("descr"));
        projectItem.setProjectTimeRequired(jsonObject1.getInt("time_req"));
        projectItem.setProjectRequiredStudents(jsonObject1.getInt("req_stu_no"));
        projectItem.setProjectId(jsonObject1.getInt("id"));
        projectItemArrayList.add(projectItem);
    }

    private void fetchData(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("The response is : " + response);
                JSONObject jsonObject = null;
                try {
                    JSONArray jsonArray = new JSONArray(response);
//                    jsonObject = new JSONObject(response);
                    ProjectItem projectItem;
                    for( int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        projectItem = new ProjectItem();
                        fillData(projectItem, jsonObject1);
                    }
                    projectAdapter.notifyDataSetChanged();

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