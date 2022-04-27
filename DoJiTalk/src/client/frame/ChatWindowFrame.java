package client.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import controller.Controller;
import util.JFrameWindowClosingEventHandler;

@SuppressWarnings("serial")
public class ChatWindowFrame extends JFrame {

  private String frameName;

  public ChatWindowFrame(JPanel panel, String frameName) {

    Controller controller = Controller.getInstance();
    
    this.frameName = frameName;

    setTitle(controller.username + "의 Chatting");
    setBounds(1150, 220, 405, 605);
    Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
    getContentPane().add(panel);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    addWindowListener(new JFrameWindowClosingEventHandler(getFrameName()));
  }

  public String getFrameName() {

    return frameName;
  }

  public void setFrameName(String frameName) {

    this.frameName = frameName;
  }
}
