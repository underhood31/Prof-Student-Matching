package in.ac.iiitd.projecto.Adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ac.iiitd.projecto.Model.StudentItem;
import in.ac.iiitd.projecto.R;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private ArrayList<StudentItem> studentItemArrayList;
    public StudentAdapter(ArrayList<StudentItem> list){studentItemArrayList=list;}


    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        StudentItem item =studentItemArrayList.get(position);
        Resources res=holder.itemView.getContext().getResources();
        holder.nameText.setText(item.getName());
        holder.streamText.setText(item.getStream());
        holder.degreeText.setText(item.getDegree());
        Bitmap bitmapImage= BitmapFactory.decodeResource(res,R.drawable.peeyush_image);
        int nh = (int) ( bitmapImage.getHeight() * (250.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 250, nh, true);
        holder.studentImage.setImageBitmap(scaled);
    }

    @Override
    public int getItemCount() {
        return studentItemArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView nameText, streamText, degreeText;
        private ImageView studentImage;

        public ViewHolder(View itemView){
            super(itemView);
            nameText=itemView.findViewById(R.id.prof_studentName_text);
            streamText=itemView.findViewById(R.id.prof_stream_text);
            degreeText=itemView.findViewById(R.id.prof_degree_text);
            studentImage=itemView.findViewById(R.id.prof_student_image);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            String name=studentItemArrayList.get(position).getName();
            Toast.makeText(view.getContext(),name,Toast.LENGTH_SHORT).show();
        }
    }
}
