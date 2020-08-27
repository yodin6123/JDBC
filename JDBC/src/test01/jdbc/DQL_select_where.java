/*
	프로그램이 실행되면
	모든 데이터를 조회해서 보여주도록 한다.
	------------------------------------------------------------
	글번호	글쓴이	글내용	작성일자
	------------------------------------------------------------
	6	김용진	추가요	2020-08-26 12:25:16
	3	홍길동	아버지를 아버지라 부르지 못하고	2020-08-26 12:08:58
	2	김민아	배고파~~~	2020-08-26 12:02:39
	
	---- >>> 조회할 대상 <<< ----
	1.글번호 2.글쓴이 3.글내용 4.종료
	-------------------------
	▷ 선택번호: 1
	▷ 검색어: 6
	
	------------------------------------------------------------
	글번호	글쓴이	글내용	작성일자
	------------------------------------------------------------
	6	김용진	추가요	2020-08-26 12:25:16
	
	---- >>> 조회할 대상 <<< ----
	1.글번호 2.글쓴이 3.글내용 4.종료
	-------------------------
	▷ 선택번호: 2
	▷ 검색어: 홍길동
	
	------------------------------------------------------------
	글번호	글쓴이	글내용	작성일자
	------------------------------------------------------------
	3	홍길동	아버지를 아버지라 부르지 못하고	2020-08-26 12:08:58
	
	---- >>> 조회할 대상 <<< ----
	1.글번호 2.글쓴이 3.글내용 4.종료
	-------------------------
	▷ 선택번호: 3
	▷ 검색어: 배고파 -- 해당 검색어가 포함되어 있으면 결과창에 나오도록 한다.
	
	------------------------------------------------------------
	글번호	글쓴이	글내용	작성일자
	------------------------------------------------------------
	2	김민아	배고파~~~	2020-08-26 12:02:39
	
	---- >>> 조회할 대상 <<< ----
	1.글번호 2.글쓴이 3.글내용 4.종료
	-------------------------
	▷ 선택번호: 4
	
	~~~~ 프로그램 종료 ~~~~
 */

package test01.jdbc;

import java.sql.*;
import java.util.Scanner;

public class DQL_select_where {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			System.out.print("▷ 연결할 오라클 서버의 IP 주소: ");
			String ip = sc.nextLine();
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe", "HR", "cclass"); // @127.0.0.1: 로컬 호스트(자신의 IP)
						
			String sql = " select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') AS WRITEDAY "
					   + " from jdbc_tbl_memo "
					   + " order by no desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			System.out.println("------------------------------------------------------------");
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			System.out.println("------------------------------------------------------------");
			
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
			}// end of while(rs.next())
			
			System.out.println(sb.toString());
			
			sb = new StringBuilder();
			sb.append("---- >>> 조회할 대상 <<< ----\n");
			sb.append("1.글번호  2.글쓴이  3.글내용  4.종료\n");
			sb.append("-------------------------\n");
			String menu = sb.toString();
			
			String menuNo = "";
			do {
				System.out.println(menu);
				System.out.print("▷ 선택번호: ");
				menuNo = sc.nextLine();
				
				String colName = "";  // where절에 들어올 컬럼명
				
				switch(menuNo) {
					case "1" :
						colName = "no";
						break;
					case "2" :
						colName = "name";
						break;
					case "3" :
						colName = "msg";
						break;
					case "4" :
						break;
					default :
						System.out.println("~~~ 메뉴에 없는 번호입니다. ~~~\n");
						break;
				}// end of switch()
				
				if("1".equals(menuNo) || "2".equals(menuNo) || "3".equals(menuNo)) {
					System.out.print("▷ 검색어: ");
					String search = sc.nextLine();
					
					sql = " select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') AS WRITEDAY "
						+ " from jdbc_tbl_memo ";
					if(!"3".equals(menuNo)) {  // 글번호 또는 글쓴이로 검색 시
						sql += " where " + colName + " = ? ";
					} else {  // 글내용으로 검색 시
						sql += " where " + colName + " like '%'||?||'%' ";  // 오라클 || ==> 결합('%'|| ? ||'%' : 물음표 앞 뒤 띄어도 된다)
						// where msg like '%아버지%'
					}
					sql += " order by no desc ";
					/*
					 	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						위치홀더(?)에는 오로지 데이터 값만 들어와야 하고,
						컬럼명 또는 테이블명에는 위치홀더가 들어오면 오류가 발생한다.
						컬럼명 또는 테이블명은 변수로 처리해주어야 한다.
					 */
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, search);
					
					rs = pstmt.executeQuery();
					
					System.out.println("------------------------------------------------------------");
					System.out.println("글번호\t글쓴이\t글내용\t작성일자");
					System.out.println("------------------------------------------------------------");
					
					// StringBuilder 초기화
					// 첫번째 방법: 새로운 객체 생성
					// 두번째 방법: delete() 나 setLength(0) 메소드 사용
					// sb.length();  // StringBuilder sb에 append된 개수 ==> 현재 3
					sb.delete(0, sb.length());  // 인자(int start, int end): 시작부터 끝 이전까지, 시작은 0부터 가능 ==> (0,3)이면 0부터 0,1,2인 3개 삭제
					// 또는 sb.setLength(0) 전체를 비우는 메소드
					
					while(rs.next()) {
						int no = rs.getInt(1);
						String name = rs.getString(2);
						String msg = rs.getString(3);
						String writeday = rs.getString(4);
						
						sb.append(no);
						sb.append("\t" + name);
						sb.append("\t" + msg);
						sb.append("\t" + writeday + "\n");
					}// end of while
					
					System.out.println(sb.toString());
				}
			} while(!("4".equals(menuNo)));
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		sc.close();
		System.out.println("~~~ 프로그램 종료 ~~~");

	}// end of main()

}
