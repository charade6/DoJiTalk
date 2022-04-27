import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class CreateDatabase {
      //데이터베이스 연결 변수 선언 (Declaring database connection variables)
      //데이터베이스 연결 (database connection)

   public static void main(String[] args) {
      String url = "jdbc:mysql://localhost:3306/";
      String id = "root";      
      String password = "1234";
		Connection con = null; 
		  try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  con=DriverManager.getConnection(url,id,password);  
			  Statement stmt = con.createStatement();
			  stmt.executeUpdate("create database dojidb;");
			  System.out.println("\"dojidb\" database가 생성되었습니다.");
			  stmt.executeUpdate("use dojidb;");
			  stmt.executeUpdate("create table member ("
		                 +"uname varchar(30),"
	       		         +"uemail varchar(30) not null,"
	       		         +"password varchar(30), "
	       		         +"primary key(uemail)"
	       		         + ");");
			  System.out.println("\"member\" Table이 생성되었습니다.");

			  
			  stmt.executeUpdate("INSERT INTO member VALUES('장지원','a','a');");
			  stmt.executeUpdate("INSERT INTO member VALUES('김도현','b','b');");
			  stmt.executeUpdate("INSERT INTO member VALUES('엄인섭','c','c');");
			  stmt.executeUpdate("INSERT INTO member VALUES('양승호','d','d');");
			  stmt.executeUpdate("INSERT INTO member VALUES('졸려유','asdf','asdf');");
			  stmt.executeUpdate("INSERT INTO member VALUES('헬로','asas','asas');");
			  stmt.executeUpdate("INSERT INTO member VALUES('오이','asd1','asd1');");
			  System.out.println("\"member\" 컬럼이 추가되었습니다.");


			  stmt.executeUpdate("create table friendList ("
	                     +"userEmail varchar(30),"
 		                 +"friendEmail varchar(30),"
	                     +"foreign key (userEmail) "
 		                 +"references member (uemail),"
	                     +"foreign key (friendEmail) "
 		                 +"references member (uemail)"
 		                 + ");");
			  System.out.println("\"FRIENDLIST\" Table이 생성되었습니다.");

			  
			  stmt.executeUpdate("insert into friendList values('a','b')");
			  stmt.executeUpdate("insert into friendList values('a','c')");
			  stmt.executeUpdate("insert into friendList values('a','d')");
			  stmt.executeUpdate("insert into friendList values('b','a')");
			  stmt.executeUpdate("insert into friendList values('b','c')");
			  stmt.executeUpdate("insert into friendList values('b','d')");
			  stmt.executeUpdate("insert into friendList values('c','a')");
			  stmt.executeUpdate("insert into friendList values('c','b')");
			  stmt.executeUpdate("insert into friendList values('c','d')");
			  stmt.executeUpdate("insert into friendList values('d','a')");
			  stmt.executeUpdate("insert into friendList values('d','b')");
			  stmt.executeUpdate("insert into friendList values('d','c')");
			  System.out.println("\"FRIENDLIST\" 컬럼이 추가되었습니다.");
			  
			  con.close();
		  }
		  catch(Exception e) {
			  System.out.println(e.getMessage());
			  e.printStackTrace();	
		  }
	}
}