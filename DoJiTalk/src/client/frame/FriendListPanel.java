package client.frame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import controller.Controller;
import server.datacommunication.Message;
import server.userdb.UserDAO;
import util.ColorSet;
import util.UseImageFile;
import util.UserProfileButton;

@SuppressWarnings("serial")
public class FriendListPanel extends JPanel {

  private ArrayList<String> friends; // 친구들 이름 저장

  private ArrayList<ImageIcon> friendIcons = new ArrayList<ImageIcon>(); // 친구들 프로필 이미지 저장

  public static ArrayList<JButton> friendButtons = new ArrayList<JButton>(); // 친구들 정보 버튼 저장

  private final int FRIEND_PROFILE_IMG_MAX = 9;

  private final int FRIEND_PROFILE_IMG_MIN = 0;
  
  JButton delButton;

  public FriendListPanel() {
	setBackground(ColorSet.talkBackgroundColor);
	Controller controller = Controller.getInstance();
    
    friends = controller.friendList();
    int friendNum = friends.size();

    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    
    for (int i = 0; i < friendNum; i++) {
    	String fname = friends.get(i); 
        Random rand = new Random();
        int randomNum =
            rand.nextInt((FRIEND_PROFILE_IMG_MAX - FRIEND_PROFILE_IMG_MIN) + FRIEND_PROFILE_IMG_MIN)
                + 1;
        Image img = UseImageFile.getImage("resources\\friendProfile//profile" + randomNum + ".png");
        ImageIcon imageIcon = new ImageIcon(img);
        UserProfileButton userprofileButton = new UserProfileButton(imageIcon);
        userprofileButton.setText(fname);
        add(userprofileButton);
        userprofileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, userprofileButton.getMinimumSize().height));
        friendIcons.add(imageIcon);
        userprofileButton.putClientProperty("page", i);
        userprofileButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
              int idx = (Integer) ((JButton) e.getSource()).getClientProperty("page");
              String messageType = "text";
              Message message = new Message(controller.username, controller.username + "님이 입장하였습니다.",
                  LocalTime.now(), messageType, friends.get(idx));
              ChatWindowPanel c = new ChatWindowPanel(friendIcons.get(idx), friends.get(idx));
              new ChatWindowFrame(c, friends.get(idx));
              IndexPanel.chatPanelName.add(c);
              
              Controller controller = Controller.getInstance();
              controller.clientSocket.send(message);
            }
          });
        
        delButton = new JButton("삭제");
        delButton.setMaximumSize(new Dimension(65,10));
        delButton.setBackground(ColorSet.signUpButtonColor);
        delButton.setForeground(Color.WHITE);
        delButton.setFont(new Font("맑은 고딕",Font.BOLD,12));
        delButton.setFocusPainted(false);
        add(delButton);
        delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,  "친구("+fname+")를 정말 삭제하겠습니까?", "친구삭제",  JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
					
				} else {
					controller.delFriend(fname);
				}
				
			}
		});
        
        add(Box.createHorizontalStrut(5));
      }
  }
}
