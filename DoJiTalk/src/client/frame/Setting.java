package client.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.Controller;
import util.ColorSet;

public class Setting extends JFrame {
	
	Controller controller;
	public Setting() {
		controller = Controller.getInstance();
		
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(ColorSet.talkBackgroundColor);
		frame.setLayout(null);
		JLabel title = new JLabel("변경할 닉네임을 입력하세요");
		title.setBounds(20,20,250,20);
		title.setFont(new Font("맑은 고딕", Font.BOLD,18));
		frame.getContentPane().add(title);

		JTextField find = new JTextField();
		find.setBounds(20,60,175,30);
		frame.getContentPane().add(find);
		
		JButton search = new JButton("CHANGE");
		search.setForeground(Color.WHITE);
		search.setBackground(ColorSet.signUpButtonColor);
		search.setBounds(205,60,60,30);
		search.setFocusPainted(false);
		search.setBorder(null);
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(find.getText().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "빈칸입니다","ERROR",JOptionPane.ERROR_MESSAGE);
				} else {
					controller.cname(find.getText());
					JOptionPane.showMessageDialog(null, "닉네임이 '"+find.getText()+"' (으)로 변경 되었습니다.\n로그아웃 됩니다.");
					controller.clientSocket.stopClient();
					frame.dispose();
					MainPanel.frame.dispose();
					new MainFrame();
				}
				find.setText("");
			}
		});
		
		frame.getContentPane().add(search);
		
		JButton deluser = new JButton("Delete Account");
		deluser.setForeground(Color.WHITE);
		deluser.setBackground(ColorSet.signUpButtonColor);
		deluser.setBounds(80,110,120,30);
		deluser.setFocusPainted(false);
		deluser.setBorder(null);
		deluser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int result = JOptionPane.showConfirmDialog(null,  "계정을 정말 삭제하겠습니까?", "회원탈퇴",  JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
					
				} else {
					controller.delUser();
				}
			}
		});
		frame.getContentPane().add(deluser);
		
		frame.setTitle("설정");
		frame.setVisible(true);
		frame.setSize(300,200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
}
