package in.ac.iiitd.projecto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import in.ac.iiitd.projecto.Adapter.ProjectAdapter;
import in.ac.iiitd.projecto.Model.ProjectItem;
import in.ac.iiitd.projecto.Model.UserDetails;

public class prof_profile extends Fragment {


    private ImageView profImage;
    private final int CAMERA_REQUEST=1;
    private final int PICK_IMAGE_REQUEST=2;
    private final int PICK_PDF_REQUEST=3;
    private static final String TAG = "ProfessorActivity";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private ArrayList<ProjectItem> profProjectArrayList;
    private ProjectAdapter projectAdapter;
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    TextView profName;
    private RequestQueue profRequestQueue;
    Button btnProfAddProject;
    Button btnChat;
    String profProjects = "";
    ArrayList<String> projectListArr = new ArrayList<>();
    TextView profContactTextView, profRoomTextView, profDesignationTextView, profLabTextView;
    private String contact, roomNo, lab, designation, res1,res2,res3, profEmail;
    private void saveImage(Bitmap imageBitmap) {
        StorageReference imagesRef = storageRef.child(currentUser.getEmail());


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.child("dp").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), "Unable to upload image", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public prof_profile() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profImage.setImageBitmap(imageBitmap);
            saveImage(imageBitmap);
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri currentUri = data.getData();
            profImage.setImageURI(currentUri);
            try {
                saveImage(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),currentUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.uploadImage:
                Toast.makeText(getActivity(), "Upload Image", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                return true;
            case R.id.captureImage:
                Toast.makeText(getActivity(), "Capture Image", Toast.LENGTH_SHORT).show();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 1);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Unable to capture image.", Toast.LENGTH_SHORT).show();

                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public ArrayList<String> changeProjectList(String str){
        System.out.println("BRUHHHHHH");
        System.out.println(str);
        str = str.replace("[","");
        str = str.replace("]","");
        String[] elements = str.split(",");
        List<String> list = Arrays.asList(elements);

        ArrayList<String> listOfString = new ArrayList<String>(list);

        return listOfString;
    }
    public String removeExtra(String str){
        str = str.replace("[","");
        str = str.replace("]","");
        str = str.split(",")[0];
        return str;
    }
    public void fillData(ProjectItem projectItem, JSONObject jsonObject1) throws JSONException {
        projectItem.setProjectTitle(jsonObject1.getString("title"));

        String advisors = jsonObject1.getString("advisor_id");
        advisors = removeExtra(advisors);
        projectItem.setProjectAdvisorName(advisors);
        System.out.println("The name of the advisor is: "+advisors);
//        setAdvisor.add(advisors);



        String tech_stack = jsonObject1.getString("tech_stack");
        tech_stack = removeExtra(tech_stack);
        projectItem.setProjectTechStack(tech_stack);

        projectItem.setProjectStatus(jsonObject1.getBoolean("alloc_stat"));
        projectItem.setProjectDescription(jsonObject1.getString("descr"));
        projectItem.setProjectTimeRequired(jsonObject1.getInt("time_req"));
        projectItem.setProjectRequiredStudents(jsonObject1.getInt("req_stu_no"));
        projectItem.setProjectId(jsonObject1.getInt("id"));

        profProjectArrayList.add(projectItem);
    }
    private void fetchData(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("The new response is : " + response);
                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = new JSONObject(response);
                    ProjectItem projectItem;
                    projectItem = new ProjectItem();
                    fillData(projectItem, jsonObject2);
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

        profRequestQueue.add(request); //Execution
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_prof_profile, container, false);
        profName = view.findViewById(R.id.prof_professor_name_text);
        btnChat = view.findViewById(R.id.prof_chats);
        profName.setText(currentUser.getDisplayName());
        btnProfAddProject = view.findViewById(R.id.prof_add_project_button);
        Bitmap bitmapImage= BitmapFactory.decodeResource(getResources(),R.drawable.peeyush_image);
        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//        profImage=(ImageView) view.findViewById(R.id.profImage);

        profContactTextView = view.findViewById(R.id.profContactTextView);
        profRoomTextView = view.findViewById(R.id.profRoomTextview);
        profDesignationTextView = view.findViewById(R.id.profDesignationTextView);
        profLabTextView = view.findViewById(R.id.profLabTextView);

//        String url = "https://prof-student-matching.herokuapp.com/prof/mukulika/";
        StringBuilder urlsb = new StringBuilder();

        urlsb.append("https://prof-student-matching.herokuapp.com/prof/");
//        urlsb.append(currentUser.getDisplayName());
        urlsb.append(currentUser.getEmail().split("@")[0]);

        urlsb.append("/");
        Log.d("TAG", "onCreateView: Link:"+urlsb.toString());
        String url = urlsb.toString();
        System.out.println("printing URL: " + url);

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                System.out.println("request successful: "+ s);
                doOnSuccess(s);
                projectListArr = changeProjectList(profProjects);
                for(int i=0;i<projectListArr.size();i++){
                    StringBuilder projectURL = new StringBuilder("https://prof-student-matching.herokuapp.com/project/");
                    projectURL.append(projectListArr.get(i));
                    System.out.println(projectListArr.get(i)+" is the project id.");
                    String projURL = projectURL.toString();
                    System.out.println("The project url is: "+projURL);
                    fetchData(projURL);
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);



//        registerForContextMenu(profImage);
        /*********************RecyclerView*********************************************/
        /******************************************************************************/
        this.recyclerView = view.findViewById(R.id.recyclerViewProfProject);
        profProjectArrayList = new ArrayList<>();
        projectAdapter =  new ProjectAdapter(profProjectArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        profRequestQueue = Volley.newRequestQueue(getContext());




        /******************************************************************************/
        String image_path = getDP();
        Toast.makeText(getActivity(), "image_path:"+image_path, Toast.LENGTH_SHORT).show();
//        if (image_path!=null){
//            LinearLayout placeholder= (LinearLayout) view.findViewById(R.id.profImagePlaceHolder);
//            placeholder.setVisibility(View.GONE);
//            profImage.setImageURI(Uri.parse(image_path));
//            profImage.setVisibility(View.VISIBLE);
//        }

        btnProfAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddProject addProject = new AddProject(profEmail,res1,res2,res3);
                fragmentTransaction.replace(R.id.layoutFragmentProfessor,addProject)
                        .addToBackStack(addProject.getClass().getName())
                        .commit();
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Users.class);
                intent.putExtra("HashSet_data", "all");
                startActivity(intent);
            }
        });


        return view;
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);
            System.out.println("inside success");

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                if(key.equals("room_no")){
                    profRoomTextView.setText(obj.get(key).toString());
                    roomNo=obj.get(key).toString();
                }

                else if(key.equals("des")){
                    profDesignationTextView.setText(obj.get(key).toString());
                    designation=obj.get(key).toString();
                }

                else if(key.equals("contact")){
                    profContactTextView.setText(obj.get(key).toString());
                    contact=obj.get(key).toString();
                }

                else if(key.equals("lab_name")){
                    profLabTextView.setText(obj.get(key).toString());
                    lab=obj.get(key).toString();
                }
                else if (key.equals("res_interest1")) {
                    res1=obj.get(key).toString();
                }
                else if (key.equals("res_interest2")) {
                    res2=obj.get(key).toString();
                }
                else if (key.equals("res_interest3")) {
                    res3=obj.get(key).toString();
                }
                else if (key.equals("email")) {
                    profEmail= obj.get(key).toString();
                }
                else if(key.equals("proj_list")){
                    profProjects = obj.get(key).toString();
                }
            }
            System.out.println("The project list is as follows: "+ profProjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDP() {
        StorageReference imagesRef = storageRef.child(currentUser.getEmail());
        File localFile;
        try {
            localFile = new File(getActivity().getFilesDir(),currentUser.getEmail()+"dp");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        imagesRef.child("dp").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(getActivity(), "Unable to download", Toast.LENGTH_SHORT).show();
            }
        });
        try{
            return localFile.getPath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}