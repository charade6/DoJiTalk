package controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import client.datacommunication.ClientSocket;
import client.frame.ErrorMessagePanel;
import client.frame.FindFriend;
import client.frame.IndexPanel;
import client.frame.MainFrame;
import client.frame.MainPanel;
import server.userdb.User;
import server.userdb.UserDAO;

public class Controller {

  private static Controller singleton = new Controller();

  public String username = null;
  public String useremail = null;

  public ClientSocket clientSocket;

  UserDAO userDAO;


  private Controller() {

    clientSocket = new ClientSocket();

    userDAO = new UserDAO();

  }

  public static Controller getInstance() {

    return singleton;
  }
  // 회원 가입
  public void insertDB(User user) {

    boolean isInsert = userDAO.insertDB(user);

    if (isInsert) {
      MainPanel mainPanel = new MainPanel(MainPanel.frame);
      MainPanel.frame.change(mainPanel);
      JOptionPane.showMessageDialog(mainPanel, "회원가입 성공!!!", "회원가입", JOptionPane.WARNING_MESSAGE);
    } else {
      ErrorMessagePanel errorPanel = new ErrorMessagePanel("회원가입");
      MainPanel.frame.change(errorPanel);
    }

  }

  // 로그인 관련
  public void findUser(ArrayList<JTextField> userInfos) {

   username = userDAO.findUser(userInfos);

    if (username != null) {
      IndexPanel indexPanel = new IndexPanel();
      MainPanel.frame.change(indexPanel);
      clientSocket.startClient();
      JOptionPane.showMessageDialog(indexPanel, "로그인 성공!!!", "로그인", JOptionPane.WARNING_MESSAGE);
    } else if (username == null) {
      ErrorMessagePanel err = new ErrorMessagePanel("로그인");
      MainPanel.frame.change(err);
    }
  }

  // 친구목록
  public ArrayList<String> friendList() {

    ArrayList<String> friends = new ArrayList<String>();
    friends = userDAO.friendList();

    return friends;
  }
  // 닉네임변경
  public void cname(String name) {
	userDAO.changeName(name);
}
  public ArrayList<String> searchFriend(String find) {
	  ArrayList<String> array = new ArrayList<String>();
	  array = userDAO.searchFriend(find);
	  
	  return array;
  }
  
  // 친구추가
  public void addFriend(String friend) {
	  userDAO.addFriend(friend);
  }
  // 친구삭제
  public void delFriend(String friend) {
	  userDAO.deleteFriend(friend);
  }
  // 회원탈퇴
  public void delUser() {
	  userDAO.deleteUser();
	  JOptionPane.showMessageDialog(null, "회원탈퇴가 완료되었습니다.");
	  System.exit(0);
  }
}
