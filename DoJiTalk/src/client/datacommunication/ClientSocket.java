package client.datacommunication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import client.frame.ChatWindowPanel;
import server.datacommunication.Message;

public class ClientSocket {

  static Socket socket;

  public void startClient() {
    
    Thread thread = new Thread(()->{
      try {
        socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 5002));
        System.out.println("연결 요청");

      } catch (IOException e) {
        System.out.println("서버 통신 안됨");
        e.printStackTrace();
      }
      receive();
    });
    
    thread.start();

  }

  public void stopClient() {

    try {
      if (socket != null && !socket.isClosed()) {
        socket.close();
      }
    } catch (IOException e) {
    }
  }


  public void receive() {

    while (true) {

      byte[] recvBuffer = new byte[1024];

      InputStream inputStream;
      try {
        inputStream = socket.getInputStream();
        int readByteCount = inputStream.read(recvBuffer);
        if (readByteCount == -1) {
          throw new IOException();
        }
        Message message = toMessage(recvBuffer, Message.class);
        ChatWindowPanel.displayComment(message);
      } catch (IOException e) {
//        e.printStackTrace();
      }
    }
  }

  private Message toMessage(byte[] recvBuffer, Class<Message> class1) {

    Object obj = null;
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(recvBuffer);
      ObjectInputStream ois = new ObjectInputStream(bis);
      obj = ois.readObject();
    } catch (Exception e) {
    }
    return class1.cast(obj);
  }


  public void send(Message messageInfo) {

    Thread thread = new Thread(()->{

      byte[] bytes = null;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      
      try {
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(messageInfo); 
        oos.flush(); 
        oos.close();
        bos.close();
        bytes = bos.toByteArray();
      } catch (IOException e) {
      }
     
      try {
        byte[] data = bytes;
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
        System.out.println("서버로 보내기 완료");
      } catch (IOException e) {
        System.out.println("서버로 통신 안됨");
        e.printStackTrace();
      }
    });
    thread.start();
  }
}
