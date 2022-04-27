package server.userdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UserDAO {

  private String driver = "com.mysql.cj.jdbc.Driver";

  private String jdbcurl = "jdbc:mysql://localhost:3306/dojidb?serverTimezone=Asia/Seoul";

  private Connection conn;

  private PreparedStatement pstmt;

  public String username = null;

  public void connect() {

    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(jdbcurl, "root", "1234");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void disconnect() {

    try {
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }
  // 회원가입
  public boolean insertDB(User user) {

    connect();
    String sql = "insert into member values(?,?,?)";
    
    boolean isInsert = false;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, user.getUname());
      pstmt.setString(2, user.getUemail());
      pstmt.setString(3, user.getPassword());
      pstmt.executeUpdate();
      
      isInsert = true;
 
    } catch (SQLException e) {
      isInsert = false;
    }
    disconnect();
    
    return isInsert;
    
  }
  // 로그인
  public String findUser(ArrayList<JTextField> userInfos) {

    connect();
    String sql = "select uname from member where uemail=? and password=?";	// 입력한 값이 테이블에 있는지 검사
    String uemail = userInfos.get(0).getText();
    String password = userInfos.get(1).getText();
    
    String uname = null;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, uemail);
      pstmt.setString(2, password);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        uname = rs.getString("uname");	// 해당하는 닉네임 받아옴
      }
      
      username = uname;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    disconnect();
    
    return username; 
  }

  // 친구리스트
  public ArrayList<String> friendList() {

    String uemail = findUserEmail();
    connect();
    ArrayList<String> friends = new ArrayList<String>();	// 친구목록을 저장할 배열생성
    // 유저이메일과 일치하는 친구를 친구목록테이블에서 닉네임을받아와서 이름순 정렬
    String sql =
        "select m.uname from member m, friendList f where m.uemail = f.friendEmail and f.userEmail= ? ORDER BY uname";
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, uemail);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        friends.add(rs.getString("uname"));	// 친구목록배열에 닉네임 추가
      }
    } catch (SQLException e) {
    }
    disconnect();
    return friends;	// 친구목록 리턴
  }
  // 닉네임 변경
  public void changeName(String changeName) {
	connect();
	String sql = "update member set uname = ? where ( uname = ?)";
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, changeName);
		pstmt.setString(2, username);
		int rs = pstmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	disconnect();
}
  // 검색
  public ArrayList<String> searchFriend(String search) {
	  ArrayList<String> array = new ArrayList<String>();	// 검색목록 배열생성
	  connect();
	  String sql = "select uemail from member where uemail like ?";
	  try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, search+"%");	// 검색어로 시작하는 이메일 검색
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			array.add(rs.getString("uemail"));	// 검색목록 배열에 추가
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	  disconnect();
	  return array;
  }
  
  
  // 친구추가
  public void addFriend(String friendemail) {
	  String myfriend = "false";	// 내친구? 기본 false
	  String uemail = findUserEmail();	// 내 이메일 가져옴
	  connect();
	  String sql = "select userEmail from friendlist where userEmail = ? and friendEmail = ?";	// 내 친구인지 검색
	  try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, uemail);
		pstmt.setString(2, friendemail);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			myfriend = "true";	// 내 친구일경우 true
	      }
		
		if (myfriend.equals("false") && uemail.equals(friendemail) == false) {	// 내 친구가 아니고 내이메일과 같은값이 아닐경우
			String sql2 = "insert into friendlist values (?,?);";	// 친구 추가
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, uemail);
			pstmt.setString(2, friendemail);
			int rs2 = pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "친구가 추가되었습니다.\n친구목록을 새로고침 하세요!");
		} else if (myfriend.equals("true")) {	// 내 친구일경우
			JOptionPane.showMessageDialog(null,  "이미 친구입니다.","ERROR", JOptionPane.ERROR_MESSAGE);
		} else if (uemail.compareTo(friendemail)==0) {	// 추가하려는 이메일이 내이메일일경우
			JOptionPane.showMessageDialog(null,  "자신을 친구추가할 수 없습니다.","ERROR", JOptionPane.ERROR_MESSAGE);
		} 
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  disconnect();
  }
  // 친구삭제
  public void deleteFriend(String friendname) {
	  String uemail = findUserEmail();	// 내 이메일 불러옴
	  String femail = null;
	  connect();
	    String sql = "select uemail from member where uname=?";	// 친구 닉네임에 해당하는 이메일 받아옴
	    String sql2 = "delete from friendlist where userEmail = ? and friendEmail = ?;"; // 친구테이블에서 친구이메일이 있는 컬럼 삭제
	    try {
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, friendname);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	    	  femail = rs.getString("uemail");
	      }
	      pstmt = conn.prepareStatement(sql2);
	      pstmt.setString(1, uemail);
	      pstmt.setString(2, femail);
	      int rs2 = pstmt.executeUpdate();
	      JOptionPane.showMessageDialog(null, "친구가 삭제되었습니다!");
	      
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    disconnect();
  }

  // 이메일 검색
  public String findUserEmail() {

    connect();
    String sql = "select uemail from member where uname=?";	// 내 이메일 검색
    String uemail = null;
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        uemail = rs.getString("uemail");
      }
    } catch (SQLException e) {
    }
    disconnect();
    return uemail;
  }
  // 회원탈퇴
  public void deleteUser() {
	  String uemail = findUserEmail();
	  connect();
	  String sql = "delete from friendlist where userEmail = ? or friendEmail = ?";	// 외래키 먼저 삭제
	  String sql2 = "delete from member where uname = ?";	// 회원탈퇴
	  
	  try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, uemail);
		pstmt.setString(2, uemail);
		int rs = pstmt.executeUpdate();
		
		pstmt = conn.prepareStatement(sql2);
		
		pstmt.setString(1, username);
		int rs2 = pstmt.executeUpdate();
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	  disconnect();
  }
}
