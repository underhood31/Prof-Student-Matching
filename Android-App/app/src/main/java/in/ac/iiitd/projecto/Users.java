package in.ac.iiitd.projecto;

import androidx.appcompat.app.AppCompatActivity;
import in.ac.iiitd.projecto.Model.UserDetails;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Users extends AppCompatActivity {
    ListView usersList ;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        //////////SASTA TAREEKA FOR TESTING//////////////
        //Currently working just for students for a particular project
        String nameOfAdvisor = "";
        ArrayList<String> listAdvisor = new ArrayList<>();
        HashSet<String> hs = new HashSet<>();
        if(getIntent().getStringExtra("advisorName")!=null){
            nameOfAdvisor = getIntent().getStringExtra("advisorName");
        }
        System.out.println("The Hashset data is as follows:");
        if(getIntent().getSerializableExtra("advisorSet")!=null){
            hs = (HashSet<String>) getIntent().getSerializableExtra("advisorSet");
        }
        if(getIntent().getStringExtra("HashSet_data").equals("all")){
            hs.add("swastikjain101");
            hs.add("manavjeetsingh31");
            hs.add("swastik18269");
            hs.add("manavjeet18295");
            hs.add("aanya18208");
//            String url = "https://projecto-7c78f-default-rtdb.firebaseio.com/registered.json";

        }

        if(hs.contains("Sambuddho Chakravarty")){
            hs.add("swastikjain101");
        }
        if(hs.contains("Mukulika Maity")){
            hs.add("manavjeetsingh31");
        }


        System.out.println(nameOfAdvisor+"balle balle");

        if(nameOfAdvisor.equals("Sambuddho Chakravarty")){
            nameOfAdvisor = "swastikjain101";
        }
        else if(nameOfAdvisor.equals("Mukulika Maity")){
            nameOfAdvisor = "manavjeetsingh31";
        }
        hs.add(nameOfAdvisor);
        /////////////////////////////////////////////////

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://projecto-7c78f-default-rtdb.firebaseio.com/registered.json";

        String finalNameOfAdvisor = nameOfAdvisor;
        HashSet<String> finalHs = hs;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                System.out.println("request succesful: "+ s);
                doOnSuccess(s, finalHs);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(Users.this, Chat.class));
            }
        });
    }

    public void doOnSuccess(String s, HashSet<String> hs){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator<String> it = hs.iterator();
            while(it.hasNext()){
                System.out.println("Hashset data: " + it.next());
            }

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                System.out.println(key);

                if(!key.equals(UserDetails.username) && hs.contains(key)) {
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }
}