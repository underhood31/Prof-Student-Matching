package in.ac.iiitd.projecto;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.ac.iiitd.projecto.Adapter.ProjectAdapter;
import in.ac.iiitd.projecto.Model.ProjectItem;
import in.ac.iiitd.projecto.Model.Student;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class student_profile_fragment extends Fragment {

    private Button studentUploadResumeBtn, studentDownloadResumeBtn, studentApplyProjectBtn ;
    private TextView studentName, studentStream, studentDegree;
    private ImageView studentImage;
    private RequestQueue requestQueue, studentRequestQueue;
    private static final String TAG = "VolleyActivity";
    private ArrayList<ProjectItem> studentProjectArrayList;
    private ProjectAdapter projectAdapter;
    private RecyclerView recyclerView;
    private final int CAMERA_REQUEST=1;
    private final int PICK_IMAGE_REQUEST=2;
    private final int PICK_PDF_REQUEST=3;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseUser currentUser = mAuth.getCurrentUser();


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

    private void saveResume(Uri pdf) {
        Log.d(TAG, "saveResume: "+pdf.toString());
        LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.show(getActivity().getSupportFragmentManager(),"Loading...");

        StorageReference pdfRef = storageRef.child(currentUser.getEmail()).child("resume");


        // Here we are uploading the pdf in firebase storage with the name of current time
        pdfRef.putFile(pdf).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return pdfRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // After uploading is done it progress
                    // dialog box will be dismissed
                    loadingFragment.dismiss();
                    Uri uri = task.getResult();
                    String myurl;
                    myurl = uri.toString();
                    Toast.makeText(getActivity(), "Resume Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    loadingFragment.dismiss();
                    Toast.makeText(getActivity(), "Resume Uploaded Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            studentImage.setImageBitmap(imageBitmap);
            saveImage(imageBitmap);
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri currentUri = data.getData();
            studentImage.setImageURI(currentUri);
            try {
                saveImage(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),currentUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == PICK_PDF_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Uri pdfUri = data.getData();
            saveResume(pdfUri);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.imageupload_menu, menu);
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
        Bitmap bitmapImage= BitmapFactory.decodeResource(getResources(),R.drawable.peeyush_image);
        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
        studentImage=(ImageView)view.findViewById(R.id.studentImage);

        registerForContextMenu(studentImage);

        String image_path = getDP();
        Toast.makeText(getActivity(), "image_path:"+image_path, Toast.LENGTH_SHORT).show();
        if (image_path!=null){
            LinearLayout placeholder= (LinearLayout) view.findViewById(R.id.imagePlaceHolder);
            placeholder.setVisibility(View.GONE);
            studentImage.setImageURI(Uri.parse(image_path));
            studentImage.setVisibility(View.VISIBLE);
        }


        /**********Viewing and Uploading Resume***********************************/
        /****************************************************************************/
        studentUploadResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_REQUEST);



            }
        });
        //Log.i("Alert Box",student.getStudentResumeLink());
        studentDownloadResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String resumePath = getResume();
                Toast.makeText(getActivity(), "resume path:"+resumePath, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onClick: "+resumePath);
                File file = new File(resumePath);
                Uri uri = FileProvider.getUriForFile(getContext(),"in.ac.iiitd.projecto.provider",file);

                Intent myIntent=new Intent(Intent.ACTION_VIEW);
                myIntent.setDataAndType(uri,    "application/pdf");
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getActivity().startActivity(myIntent);
            }
        });
        /****************************************************************************/

        studentName = view.findViewById(R.id.studentName);
        studentStream = view.findViewById(R.id.studentStream);
        studentDegree = view.findViewById(R.id.studentDegree);

        /*********************RecyclerView*********************************************/
        /******************************************************************************/
        this.recyclerView = view.findViewById(R.id.recyclerViewProject);
        studentProjectArrayList = new ArrayList<>();
        projectAdapter =  new ProjectAdapter(studentProjectArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        studentRequestQueue = Volley.newRequestQueue(getContext());

        /******************************************************************************/
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
                Toast.makeText(getActivity(), "Unable to download", Toast.LENGTH_SHORT).show();
            }
        });
        try{
            return localFile.getPath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getResume() {
        StorageReference resumeRef = storageRef.child(currentUser.getEmail()).child("resume");

//        return resumeRef.getDownloadUrl().toString();
        File localFile;
        try {
            localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),"resume.pdf");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.show(getActivity().getSupportFragmentManager(),"Loading...");

        resumeRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                loadingFragment.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), "Unable to download", Toast.LENGTH_SHORT).show();
            }
        });

        try{
            return localFile.getPath();
        }
        catch (Exception e){
            return null;
        }
    }



    public String removeExtra(String str){
        str = str.replace("{","");
        str = str.replace(":","");
        str = str.replace("1","");
        str = str.replace("}","");
        return str;
    }
    public void fillData(ProjectItem projectItem, JSONObject jsonObject1) throws JSONException {
        projectItem.setProjectTitle(jsonObject1.getString("title"));

        String advisors = jsonObject1.getString("advisor_id");
        advisors = removeExtra(advisors);
        projectItem.setProjectAdvisorName(advisors);

        String tech_stack = jsonObject1.getString("tech_stack");
        tech_stack = removeExtra(tech_stack);
        projectItem.setProjectTechStack(tech_stack);

        projectItem.setProjectStatus(jsonObject1.getBoolean("alloc_stat"));
        projectItem.setProjectDescription(jsonObject1.getString("descr"));
        projectItem.setProjectTimeRequired(jsonObject1.getInt("time_req"));
        projectItem.setProjectRequiredStudents(jsonObject1.getInt("req_stu_no"));
        projectItem.setProjectId(jsonObject1.getInt("id"));

        studentProjectArrayList.add(projectItem);
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

        studentRequestQueue.add(request); //Execution
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
                    String str = jsonObject.getString("proj_applied");
                    str = str.replace("{","");
                    str = str.replace("0","");
                    str = str.replace("}","");
                    str = str.replace(":","");
                    String[] arr = str.split(",");
                    ArrayList<String> arrayList = new ArrayList<>();
                    for(int i=0;i<arr.length;i++){
                        arrayList.add(arr[i]);
                    }
                    student.setArrayList(arrayList);
//                    System.out.println("MAAHI VE : " + student.getArrayList().get(0)+" : "+student.getArrayList().get(1));
//                    fetchData("https://prof-student-matching.herokuapp.com/project/1/");

                    for(int i = 0;i<student.getArrayList().size();i++){
                        StringBuilder projectURL = new StringBuilder("https://prof-student-matching.herokuapp.com/project/");
                        projectURL.append(student.getArrayList().get(i));
                        String projURL = projectURL.toString();
                        fetchData(projURL);
                    }


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