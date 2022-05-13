package in.ac.iiitd.projecto;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ProjectView extends Fragment {

    private TextView projectTitle, advisorName, projectDescriptionTextView, timeRequiredTextView, techStackTextView, requiredStudentsTextView, allocationStatusTextView;
    private ImageView projectImage;
    private Button projectApplyBtn, projectWithdrawBtn, chatWithProf;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String projectID;
    FirebaseUser currentUser = mAuth.getCurrentUser();
    public ProjectView() {
        // Required empty public constructor
    }


    public static ProjectView newInstance(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        ProjectView fragment = new ProjectView();
        Bundle args = new Bundle();

        args.putString("title", s1);
        args.putString("advisorName", s2);
        args.putString("description", s3);
        args.putString("timeRequired", s4);
        args.putString("techStack", s5);
        args.putString("requiredStudents", s6);
        args.putString("allocationStatus", s7);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_view, container, false);

        projectTitle = view.findViewById(R.id.projectTitle);
        projectImage=(ImageView)view.findViewById(R.id.projectImageView);
        Bitmap bitmapImage= BitmapFactory.decodeResource(view.getContext().getResources(),R.drawable.sample_project_1);
        int nh = (int) ( bitmapImage.getHeight() * (500.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage,400 , nh, true);
        projectImage.setImageBitmap(scaled);
        advisorName = view.findViewById(R.id.advisorName);
        projectDescriptionTextView = view.findViewById(R.id.projectDescriptionTextView);
        timeRequiredTextView = view.findViewById(R.id.timeRequiredTextView);
        techStackTextView = view.findViewById(R.id.techStackTextView);
        timeRequiredTextView = view.findViewById(R.id.timeRequiredTextView);
        requiredStudentsTextView = view.findViewById(R.id.requiredStudentsTextView);
        allocationStatusTextView = view.findViewById(R.id.allocationStatusTextView);
        projectApplyBtn = view.findViewById(R.id.projectApplyBtn);
        projectWithdrawBtn = view.findViewById(R.id.projectWithdrawBtn);
        projectTitle.setText(getArguments().getString("title"));
        advisorName.setText(getArguments().getString("advisorName"));
        projectDescriptionTextView.setText(getArguments().getString("description"));
        timeRequiredTextView.setText(getArguments().getString("timeRequired"));
        techStackTextView.setText(getArguments().getString("techStack"));
        requiredStudentsTextView.setText(getArguments().getString("requiredStudents"));
        allocationStatusTextView.setText(getArguments().getString("allocationStatus"));
        projectID=getArguments().getString("requiredStudents");
        chatWithProf=view.findViewById(R.id.chatProf);
        String nameOfAdvisor = advisorName.getText().toString();
        chatWithProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Users.class);
                intent.putExtra("advisorName", nameOfAdvisor);
                startActivity(intent);
            }
        });

        Toast.makeText(getContext(), "project ID:"+projectID, Toast.LENGTH_SHORT).show();

        projectApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                postNewComment(getContext());
                String emailID=currentUser.getEmail().split("@")[0];
                /***************************Trying POST command******************************/
                /****************************************************************************/
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    String URL = "http://prof-student-matching.herokuapp.com/students/"+emailID+"/add_proj_applied/";
                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("proj_applied",projectID);
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(getContext(),"You have successfully applied", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }

                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                /****************************************************************************/
            }
        });
        projectWithdrawBtn.setOnClickListener(new View.OnClickListener() {
            String emailID=currentUser.getEmail().split("@")[0];
            @Override
            public void onClick(View view) {
//                postNewComment(getContext());
                /***************************Trying POST command******************************/
                /****************************************************************************/
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    String URL = "http://prof-student-matching.herokuapp.com/students/"+emailID+"/withdraw_proj/";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("proj_applied",projectID);
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(getContext(),"Application Withdrawn!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }

                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                /****************************************************************************/
            }
        });


        return view;
    }

/***************BACK UP POST IN CASE USME KUCH HAGGA******************/
//    public static void postNewComment(Context context){
////        mPostCommentResponse.requestStarted();
//        RequestQueue queue = Volley.newRequestQueue(context);
//        StringRequest sr = new StringRequest(Request.Method.POST,"https://prof-student-matching.herokuapp.com/students/swastik18269/update_fields/", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println("BHAI SAB CHAL GAYA");
////                mPostCommentResponse.requestCompleted();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("BHAI ERROR AA GAYA");
////                mPostCommentResponse.requestEndedWithError(error);
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
////                params.put("user",userAccount.getUsername());
////                params.put("pass",userAccount.getPassword());
////                params.put("comment", Uri.encode(comment));
////                params.put("comment_post_ID",String.valueOf(postId));
////                params.put("blogId",String.valueOf(blogId));
//                params.put("sec_name","demo");
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        queue.add(sr);
//    }
//
//    public interface PostCommentResponseListener {
//        public void requestStarted();
//        public void requestCompleted();
//        public void requestEndedWithError(VolleyError error);
//    }
/*********************************************************************/

}