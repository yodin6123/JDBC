package test01.jdbc;

import java.sql.*;
import java.util.Scanner;

public class DQL_select {

	public static void main(String[] args) {
		
		Connection conn = null;
		// Connection conn은 오라클 데이터베이스 서버와 연결을 맺어주는 객체이다.
		
		PreparedStatement pstmt = null;
		// Connection conn(특정 오라클서버)에 전송할 SQL문(편지)을 전달할 객체(우편배달부)이다.
		
		ResultSet rs = null;
		// ResultSet rs은 select된 결과물이 저장되는 객체이다.
		
		Scanner sc = new Scanner(System.in);
		
		try {
			// >>> 1. 오라클 드라이버 로딩 <<<
			/*
				=== OracleDriver(오라클 드라이버)의 역할 ===
				1) OracleDriver를 메모리에 로딩시켜준다.
				2) OracleDriver 객체를 생성해준다.
				3) OracleDriver 객체를 DriverManager에 등록시켜준다.
				   --> DriverManager는 여러 드라이버들을 Vector(List 계열)에 저장하여 관리해주는 클래스이다.
			*/
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// >>> 2. 어떤 오라클 서버에 연결할지를 결정 <<<
			System.out.print("▷ 연결할 오라클 서버의 IP 주소: ");
			String ip = sc.nextLine();
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe", "HR", "cclass"); // @127.0.0.1: 로컬 호스트(자신의 IP)
						
			// >>> 3. SQL문(예)편지) 작성 <<<
			String sql = " select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') AS WRITEDAY "
					   + " from jdbc_tbl_memo "
					   + " order by no desc ";
			// SQL문을 마치는 ;(세미콜론)은 붙이면 안된다.
			// SQL문 기준으로 enter가 필요한 부분에 공백을 주어 문자열 결합 시 띄어쓰기에 맞는 구분을 확실하게 주어야 한다.
			
			// >>> 4. 연결할 오라클서버(conn)에 SQL문(편지)을 전달할 PreparedStatement 객체(우편배달부) 생성 <<<
			pstmt = conn.prepareStatement(sql);
			
			// >>> 5. PreparedStatement 객체(우편배달부) pstmst가 작성된 SQL문(편지)을 오라클서버에 보내서 실행 <<<
			rs = pstmt.executeQuery();
			// 실행할 SQL문이 select문일 때 사용
			// pstmt.executeQuery(); 을 실행하면 select 되어진 결과물을 가져오는데 
			// 그 타입은 ResultSet 으로 가져온다.
			/*
				=== 인터페이스 ResultSet 의 주요한 메소드 ===
				1. next()  : select 되어진 결과물에서 커서를 다음으로 옮겨주는 것		리턴타입은 boolean
							 *커서: 테이블의 행을 읽어올 때 읽어올 행을 가리키는 것. 처음 시작 위치는 첫 행이 아닌 어느 행도 가리키지 않는 위치에 있다. 다음 행이 있다면 true, 없다면 false 반환. 
				2. first() : select 되어진 결과물에서 커서를 가장 처음으로 옮겨주는 것	리턴타입은 boolean
				3. last()  : select 되어진 결과물에서 커서를 가장 마지막으로 옮겨주는 것	리턴타입은 boolean
						   
				== 커서가 위치한 행에서 컬럼의 값을 읽어들이는 메소드 ==
				getInt(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						             파라미터 숫자는 컬럼의 위치값 
						                 
				getInt(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						             파라미터 문자는 컬럼명 또는 alias명 
						                  
				getLong(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						               파라미터 숫자는 컬럼의 위치값 
						                 
				getLong(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						               파라미터 문자는 컬럼명 또는 alias명                
						   
				getFloat(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                 파라미터 숫자는 컬럼의 위치값 
						                 
				getFloat(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                 파라미터 문자는 컬럼명 또는 alias명 
						                      
				getDouble(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                   파라미터 숫자는 컬럼의 위치값 
						                 
				getDouble(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                   파라미터 문자는 컬럼명 또는 alias명    
						                        
				getString(숫자) : 컬럼의 타입이 문자열로 읽어들이때
						                   파라미터 숫자는 컬럼의 위치값 
						                 
				getString(문자) : 컬럼의 타입이 문자열로 읽어들이때
						                   파라미터 문자는 컬럼명 또는 alias명                                                        
			*/
			
			System.out.println("------------------------------------------------------------");
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			System.out.println("------------------------------------------------------------");
			
			StringBuilder sb = new StringBuilder();
			// 또는 StringBuffer
			
			while(rs.next()) {
				/*
					rs.next()는 select된 결과물에서 커서의 위치(행의 위치)를 다음으로 옮긴 후
					행이 존재하면 true, 행이 없으면 false를 리턴한다.
				 */
				int no = rs.getInt(1);		// 숫자 1은 select되어 나오는 컬럼의 위치값(순서)이다. 즉, 첫번째 컬럼(no). no 컬럼의 값은 숫자 타입이기 때문에 getInt()
		// 또는	int no = rs.getInt("NO");	// "NO"는 컬럼의 alias인 컬럼명을 말한다. 즉, NO 컬럼
				String name = rs.getString(2);
				String msg = rs.getString(3);
				String writeday = rs.getString(4);
		// 또는	String writeday = rs.getString("WRITEDAY");
				
				sb.append(no);		// 읽어온 값을 StringBuilder에 쌓아둔다.
				sb.append("\t" + name);
				sb.append("\t" + msg);
				sb.append("\t" + writeday + "\n");
			}// end of while(rs.next())
			
			/*
				String 객체와 StringBuilder 객체의 차이
				
				String str = ""; // str 객체생성
				str += "하나";	 // str 객체생성
				str += "둘";		 // str 객체생성
				str += "셋";	     // str 객체생성
				str += "넷";		 // str 객체생성. 문자열을 더할 때마다 객체를 생성한다.
				System.out.println(str); // 하나둘셋넷
				
				StringBuffer sb = new StringBuffer(); // Multi Thread 사용 ex) 게임
				StringBuilder sb = new StringBuilder(); // Single Thread 사용 ex) 웹
				
				StringBuilder sb = new StringBuilder();
				sb.append("하나");
				sb.append("둘");
				sb.append("셋");
				sb.append("넷"); // 하나의 객체에 문자열을 더한다(쌓는다).
				sb.toString(); // 하나둘셋넷
				하지만, StringBuiler 객체는 String 객체에 비해 크기가 커 하나의 객체만을 생성한다 하더라도 담고자 하는 정보의 양이 적다면
				굳이 사용할 필요가 없다(오히려 메모리를 더 많이 차지하기 때문에 String 객체가 더 낫다).
			 */
			
			System.out.println(sb.toString()); // sb.toString(): StringBuilder에 쌓아둔 값을 실제 타입인 String 타입으로 변환
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// >>> 6. 사용한 자원 반납 <<<
			// 반납의 순서는 생성순서의 역순으로 한다.
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); // 사용했다는 조건 확인을 위해 null값이 아닐 시라는 조건을 준다.
				if(conn!=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		sc.close();
		System.out.println("~~~ 프로그램 종료 ~~~");

	}// end of main()

}
