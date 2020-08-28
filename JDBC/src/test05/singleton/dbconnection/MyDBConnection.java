package test05.singleton.dbconnection;

import java.sql.*;

/*
	외부 DB는 네트워크로 연결해야 하기 때문에 부하가 크다.
	그런 DB에 접속하기 위해 매번 똑같은 Connection 객체를 생성하여 접속하는 것은 비효율적이다. 
	작업에 따라 나눠진 각각에서 객체 생성하지 않고 하나의 인스턴스만을 생성하는 싱글톤 패턴을 활용할 수 있다.
 */

public class MyDBConnection {
	
	// --> static 변수 : 첫번째로 호출(작동) <-- //
	// 리턴해줄 Connection 객체
	private static Connection conn = null;
	
	// --> static 초기화 블럭 : 두번째로 호출(작동) <-- //
	static {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "MYORAUSER", "cclass");
			conn.setAutoCommit(false);  // 수동 commit으로 전환
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}// end of static {}
	
	// --> 기본 생성자 : 다섯번째로 호출(작동) <-- //
	private MyDBConnection() { }  // 기본 생성자를 막음
	
	// --> getConn() 메소드 생성 : 세번째로 호출(작동) <-- //
	public static Connection getConn() {
		return conn;
	}
	
	
	////////////////////////////////////////////////////////////
	// == Connection conn 객체 자원 반납하기 == //
	public static void closeConnection() {
		try {
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of closeConnection()

}
