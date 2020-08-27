package test01.jdbc;

import java.sql.*;
import java.util.Scanner;

public class DML_delete {

	public static void main(String[] args) {
		
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			// >>> 1. 오라클 드라이버 로딩 <<<
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// >>> 2. 어떤 오라클 서버에 연결할지를 결정 <<<
			System.out.print("▷ 연결할 오라클 서버의 IP 주소: ");
			String ip = sc.nextLine();
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe", "HR", "cclass");
			conn.setAutoCommit(false);
			
			// >>> 3. SQL문(예)편지) 작성 - select문 <<<
			String sql = " select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') "
					   + " from jdbc_tbl_memo "
					   + " order by no desc ";
			
			// >>> 4. 연결할 오라클서버(conn)에 SQL문(편지)을 전달할 PreparedStatement 객체(우편배달부) 생성 <<<
			pstmt = conn.prepareCall(sql);
			
			// >>> 5. PreparedStatement 객체(우편배달부) pstmst가 작성된 SQL문(편지)을 오라클서버에 보내서 실행 <<<
			rs = pstmt.executeQuery();
			
			StringBuilder sb = new StringBuilder();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String msg = rs.getString(3);
				String writeday = rs.getString(4);
				
				sb.append(no);
				sb.append("\t" + name);
				sb.append("\t" + msg);
				sb.append("\t" + writeday + "\n");
			}
			
			System.out.println("------------------------------------------------------------");
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			System.out.println("------------------------------------------------------------");
			System.out.println(sb.toString());
			
			// >>> 3. SQL문(예)편지) 작성 - delete문 <<<
			System.out.print("▷ 삭제할 글번호: ");
			String no = sc.nextLine();
			
			String deleteSql = " delete from jdbc_tbl_memo "
					         + " where no = ? ";
			
			// >>> 4. 연결할 오라클서버(conn)에 SQL문(편지)을 전달할 PreparedStatement 객체(우편배달부) 생성 <<<
			pstmt.close();
			pstmt = conn.prepareStatement(deleteSql);
			pstmt.setString(1, no);
			
			// >>> 5. PreparedStatement 객체(우편배달부) pstmst가 작성된 SQL문(편지)을 오라클서버에 보내서 실행 <<<
			int n = pstmt.executeUpdate();
			if(n==1) {
				String yn = "";
				do {
					System.out.print("▷ 정말로 삭제하시겠습니까?[Y/N] ");
					yn = sc.nextLine();
					if("y".equalsIgnoreCase(yn)) {
						conn.commit();
						System.out.println(">> 데이터 삭제 성공!! <<");
					} else if("n".equalsIgnoreCase(yn)) {
						conn.rollback();
						System.out.println(">> 데이터 삭제 취소!! <<");
					} else {
						System.out.println(">> Y 또는 N 만 다시 입력해주세요. <<");
					}
				} while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
			} else if(n==0) {
				System.out.println(">> 삭제하시려는 글번호"+no+"는 존재하지 않는 글번호입니다. <<");
			} else {
				System.out.println(">> 데이터 입력에 오류가 발생함 <<");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// >>> 6. 사용한 자원 반납 <<<
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		sc.close();
		System.out.println("~~~ 프로그램 종료 ~~~");

	}

}
