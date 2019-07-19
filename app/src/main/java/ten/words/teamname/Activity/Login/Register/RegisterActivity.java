package ten.words.teamname.Activity.Login.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    Button done, confirm;
    EditText edt_id;
    EditText edt_pwd;
    EditText edt_nick;
    EditText edt_pwd_re;
    TextView finish;
    private FirebaseAuth mAuth;
    private boolean prevent_duplication = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String str_id, str_nick, str_pwd;
    Map<String, Object> datas;
    int bool_confirm = 0;
    Spinner spinner;
    String[] loc_array;
    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        done = findViewById(R.id.register_done);
        edt_id = findViewById(R.id.register_id);
        edt_pwd = findViewById(R.id.register_pwd);
        edt_nick = findViewById(R.id.register_nickname);
        mAuth = FirebaseAuth.getInstance();
        confirm = findViewById(R.id.register_confirm);
        edt_pwd_re = findViewById(R.id.register_pwd_re);
        finish = findViewById(R.id.register_finish);
        spinner = findViewById(R.id.register_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.loc_array, android.R.layout.simple_spinner_item);
        loc_array = getResources().getStringArray(R.array.loc_array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loc = loc_array[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        finish.setOnClickListener(view -> finish());

        datas = new HashMap<>();

        confirm.setOnClickListener(view -> {
            str_nick = edt_nick.getText().toString();
            if (edt_nick.getText().toString().equals("")) {
                Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                db.collection("Accounts").document(str_nick)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (!task.getResult().exists()) {//아이디가 없으면
                                    Toast.makeText(this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                    bool_confirm = 1;
                                } else {
                                    Toast.makeText(this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        done.setOnClickListener(view -> {
            str_id = edt_id.getText().toString();
            str_pwd = edt_pwd.getText().toString();

            if (str_id.equals("") || str_pwd.equals("") || edt_nick.getText().toString().equals("")) {
                Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
            } else if (str_pwd.length() < 8) {
                Toast.makeText(this, "8자 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_id).matches()) {
                Toast.makeText(this, "이메일 형식의 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                if (prevent_duplication) {
                } else {
                    if (bool_confirm == 1 && str_nick.equals(edt_nick.getText().toString())) {
                        if (!str_pwd.equals(edt_pwd_re.getText().toString())) {
                            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            prevent_duplication = true;
                            datas.put("nickname",str_nick);
                            datas.put("prefer_loc", loc);
                            mAuth.createUserWithEmailAndPassword(str_id, str_pwd)//회원가입
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            db.collection("Accounts").document(str_id)
                                                    .set(datas)
                                                    .addOnCompleteListener(task12 -> {
                                                        if (task12.isSuccessful()) {
                                                            Log.e("qwe", "put");
                                                        }
                                                    });
                                            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.putExtra("nickname",str_nick);
                                            intent.putExtra("id",str_id);
                                            intent.putExtra("prefer",loc);
                                            setResult(1234);
                                            finish();
                                        } else {
                                            Toast.makeText(this, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(task1 -> {
                                        Log.e("qwe", task1.getMessage());
                                        Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    });
                            prevent_duplication = false;
                        }
                    } else {
                        Toast.makeText(this, "닉네임을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
