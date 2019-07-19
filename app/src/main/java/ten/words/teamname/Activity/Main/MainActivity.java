package ten.words.teamname.Activity.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ten.words.teamname.Activity.Login.LoginActivity;
import ten.words.teamname.Data.Main_Data;
import ten.words.teamname.R;

public class MainActivity extends AppCompatActivity {

    Main_RecyclerAdpater adapter;
    Drawer_RecyclerAdapter dadapter;
    ArrayList<Main_Data> items;
    ArrayList<String> ditems;
    RecyclerView rcv;
    TextView topic;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView drcv;
    String db_title, db_date, db_location;
    int db_agree, db_disagree;
    boolean db_i_like;
    LinearLayout LL;
    ImageView opendrawer;
    DrawerLayout DL;
    String myemail;
    RecyclerView rcv2;
    Main2_RecyclerAdapter adapter2;
    TextView rightnow,loc;
    String db_email,db_content;
    LinearLayout LL1,LL2;
    TextView logout;
    SharedPreferences mprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.main_rcv);
        topic = findViewById(R.id.main_tv_topic);
        drcv = findViewById(R.id.drawer_rcv);
        LL = findViewById(R.id.drawer_background);
        opendrawer = findViewById(R.id.main_drawer_open);
        DL = findViewById(R.id.drawer_layout);
        rcv2 = findViewById(R.id.main_rcvup);
        loc=findViewById(R.id.main_loc);
        rightnow = findViewById(R.id.main_rightnow);
        LL1=findViewById(R.id.main_btn1);//안함
        LL2=findViewById(R.id.main_btn2);//안함
        logout=findViewById(R.id.main_logout);


        items = new ArrayList<>();
        ditems = new ArrayList<>();

        String names[] = {"서울특별시", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구",
                "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
        ditems.addAll(Arrays.asList(names));

        adapter = new Main_RecyclerAdpater(items);
        adapter2 = new Main2_RecyclerAdapter(items);
        dadapter = new Drawer_RecyclerAdapter(ditems);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        rcv2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv2.setAdapter(adapter2);

        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(adapter);

        drcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        drcv.setAdapter(dadapter);

        mprefs = getSharedPreferences("Profile", MODE_PRIVATE);
        String a = mprefs.getString("S_pref", "서울특별시");
        myemail = mprefs.getString("S_id", "");

        Listload(a);

        opendrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DL.openDrawer(Gravity.LEFT);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mprefs.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Listload(final String loc) {
        Log.e("loc", loc);
        this.loc.setText(loc);

        this.rightnow.setText("지금, "+loc+"의 개선사항");

        items.clear();
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();

        if (loc.equals("서울특별시")) {
            db.collection("Locations")
                    .orderBy("agree")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                JSONObject jsonObject = new JSONObject(document.getData());
                                db_i_like = false;
                                try {
                                    db_title = jsonObject.getString("title");
                                    db_date = jsonObject.getString("date");
                                    db_location = jsonObject.getString("location");
                                    db_agree = jsonObject.getInt("agree");
                                    db_disagree = jsonObject.getInt("disagree");
                                    db_email=jsonObject.getString("img");
                                    db_content =jsonObject.getString("content");

                                    Main_Data a = new Main_Data(db_title, db_date, db_location, db_agree, db_disagree, db_i_like,db_email,db_content);
                                    items.add(a);
                                    adapter.notifyDataSetChanged();
                                    adapter2.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } else {
            db.collection("Locations")
                    .orderBy("agree")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                JSONObject jsonObject = new JSONObject(document.getData());
                                db_i_like = false;
                                try {
                                    Log.e("qwe","qwe1");
                                    if (jsonObject.getString("location").equals(loc)) {
                                        Log.e("qwe","qwe2");
                                        db_title = jsonObject.getString("title");
                                        db_date = jsonObject.getString("date");
                                        db_location = jsonObject.getString("location");
                                        db_agree = jsonObject.getInt("agree");
                                        db_disagree = jsonObject.getInt("disagree");
                                        db_email=jsonObject.getString("img");
                                        db_content =jsonObject.getString("content");
                                        Main_Data a = new Main_Data(db_title, db_date, db_location, db_agree, db_disagree, db_i_like,db_email,db_content);
                                        items.add(a);
                                        adapter.notifyDataSetChanged();
                                        adapter2.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }
}
