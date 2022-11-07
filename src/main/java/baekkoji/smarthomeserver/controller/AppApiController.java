package baekkoji.smarthomeserver.controller;

import baekkoji.smarthomeserver.dto.ControlData;
import baekkoji.smarthomeserver.dto.HomeDataInfo;
import baekkoji.smarthomeserver.dto.Users;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class AppApiController {

    public static Users person = new Users(); //회원정보 클래스 생성
    static HomeDataInfo home = new HomeDataInfo();

    /* 회원 가입 및 로그인 */
    @PostMapping ("/users/idCheck") // 앱 -> 서버 : 회원가입 아이디 중복체크
    public @ResponseBody boolean checkId(@RequestBody String id) throws SQLException{
        //person.setId(id);
        boolean result = person.checkId(id);
        return result;
    }

    @PostMapping ("/users/signUp") // 앱 -> 서버 : 회원가입 요청
    public @ResponseBody String newUser(@RequestBody Map<String,String> users) throws SQLException{
        String result = person.newSignupUser(users);
        return result;
    }

    @PostMapping ("/users/login") // 앱 -> 서버 : 로그인
    public @ResponseBody boolean login(@RequestBody Map<String,String> data) throws SQLException{
        return person.login(data.get("id"),data.get("passwd"));
    }


    /* 회원 정보 수정 및 탈퇴 */
    @PostMapping("/users/getUsers") // 앱 -> 서버 : 회원정보 수정 전 참조
    public @ResponseBody Map<String,String> sendData(@RequestBody String id) throws SQLException{
        Map<String, String> Userdata = new HashMap<>();
        Userdata = person.getUserData(id);
        return Userdata; // 수정 페이지에 회원 정보 출력하기 위함.
    }

    @PostMapping("/users/setUsers") // 앱 -> 서버 : 회원정보 수정
    public @ResponseBody boolean setUser(@RequestBody Map<String,String> users) throws SQLException{
        boolean result = person.setUserData(users); // 앱에서 받은 데이터로 DB 수정
        return result; //앱에게 수정 여부 반환
    }

    @PostMapping("/users/setControlDevices") // 앱 -> 서버 : 스마트홈 기기 삭제 및 추가
    public @ResponseBody boolean editControlDevices(@RequestBody Map<String,String> datas) throws SQLException {
        boolean result = person.editControlDevices(datas);
        return result;
    }

    @PostMapping("/users/WithdrawUser") // 앱 -> 서버 : 회원 탈퇴
    public @ResponseBody boolean WithdrawUser(@RequestBody Map<String,String> data) throws SQLException{
        return person.WithdrawUserData(data.get("id"),data.get("passwd")); //앱에게 탈퇴 여부 반환
    }


    /* 주요 페이지 기능 */
    @GetMapping("/home/getDatas") // 앱 -> 서버 : 홈데이터 요청
    public @ResponseBody Map<String,String> getHomeData(@RequestBody String id) throws SQLException {
        Map<String, String> HomeData = new HashMap<>();
        HomeData = home.getHomeDataInfo(id);
        home.toString();
        return HomeData; // 앱에 홈 데이터 반환
    }

    @PostMapping("/main/getDatas") // 앱 -> 서버 : 메인 페이지 정보
    public @ResponseBody Map<String,String> getMainData(@RequestBody String id) throws SQLException {
        Map<String, String> MainData = new HashMap<>();
        MainData = home.getMainDataInfo(id);
        return MainData;
    }

    @PostMapping("/home/Control") // 앱 -> 서버 : 기기 제어 요청
    public @ResponseBody String ControlHome(@RequestBody ControlData controlData) throws SQLException{
        String result = controlData.setControlData(); // 앱에서 받은 데이터 DB에 저장.
        return result; //앱에 응답여부 반환 (ok)
    }

}