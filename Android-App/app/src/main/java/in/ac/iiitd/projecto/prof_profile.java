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

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class prof_profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView profImage;
    private final int CAMERA_REQUEST=1;
    private final int PICK_IMAGE_REQUEST=2;
    private final int PICK_PDF_REQUEST=3;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    TextView profName;
    ImageButton btnProfAddProject;
    Button btnChat;

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
        profImage=(ImageView) view.findViewById(R.id.profImage);

        registerForContextMenu(profImage);

        String image_path = getDP();
        Toast.makeText(getActivity(), "image_path:"+image_path, Toast.LENGTH_SHORT).show();
        if (image_path!=null){
            LinearLayout placeholder= (LinearLayout) view.findViewById(R.id.profImagePlaceHolder);
            placeholder.setVisibility(View.GONE);
            profImage.setImageURI(Uri.parse(image_path));
            profImage.setVisibility(View.VISIBLE);
        }

        btnProfAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Student_list_of_project studentListOfProject = new Student_list_of_project();
                AddProject addProject = new AddProject();
                fragmentTransaction.replace(R.id.layoutFragmentProfessor,addProject)
                        .addToBackStack(addProject.getClass().getName())
                        .commit();
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Users.class);
                startActivity(intent);
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
}