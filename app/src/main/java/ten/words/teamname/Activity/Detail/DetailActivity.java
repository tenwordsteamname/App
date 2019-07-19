package ten.words.teamname.Activity.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ten.words.teamname.R;

public class DetailActivity extends AppCompatActivity {

    String title, date, location, img,content;
    int agree, disagree;

    ImageView back, background;
    TextView tv_agree, tv_disagree, tv_loc, tv_date, tv_title, tv_content;

    LinearLayout left, right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tv_agree = findViewById(R.id.detail_agree);
        tv_disagree = findViewById(R.id.detail_disagree);
        tv_loc = findViewById(R.id.detail_loc);
        tv_date = findViewById(R.id.detail_date);
        tv_title = findViewById(R.id.detail_title);
        tv_content = findViewById(R.id.detail_content);
        back = findViewById(R.id.detail_back);
        background = findViewById(R.id.detail_ll3);
        left = findViewById(R.id.detail_left);
        right = findViewById(R.id.right);


        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        location = intent.getStringExtra("location");
        agree = intent.getIntExtra("agree", 0);
        disagree = intent.getIntExtra("disagree", 0);
        img = intent.getStringExtra("img");
        content=intent.getStringExtra("content");

        tv_agree.setText(agree + "");
        tv_disagree.setText(disagree + "");
        tv_loc.setText(location);
        tv_date.setText(date);
        tv_title.setText(title);
        tv_content.setText(content);

        Glide.with(this).load(img).into(background);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
