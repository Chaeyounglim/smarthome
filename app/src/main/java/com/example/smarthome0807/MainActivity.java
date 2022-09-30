package com.example.smarthome0807;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //하단 메뉴바 선언
        bottomNavigationView = findViewById(R.id.btm_navi);
        //프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        //뷰 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_frame, fragment1).commitAllowingStateLoss();
        //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너 추가
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_frame, fragment1).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.tab2: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_frame, fragment2).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_frame, fragment3).commitAllowingStateLoss();
                        return true;
                    }

                    default:
                        return false;

                }
            }
        });

    }//end of onCreate() Method

}