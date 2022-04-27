package client.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controller.Controller;
import util.ColorSet;
import util.FriendButton;

public class SearchResult extends JFrame{
	private JFrame frame;
	private JPanel title;
	private JPanel list;
	private JScrollPane scroll;
	private JPanel close;
	private ArrayList<String> resultlist;
	private Controller controller = Controller.getInstance();
	
	public SearchResult(String femail) {
		resultlist = controller.searchFriend(femail);
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		title = new JPanel();
		
		JLabel t = new JLabel("검색결과");
		t.setFont(new Font("맑은 고딕", Font.BOLD,18));
		title.setBackground(ColorSet.talkBackgroundColor);
		title.add(t);
		
		list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		list.setBackground(Color.white);
		if (resultlist.size() != 0) {
			for (int i=0; i < resultlist.size(); i++) {
				list.add(new FriendButton(resultlist.get(i)));
			}
			
		} else {
			JLabel no =  new JLabel("검색 결과가 없습니다");
			no.setFont(new Font("맑은 고딕", Font.BOLD, 24));
			list.add(no);
		}
		
		
		scroll = new JScrollPane(list);
		scroll.setViewportView(list);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		close = new JPanel();
		close.setBackground(ColorSet.talkBackgroundColor);
		JButton c = new JButton("CLOSE");
		c.setBackground(ColorSet.signUpButtonColor);
		c.setForeground(Color.WHITE);
		c.setFocusPainted(false);
		c.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		close.add(c);
		
		
		frame.add(title,BorderLayout.NORTH);
		frame.add(scroll,BorderLayout.CENTER);
		frame.add(close,BorderLayout.SOUTH);
		
		frame.setTitle("검색결과");
		frame.setSize(300, 400);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
