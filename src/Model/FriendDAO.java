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

public class FriendDAO {

    private static FriendDAO friendDao = new FriendDAO();
    private static MariaDBConnector db = MariaDBConnector.getInstance();

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    FriendInfo friend;
    FriendList friendList;

    private FriendDAO() {
    }

    public static FriendDAO getInstance() {
        return friendDao;
    }

    //친구 리스트 뽑는 함수 인자(나) 유저아이디와 친구아이디, 친구이름 리스트 반환
    public FriendList getFriend(String me) throws Exception {
        con = db.getConnection();
        String sql = "SELECT f.user_id, f.friend_id, c.NAME FROM gegeo_db.FRIEND f INNER JOIN gegeo_db.CUSTOMER c ON f.friend_id  = c.id  WHERE f.user_id ='" + me + "'";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            friendList = new FriendList(); //친구리스트 모델 생성

            while (rs.next()) {
                friend = new FriendInfo();
                friend.setUser(rs.getString("user_id")); //유저 아이디
                friend.setId(rs.getString("friend_id")); //친구 아이디
                friend.setName(rs.getString("NAME")); //친구 이름
                friendList.addFriendlist(friend); //리스트에 추가
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendList;
    }

    //친구 추가함수 인자(나, 추가할 친구) (이미 있는 친구 추가를 막는 기능?)(고건 컨트롤러가 해야하구연)
    public int InsertFriend(String me, String you) {
        con = db.getConnection();
        int FriendOK; //친구 추가 되었는지 숫자로 전달(디비에서 값 머오는지 확인 필요)
        try {
            String sql = "INSERT INTO FRIEND(user_id, friend_id) VALUES ('" + me + "','" + you + "')";
            pstmt = con.prepareStatement(sql);
            FriendOK = pstmt.executeUpdate();
        } catch (Exception e) {
            return FriendOK = 0;
        }
        return FriendOK;

    }

    //친구 삭제함수(나, 삭제할 친구)
    public int deleteFriend(String me, String you) {
        con = db.getConnection();
        int BanOK; //너 밴
        try {
            String sql = "DELETE FROM gegeo_db.FRIEND WHERE user_id = '" + me + "'AND friend_id'" + you + "'";
            pstmt = con.prepareStatement(sql);
            BanOK = pstmt.executeUpdate();
        } catch (Exception e) {
            return BanOK = 0;
        }
        return BanOK;
    }
}

}
