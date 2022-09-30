package com.example.smarthome0807;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_join extends AppCompatActivity {

    private String url = "http://10.0.2.2:8081";
    private EditText join_id, join_pw, join_name, join_apt, join_adr, join_key;
    private Button join_yes;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //회원 정보 값 받아오기
        join_id = findViewById(R.id.join_id);
        join_pw = findViewById(R.id.join_pw);
        join_name = findViewById(R.id.join_name);
        join_apt = findViewById(R.id.join_apt);
        join_adr = findViewById(R.id.join_adr);
        join_key = findViewById(R.id.join_key);

        //회원가입 버튼 클릭 시 수행
        join_yes = findViewById(R.id.join_yes);
        join_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = join_id.getText().toString();
                String UserPw = join_pw.getText().toString();
                String UserName = join_name.getText().toString();
                String UserApt = join_apt.getText().toString();
                String UserAdr = join_adr.getText().toString();
                String UserKey = join_key.getText().toString();

                //한 칸이라도 입력 안했을 경우
                if
                (UserId.equals("") || UserPw.equals("") || UserName.equals("") || UserApt.equals("")|| UserAdr.equals("")|| UserKey.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity_join.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }//end of 한 칸 입력 방지

                Users users = new Users();

            }
        });

        //뒤로가기 버튼 - 화면 이동
        ImageButton imageButton = (ImageButton) findViewById(R.id.backBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_login.class);
                startActivity(intent);
            }
        });//end of 뒤로가기 버튼 onClick() 메소드
    }
}