/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.EventQueue;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author Seok17
 */
public class RoomDAO {

    private static RoomDAO roomDao = new RoomDAO();
    private static MariaDBConnector db = MariaDBConnector.getInstance();

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs; //쿼리의 결과가 들어가는 자료
    RoomInfo roomInfo;
    RoomList roomList;

    private RoomDAO() {
    }

    public static RoomDAO getInstance() {
        return roomDao;
    }

    public int insertRoom(String roomName) {
        con = db.getConnection();
        int RoomOk;
        try {
            int i = 0;
            String sql = "INSERT INTO ROOM VALUES ('" + i + "','" + roomName + "')";
            pstmt = con.prepareStatement(sql);
            RoomOk = pstmt.executeUpdate();
        } catch (Exception e) {
            return RoomOk = 0;
        }
        return RoomOk;
    }

    public RoomList getRoomInfo() throws Exception {
        con = db.getConnection();
        String sql = "SELECT * FROM ROOM"; //룸리스트 전체를 받아오는 쿼리
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery(); //쿼리를 실행하고 나온 결과가 rs에 들어감
            roomList = new RoomList(); //룸리스트 모델 생성

            while (rs.next()) {
                roomInfo = new RoomInfo(); //한 룸의 정보 모델 생성
                roomInfo.setroomId(rs.getInt("roomid")); //한줄의 룸 아이디 셋
                roomInfo.setRoomName(rs.getString("roomname")); //한줄의 룸 이름 셋
                roomList.addRoomlist(roomInfo); //룸 인포를 룸리스트에 추가
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roomList;
    }

    public int getRoomLen() {
        con = db.getConnection();
        int result = 0;
        try {
            String sql = "SELECT count(*) cnt FROM ROOM";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int cnt = rs.getInt("cnt");
                result = cnt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
