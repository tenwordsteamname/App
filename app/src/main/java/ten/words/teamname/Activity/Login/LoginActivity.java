package ten.words.teamname.Activity.Login;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ten.words.teamname.Activity.Login.Register.RegisterActivity;
import ten.words.teamname.Activity.Main.MainActivity;
import ten.words.teamname.R;

public class LoginActivity extends AppCompatActivity {

    Button login, reg;
    EditText edt_id, edt_pwd;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String str_id,str_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_login);
        reg = findViewById(R.id.login_regis);
        edt_id = findViewById(R.id.login_id);
        edt_pwd = findViewById(R.id.login_pwd);

        mAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(view -> {
            str_id = edt_id.getText().toString();
            str_pwd = edt_pwd.getText().toString();
            //로그인
            if(str_id.equals("")||str_pwd.equals("")) {
                Toast.makeText(this, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else{

                mAuth.signInWithEmailAndPassword(str_id, str_pwd)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                db.collection("nicknames").document(str_id)
                                        .get()
                                        .addOnCompleteListener(tasks ->{
                                            if(tasks.isSuccessful()){
                                                String nickname = tasks.getResult().getString("nickname");
                                                String id = tasks.getResult().getId();
                                                SaveData(nickname,id);
                                            }
                                        })
                                        .addOnFailureListener(tasks ->{

                                        });
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(task -> {
                            Toast.makeText(this, "이메일와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        });

            }
        });
        reg.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, 1234);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1234) {
                String intent_nick = data.getStringExtra("nickname");
                String intent_id = data.getStringExtra("id");
                SaveData(intent_nick,intent_id);
            }
        }
    }
    void SaveData(String nick,String id){
        SharedPreferences mprefs = getSharedPreferences("Profile",MODE_PRIVATE);
        SharedPreferences.Editor editor = mprefs.edit();
        editor.putString("S_Nick",nick);
        editor.putString("S_id",id);
        editor.apply();
    }
}
