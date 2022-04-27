package client.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;
import util.ColorSet;

public class FindFriend extends JFrame {
	String searchFriend;
	Controller controller;
	public FindFriend() {
		controller = Controller.getInstance();
		
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(ColorSet.talkBackgroundColor);
		frame.setLayout(null);
		JLabel title = new JLabel("상대 이메일을 입력하세요");
		title.setBounds(20,20,220,20);
		title.setFont(new Font("맑은 고딕", Font.BOLD,18));
		frame.getContentPane().add(title);
		
		JTextField find = new JTextField();
		find.setBounds(20,60,240,30);
		frame.getContentPane().add(find);
		
		JButton search = new JButton("SEARCH");
		search.setForeground(Color.WHITE);
		search.setBackground(ColorSet.signUpButtonColor);
		search.setBounds(90,110,100,30);
		search.setFocusPainted(false);
		search.setBorder(null);
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchFriend = find.getText();
				
				if (searchFriend.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "검색어를 입력하세요!", "ERROR",JOptionPane.ERROR_MESSAGE);
				} else {
					frame.dispose();
					new SearchResult(searchFriend);
				}
			}
		});
		
		frame.getContentPane().add(search);
		
		frame.setTitle("친구검색");
		frame.setVisible(true);
		frame.setSize(300,200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
}
