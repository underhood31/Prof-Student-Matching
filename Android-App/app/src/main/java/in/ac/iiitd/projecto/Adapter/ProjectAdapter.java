package in.ac.iiitd.projecto.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import in.ac.iiitd.projecto.Model.ProjectItem;
import in.ac.iiitd.projecto.ProjectView;
import in.ac.iiitd.projecto.R;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private ArrayList<ProjectItem> projectItemArrayList;

    public ProjectAdapter(ArrayList<ProjectItem> list){
        projectItemArrayList = list;
    }

    @NonNull
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProjectItem item = projectItemArrayList.get(position);

        holder.projectTitle.setText(item.getProjectTitle());
        holder.projectAdvisorName.setText(item.getProjectAdvisorName());

    }

    @Override
    public int getItemCount() {
        return projectItemArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView projectTitle, projectAdvisorName;

        public ViewHolder(View itemView)
        {
            super(itemView);

            projectTitle = itemView.findViewById(R.id.projectTitle);
            projectAdvisorName = itemView.findViewById(R.id.projectAdvisorName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("clicked: ", "" + projectItemArrayList.get(getAdapterPosition()));
            Activity activity = (AppCompatActivity) view.getContext();
            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.layoutFragment, new ProjectView())
//                    .addToBackStack(null)
//                    .commit();


        }
    }
}
