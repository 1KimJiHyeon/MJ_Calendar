package com.example.mjcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText register_name;
    private EditText register_email;
    private EditText register_pass;
    private EditText register_repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);
        mAuth = FirebaseAuth.getInstance();

        register_name = (EditText) findViewById(R.id.register_name);
        register_email = (EditText) findViewById(R.id.register_email);
        register_pass = (EditText) findViewById(R.id.register_pass);
        register_repass = (EditText) findViewById(R.id.register_repass);

        ImageView register_ima = (ImageView) findViewById(R.id.register_ima);

       Button register = (Button) findViewById(R.id.register);

       ImageButton delete_name = (ImageButton) findViewById(R.id.delete_name);
       ImageButton delete_email = (ImageButton) findViewById(R.id.delete_email);
       ImageButton delete_pass = (ImageButton) findViewById(R.id.delete_pass);
       ImageButton delete_repass = (ImageButton) findViewById(R.id.delete_repass);

        delete_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_name.setText("");
            }
        });
        delete_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_email.setText("");
            }
        });
        delete_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_pass.setText("");
            }
        });
        delete_repass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_repass.setText("");
            }
        });
    }
    public void onClick (View  v){
        String email = register_email.getText().toString();
        String password = register_pass.getText().toString();
        String passwordCheck = register_repass.getText().toString();
        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) { //세 개의 입력칸이 모두 값이 입력될때
            if (password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입 성공");
                                    Intent intent = new Intent(getApplication(), Login_Main.class);
                                    startActivity(intent);
                                } else {
                                    if (task.getException() != null) { //에러 발생시
                                        startToast("이메일 혹은 비밀번호가 틀렸습니다.");
                                    }
                                }
                            }
                        });
            } else {
                startToast("비밀번호가 일치하지 않습니다.");
            }
        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }
}

