package com.example.smarthome0807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment1 extends Fragment {
    ViewGroup viewGroup;

    private String url = "http://10.0.2.2:8081";
    private Button m_btnAlert; //팝업 알림
    //context 추가

    public RetrofitService retrofitService;
    //모듈화 클래스 사용 예정(nullPointerException)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        Context c = viewGroup.getContext();
        EditText editText = (EditText) viewGroup.findViewById(R.id.editTextNumber1); //창문 각도
        EditText editText2 = (EditText)viewGroup.findViewById(R.id.editTextNumber2); //난방 온도
        EditText editText3 = (EditText)viewGroup.findViewById(R.id.editTextNumber3); //냉방 온도
        EditText editText4 = (EditText) viewGroup.findViewById(R.id.editTextpw); //도어락 비밀번호

        //창문
        Button button1 = viewGroup.findViewById(R.id.button1);
        Button button2 = viewGroup.findViewById(R.id.button2);

        button1.setOnClickListener(v-> {
            if(v.getId() == R.id.button1) {
                windowController(viewGroup, editText);
            }
        });
        button2.setOnClickListener(v-> {
            if(v.getId() == R.id.button2) {
                windowOff(viewGroup);
            }
        });

        //난방기(히터)
        Button button3 = viewGroup.findViewById(R.id.button3);
        Button button4 = viewGroup.findViewById(R.id.button4);

        button3.setOnClickListener(v-> {
            if(v.getId() == R.id.button3) {
                heaterController(viewGroup, editText2);
            }
        });
        button4.setOnClickListener(v-> {
            if(v.getId() == R.id.button4) {
                heaterOff(viewGroup);
            }
        });

        //냉방기(에어컨)
        Button button5 = viewGroup.findViewById(R.id.button5);
        Button button6 = viewGroup.findViewById(R.id.button6);

        button5.setOnClickListener(v-> {
            if(v.getId() == R.id.button5) {
                acController(viewGroup, editText3);
            }
        });
        button6.setOnClickListener(v-> {
            if(v.getId() == R.id.button6) {
                acOff(viewGroup);
            }
        });

        //공기청정기
        Button button7 = viewGroup.findViewById(R.id.button7);
        Button button8 = viewGroup.findViewById(R.id.button8);

        button7.setOnClickListener(v-> {
            if(v.getId() == R.id.button7) {
                AirController(viewGroup);
            }
        });
        button8.setOnClickListener(v-> {
            if(v.getId() == R.id.button8) {
                AirOff(viewGroup);
            }
        });

        //환기청정기
        Button button9 = viewGroup.findViewById(R.id.button9);
        Button button10 = viewGroup.findViewById(R.id.button10);

        button9.setOnClickListener(v-> {
            if(v.getId() == R.id.button9) {
                fanController(viewGroup);
            }
        });
        button10.setOnClickListener(v-> {
            if(v.getId() == R.id.button10) {
                fanOff(viewGroup);
            }
        });

        //도어락
        Button button11 = viewGroup.findViewById(R.id.button11);
        Button button12 = viewGroup.findViewById(R.id.button12);

        button11.setOnClickListener(v->{
            if (v.getId() == R.id.button11) {
                doorController(viewGroup, editText4);
            }
        });
        button12.setOnClickListener(v -> {
            if (v.getId() == R.id.button12) {
                doorOff(viewGroup, editText4);
            }
        });

        return viewGroup;

    }

    //On 버튼 클릭 시 JSON 형식으로 데이터 전송
    void windowController(View v, EditText editText) {
        String text = editText.getText().toString().trim();
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        if(text.getBytes().length <= 0){
            String msg = "값을 입력해주십시오. (입력 범위 : 1°~180°)";
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }else{
            int num = Integer.parseInt(editText.getText().toString());
            if (num>0 && num<=180) { //값이 null이 아니거나 1° ~ 180° 사이의 값일 때만 수행
                try {
                    controlDataInfo.setAngle(num);
                    controlDataInfo.setWindowUp(1);
                    //모듈화 코드로 수정해야 함 (에러 없는 오류)
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    PostApi postApi = retrofit2.create(PostApi.class);
                    Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            System.out.println(response.message());

                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ResponseBody result;
                            result = response.body();
                            if(result != null){
                                Toast.makeText(getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                                editText.setText(null);

                            }else{
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                //다시 입력 메시지 출력
                String msg = "다시 입력해주십시오. (입력 범위 : 1°~90°)";
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        }
    }//end of windowController() Method
    void heaterController(View v, EditText editText) {
        String text = editText.getText().toString().trim();
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        if(text.getBytes().length <= 0){
            String msg = "값을 입력해주십시오. (입력 범위 : 25°C~)";
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }else{
            int num = Integer.parseInt(editText.getText().toString());
            if (num>=25) { //값이 null이 아니거나 10°C~30°C 사이의 값일 때만 수행
                try {
                    controlDataInfo.setHeater_temp(num);
                    controlDataInfo.setHeater(1);
                    //모듈화 코드로 수정해야 함 (에러 없는 오류)
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    PostApi postApi = retrofit2.create(PostApi.class);
                    Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            System.out.println(response.message());

                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), "냉난방기는 동시 운용 불가합니다", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ResponseBody result;
                            result = response.body();
                            if(result != null){
                                Toast.makeText(getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                                editText.setText(null);
                            }else{
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                //다시 입력 메시지 출력
                String msg = "다시 입력해주십시오. (입력 범위 : 25°C~)";
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        }
    }//end of heaterController() Method
    void acController(View v, EditText editText) {
        String text = editText.getText().toString().trim();
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        if(text.getBytes().length <= 0){
            String msg = "값을 입력해주십시오. (입력 범위 : 10°C~)";
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }else{
            int num = Integer.parseInt(editText.getText().toString());
            if (num >= 10) { //값이 null이 아니거나 10°C~ 사이의 값일 때만 수행
                try {
                    controlDataInfo.setAc_temp(num);
                    controlDataInfo.setAc(1);
                    //모듈화 코드로 수정해야 함 (에러 없는 오류)
                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    PostApi postApi = retrofit2.create(PostApi.class);
                    Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            System.out.println(response.message());

                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), "냉난방기는 동시 운용 불가합니다", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ResponseBody result;
                            result = response.body();
                            if(result != null){
                                Toast.makeText(getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                                editText.setText(null);
                            }else{
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                //다시 입력 메시지 출력
                String msg = "다시 입력해주십시오. (입력 범위 : 10°C~)";
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
            }
        }
    }//end of acController() Method
    void AirController(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setAirCleaner(1);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of AirController() Method 공기
    void fanController(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setAirOut(1);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of fanController() Method 환기
    void doorController(View v, EditText editText) {
        //Onclick Method (JSON 데이터 송신)
        String text = editText.getText().toString().trim();
        String num2 = editText.getText().toString();
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        if (text.getBytes().length <= 0) {
            String msg = "값을 입력해주십시오.";
            Toast.makeText(v.getContext(), msg, Toast.LENGTH_LONG).show();
        } else {
            try {
                controlDataInfo.setDoor_passwd(num2);
                controlDataInfo.setDoor(1);

                //모듈화 코드로 수정해야 함 (에러 없는 오류)
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PostApi postApi = retrofit2.create(PostApi.class);
                Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);

                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(response.message());

                        if (!response.isSuccessful()) {
                            editText.setText(String.valueOf(response.code()));
                            Toast.makeText(v.getContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        ResponseBody result;
                        result = response.body();
                        if (result != null) {
                            Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                            editText.setText(null);

                        } else {
                            Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//end of doorController() Method

    //Off 버튼 클릭 시 JSON 형식으로 데이터 전송
    void windowOff(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setAngle(0);
            controlDataInfo.setWindowUp(0);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of windowOff() Method
    void heaterOff(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setHeater(0);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of heaterOff() Method
    void acOff(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setAc(0);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of acOff() Method
    void AirOff(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setAirCleaner(0);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of AirOff() Method 공기
    void fanOff(View v) {
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        try {
            controlDataInfo.setAirOut(0);
            //모듈화 코드로 수정해야 함 (에러 없는 오류)
            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PostApi postApi = retrofit2.create(PostApi.class);
            Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println(response.message());

                    if (!response.isSuccessful()) {
                        Toast.makeText(getContext(), "원격 제어 요청 실패하였습니다", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResponseBody result;
                    result = response.body();
                    if(result != null){
                        Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of fanOff() Method 환기
    void doorOff(View v, EditText editText) {
        String text = editText.getText().toString().trim();
        String num2 = editText.getText().toString();
        ControlDataInfo controlDataInfo = new ControlDataInfo();

        if (text.getBytes().length <= 0) {
            String msg = "값을 입력해주십시오.";
            Toast.makeText(v.getContext(), msg, Toast.LENGTH_LONG).show();
        } else {
            try {
                controlDataInfo.setDoor_passwd(num2);
                controlDataInfo.setDoor(0);
                //모듈화 코드로 수정해야 함 (에러 없는 오류)
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PostApi postApi = retrofit2.create(PostApi.class);
                Call<ResponseBody> call1 = postApi.getControlResult(controlDataInfo);
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(response.message());

                        if (!response.isSuccessful()) {
                            editText.setText(String.valueOf(response.code()));
                            Toast.makeText(v.getContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        ResponseBody result;
                        result = response.body();
                        if (result != null) {
                            Toast.makeText(v.getContext(), "원격 제어 요청 완료되었습니다", Toast.LENGTH_LONG).show();
                            editText.setText(null);

                        } else {
                            Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//end of doorOff() Method

}