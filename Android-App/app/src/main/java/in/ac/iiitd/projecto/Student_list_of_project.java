package in.ac.iiitd.projecto;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ac.iiitd.projecto.Adapter.ProjectAdapter;
import in.ac.iiitd.projecto.Model.ProjectItem;


public class Student_list_of_project extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ProjectItem> projectItemArrayList;
    private ProjectAdapter projectAdapter;

    public Student_list_of_project() {
        // Required empty public constructor
    }


//    public static Student_list_of_project newInstance(String param1, String param2) {
//        Student_list_of_project fragment = new Student_list_of_project();
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
        View view= inflater.inflate(R.layout.fragment_student_list_of_project, container, false);

        this.recyclerView = view.findViewById(R.id.availableProjectRecyclerView);
        projectItemArrayList = new ArrayList<>();
        projectAdapter =  new ProjectAdapter(projectItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);



        ProjectItem projectItem = new ProjectItem();
        projectItem.setProjectTitle("mc project");
        projectItem.setProjectAdvisorName("mukulika");
        projectItemArrayList.add(projectItem);
        projectAdapter.notifyDataSetChanged();


        return view;
    }
}