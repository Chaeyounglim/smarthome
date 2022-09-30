package com.example.smarthome0807;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class activity_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //계정 생성 버튼 - 화면 이동
        Button joinMember = (Button) findViewById(R.id.join_qid);
        joinMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_join.class);
                startActivity(intent);
            }
        });

        //일단 임시로 만든 홈 넘어가는 onclick (로그인 : 회원 인증 버튼 누를 시 수행)
        Button loginMember = (Button) findViewById(R.id.login);
        //사용자 입력 텍스트 값
        String login_id = findViewById(R.id.login_id).toString();
        String login_pw = findViewById(R.id.login_pw).toString();

        //Users table에 저장된 id, password 값
        String user_id;
        String user_pw;

        loginMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                if(login_id.equals("baekkoji") && (login_pw.equals("1234"))){
                    //화면 이동 구문
                    startActivity(intent);
                }
            }
        });
    }
}