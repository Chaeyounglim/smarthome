package baekkoji.smarthomeserver.dto;

import lombok.Data;

import java.sql.*;

@Data
public class ControlData {

    private int angle=0;
    private int ac_temp=0;
    private int heater_temp=0;
    private int windowUp =2;
    private int heater =2;
    private int ac =2;
    private int airCleaner =2;
    private int airOut =2;

    String url = "jdbc:mysql://database-baekkoji.ccp9kadfy1fx.ap-northeast-2.rds.amazonaws.com:3306/smarthome";
    String userName = "admin";
    String password = "baekkoji";

    public String setControlData() throws SQLException {
        String result = "" ;

        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        PreparedStatement pstmt = null;

        String sql = "update ControlData set ";

        if(windowUp==1 || windowUp==0) { //window 제어
            sql+= "windowUp=" + windowUp + ", angle=" + angle;
        }
        if(heater==1 || heater==0) {
            sql+= "heater=" + heater + ", heater_temp=" + heater_temp;
        }
        if(ac==1 || ac==0) {
            sql += "ac=" + ac + ", ac_temp=" + ac_temp;
        }
        if(airCleaner==1 || airCleaner==0) {
            sql += "airCleaner=" + airCleaner;
        }
        if(airOut==1 || airOut==0) {
            sql += "airOut=" + airOut;
        }

        sql += " where id=?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "chayoung"); //id 임의로

        pstmt.executeUpdate();

        statement.close();
        connection.close();

        result = "ok";
        return result;
    }
}
