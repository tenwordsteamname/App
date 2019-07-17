package ten.words.teamname.Activity.Login.Register;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ten.words.teamname.R;

public class RegisterActivity extends AppCompatActivity {

    Button done;
    EditText edt_id;
    EditText edt_pwd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        done = findViewById(R.id.register_done);
        edt_id=findViewById(R.id.register_id);
        edt_pwd=findViewById(R.id.register_pwd);
        mAuth = FirebaseAuth.getInstance();

        done.setOnClickListener(view -> {
            String str_id = edt_id.getText().toString();
            String str_pwd = edt_pwd.getText().toString();
            mAuth.createUserWithEmailAndPassword(str_id,str_pwd)
                    .addOnCompleteListener(task ->{
                        if(task.isSuccessful()){
                            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(this, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(task ->{
                        Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
