package util;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class MainPanelButton extends JButton {
	
  public MainPanelButton(String buttonTitle) {

    setName(buttonTitle);
    setText(buttonTitle);
    setBackground(Color.WHITE);
    setFocusPainted(false);
    setFont(new Font("맑은 고딕", Font.BOLD, 18));
    
    setBorder(new LineBorder(Color.lightGray,1, true));
    
//    Border emptyBorder = BorderFactory.createEmptyBorder();
//    setBorder(emptyBorder);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {

    super.setBounds(x, y, width, height);
  }


}

