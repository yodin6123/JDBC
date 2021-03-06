package test01.jdbc;

import java.sql.*;
import java.util.Scanner;

public class DML_insert {
	
	public static void main(String[] args) {
		
		Connection conn = null;
		// Connection conn은 오라클 데이터베이스 서버와 연결을 맺어주는 객체이다.
		
		PreparedStatement pstmt = null;
		// Connection conn(특정 오라클서버)에 전송할 SQL문(편지)을 전달할 객체(우편배달부)이다.
		
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
			// === Connection conn 기본값은 auto commit 이다. ===
			// === Connection conn 기본값인 auto commit을 수동 commit으로 전환하는 방법
			conn.setAutoCommit(false); // 수동 commit으로 전환
			
			// >>> 3. SQL문(예)편지) 작성 <<<
			System.out.print("▷ 글쓴이: ");
			String name = sc.nextLine();
			
			System.out.print("▷ 내용: ");
			String msg = sc.nextLine();
			
			String sql = "insert into jdbc_tbl_memo(no, name, msg)"
					   + "values(jdbc_seq_memo.nextval, ?, ?)";
			// ?: 위치홀더 라고 부른다.
			// PreparedStatement 에서 데이터는 무조건 위치홀더로 처리해야하며, 변수로 처리 시 오류가 된다.
			// 변수로 처리하는 Statement는 데이터 보안에 약점을 가지고 있어 외부에서도 데이터를 확인할 수 있지만, 위치홀더는 데이터를 감싸 외부에서도 ?로 밖에 보이지 않아 보안에 유리하다.
			// sql문을 마치는 ;(세미콜론)은 붙이면 안된다.
			
			// >>> 4. 연결할 오라클서버(conn)에 SQL문(편지)을 전달할 PreparedStatement 객체(우편배달부) 생성 <<<
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name); // 1은 sql에서 첫번째 ?를 말한다(index 0부터 시작 아님 주의). 첫번째 ?에 name을 넣으라는 뜻.
			pstmt.setString(2, msg);  // 2는 sql에서 두번째 ?를 말한다. 두번째 ?에 msg을 넣으라는 뜻.
			
			// >>> 5. PreparedStatement 객체(우편배달부) pstmst가 작성된 SQL문(편지)을 오라클서버에 보내서 실행 <<<
			int n = pstmt.executeUpdate();
			// 실행할 SQL문이 DML문일 때 사용
			// 반환형인 int 타입은 실행 후 결과가 적용되는 행의 개수를 의미
		//	pstmt.executeQuery(); select문일 때 사용
			if(n==1) {
				do {
					System.out.print("▷ 정말로 입력하시겠습니까?[Y/N] ");
					String yn = sc.nextLine();
					if("y".equalsIgnoreCase(yn)) {
						conn.commit(); // 수동 commit 실행
						System.out.println(">> 데이터 입력 성공!! <<");
						break;
					} else if("n".equalsIgnoreCase(yn)) {
						conn.rollback(); // 수동 commit 실행
						System.out.println(">> 데이터 입력 취소!! <<");
						break;
					} else {
						System.out.println(">> Y 또는 N 만 다시 입력해주세요. <<");
					}
				} while(true); // 2번 방법: break문 없이 while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)))로 탈출
			} else {
				System.out.println(">> 데이터 입력에 오류가 발생함 <<");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// >>> 6. 사용한 자원 반납 <<<
			// 반납의 순서는 생성순서의 역순으로 한다.
			try {
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
