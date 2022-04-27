package client.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import client.datacommunication.ClientSocket;
import client.frame.FindFriend;
import controller.Controller;
import enums.CommonWord;
import server.datacommunication.Message;
import util.CommonPanel;
import util.UseImageFile;
import util.UserProfileButton;

@SuppressWarnings("serial")
public class IndexPanel extends CommonPanel {
  private final int FRIEND_PROFILE_IMG_MAX = 9;
  private final int FRIEND_PROFILE_IMG_MIN = 0;
	
	
  private JLabel jLabel;
  Random rand = new Random();
  int randomNum = rand.nextInt((FRIEND_PROFILE_IMG_MAX - FRIEND_PROFILE_IMG_MIN) + FRIEND_PROFILE_IMG_MIN) + 1;
  Image img = UseImageFile.getImage("resources\\friendProfile//profile" + randomNum + ".png");

  public static UserProfileButton userProfileButton;

  public static ArrayList<ChatWindowPanel> chatPanelName = new ArrayList<ChatWindowPanel>();

  FriendListPanel jpanel;
  JScrollPane scroller;
  
  Controller controller;
  
  MainPanel mainPanel = new MainPanel(MainPanel.frame);
  
  public IndexPanel() {
    controller = Controller.getInstance();

    searchFriends();
    setting();
    friendListRefresh();
    
    meanMyProfileTitle(CommonWord.MYPROFILE.getText());
    meanMyProfile();
    
    meanFriendProfileTitle(CommonWord.FRIEND.getText());
    showFriendList();
    
  }
  private void setting() {
		ImageIcon ic = new ImageIcon("resources\\setting-ico.png");
		JButton st = new JButton(ic);
		st.setBorderPainted(false);
//		st.setContentAreaFilled(false);
		st.setBackground(null);
		st.setFocusPainted(false);
		st.setBounds(290, 10, 32, 32);
		add(st);
		
		st.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting setting = new Setting();
			}
		});
	  }
  
  
  private void friendListRefresh() {
	ImageIcon ic = new ImageIcon("resources\\refresh-ico.png");
	JButton rf = new JButton(ic);
	rf.setBorderPainted(false);
//	rf.setContentAreaFilled(false);
	rf.setBackground(null);
	rf.setFocusPainted(false);
	rf.setBounds(320, 215, 32, 32);
	add(rf);
	
	rf.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			IndexPanel.this.removeAll();
			MainPanel.frame.change(new IndexPanel());
			IndexPanel.this.revalidate();
		}
	});
  }
  
  private void searchFriends() {
	  ImageIcon ic = new ImageIcon("resources\\search-ico.png");
	  JButton sf = new JButton(ic);
	  sf.setBorderPainted(false);
	  sf.setBackground(null);
	  sf.setFocusPainted(false);
	  sf.setBounds(340, 10, 32, 32);
	  add(sf);
	  
	  sf.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			FindFriend finder = new FindFriend();
			
		}
	});
  }

  private void meanMyProfileTitle(String text) {

    jLabel = new JLabel(text);
    jLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    jLabel.setBounds(30, 80, 200, 30);
    add(jLabel);
  }

  private void meanMyProfile() {

    ImageIcon imageIcon = new ImageIcon(img);
    userProfileButton = new UserProfileButton(imageIcon);
    userProfileButton.setText(controller.username);
    userProfileButton.setBounds(30, 120, 325, 80);
    add(userProfileButton);
    userProfileButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        if (userProfileButton.getText().contains("대화 중..")) {
          // 작동x
        } else {
        	userProfileButton.setText(userProfileButton.getText() + "       대화 중..");
            String messageType = "text";
            Message message = new Message(controller.username, controller.username + "님이 입장하였습니다.",
                LocalTime.now(), messageType, controller.username);
            ChatWindowPanel c = new ChatWindowPanel(imageIcon, controller.username);
            new ChatWindowFrame(c, controller.username);
            chatPanelName.add(c);
            
            Controller controller = Controller.getInstance();
            controller.clientSocket.send(message);
        }
      }
    });
  }

  private void meanFriendProfileTitle(String text) {

    jLabel = new JLabel(text);
    jLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    jLabel.setBounds(30, 220, 200, 30);
    add(jLabel);
  }

  private void showFriendList() {

    jpanel = new FriendListPanel();
    scroller = new JScrollPane(jpanel);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroller.setBounds(30, 250, 325, 300);
    add(scroller);
  }

  public void paint(Graphics g) {

    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    Line2D lin = new Line2D.Float(30, 210, 350, 210);
    g2.draw(lin);
  }
}
