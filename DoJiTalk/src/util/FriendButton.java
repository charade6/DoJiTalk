package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import controller.Controller;

@SuppressWarnings("serial")
public class FriendButton extends JButton{
	Controller controller = Controller.getInstance();
	LineBorder lineBorder = new LineBorder(Color.red);
	
	public FriendButton(String femail) {
		
		setName(femail);
		setText(femail);
//		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(Color.white);
//		setBorder(lineBorder);
		setFont(new Font("맑은 고딕",Font.BOLD,14));
		setOpaque(true);
		setMaximumSize(new Dimension(Integer.MAX_VALUE,this.getMinimumSize().height));
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				setBackground(ColorSet.signUpButtonColor);
				setForeground(Color.WHITE);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				setBackground(Color.white);
				setForeground(Color.BLACK);
			}
		});
		
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(null, "'"+femail+"'을 친구로 추가할까요?","친구 추가", JOptionPane.YES_NO_OPTION);
				if (r == JOptionPane.NO_OPTION || r == JOptionPane.CLOSED_OPTION) {
					
				} else {
					controller.addFriend(femail);
				}
			}
		});
	}
}
