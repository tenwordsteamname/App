package ten.words.teamname.Activity.Login.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

import ten.words.teamname.Activity.Login.LoginActivity;
import ten.words.teamname.Activity.Main.MainActivity;
import ten.words.teamname.R;

public class RegisterActivity extends AppCompatActivity {

    Button done;
    EditText edt_id;
    EditText edt_pwd;
    EditText edt_nick;
    private FirebaseAuth mAuth;
    private boolean prevent_duplication = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String str_id, str_nick, str_pwd;
    Map<String, Object> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        done = findViewById(R.id.register_done);
        edt_id = findViewById(R.id.register_id);
        edt_pwd = findViewById(R.id.register_pwd);
        edt_nick = findViewById(R.id.register_nickname);
        mAuth = FirebaseAuth.getInstance();

        datas = new HashMap<>();

        done.setOnClickListener(view -> {
            str_id = edt_id.getText().toString();
            str_pwd = edt_pwd.getText().toString();
            str_nick = edt_nick.getText().toString();

            if (str_id.equals("") || str_pwd.equals("") || str_nick.equals("")) {
                Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
            } else if (str_pwd.length() < 8) {
                Toast.makeText(this, "8자 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_id).matches()) {
                Toast.makeText(this, "이메일 형식의 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                if (prevent_duplication) {
                } else {
                    prevent_duplication = true;

                    datas.put("email", str_id);

                    db.collection("Accounts").document(str_nick)
                            .get()
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Log.e("qwe","qwe");
                                    if(!task.getResult().exists()){//아이디가 없으면
                                        Log.e("qwe","qwe2");

                                        mAuth.createUserWithEmailAndPassword(str_id,str_pwd)//회원가입
                                                .addOnCompleteListener(task1 ->{
                                                    if(task1.isSuccessful()){
                                                        db.collection("Accounts").document(str_nick)
                                                                .set(datas)
                                                                .addOnCompleteListener(task12 -> {
                                                                    if(task12.isSuccessful()){
                                                                        Log.e("qwe","put");
                                                                    }
                                                                });
                                                        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }else{
                                                        Toast.makeText(this, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(task1 ->{
                                                    Log.e("qwe",task1.getMessage());
                                                    Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }
                            });


                    prevent_duplication = false;
                }
            }
        });
    }
}
