package test02.member;

import java.sql.*;
import java.util.Map;

/*
	DAO(Database Access Object): 데이터베이스 관련된 작업만을 수행하는 객체
	DAO를 생성하는 이유는 데이터베이스 작업에 대한 재사용성 때문이다.
 */

public class MemberDAO implements InterMemberDAO {
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// 자원반납 메소드 //
	// 자원반납을 인터페이스 메소드로 만들 경우 인터페이스 호출 시 사용이 완료되지 않은 자원을 반납할 수 있는 메소드가 돼 버려 설계 상 문제가 될 수 있으므로 주로 DAO의 private 접근지정자 메소드로 생성한다.
	private void close() {
		
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}// end of close()

	// DB에 회원가입 데이터를 INSERT하는 메소드 //
	@Override
	public int memberRegister(MemberDTO member) {
		
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "MYORAUSER", "cclass");
			
			String sql = "insert into jdbc_member(userseq, userid, passwd, name, mobile)\n"+
						 "values(userseq.nextval, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getMobile());
			
			result = pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
		} catch (SQLIntegrityConstraintViolationException e) { 
			// 아이디 중복 검사는 1. select문으로 동일 id 검색 후 insert 불가 판정 2. 일단 insert하여 에러 발생 시 insert 불가 판정
			// 2번으로도 가능하다. 에러 발생으로 회원가입 데이터가 전송되지 않기 때문.
			System.out.println("에러메시지 : " + e.getMessage());
			System.out.println("에러코드번호 : " + e.getErrorCode());			
			System.out.println(">>> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}// end of memberRegister(MemberDTO member)

	// 로그인처리 메소드 //
	@Override
	public MemberDTO login(Map<String, String> paraMap) {
		
		MemberDTO member = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "MYORAUSER", "cclass");
			
			String sql = // "select userseq, userid, passwd, name, mobile, point, to_char(registerday, 'yyyy-mm-dd') AS registerday, status\n"+
						 "select name\n"+ // 로그인 시 보여줄 정보가 이름밖에 없기 때문에 이름만 출력한다.
						 "from jdbc_member\n"+
						 "where userid = ? and passwd = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("passwd"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {  // userid는 고유하므로 조건에 맞는 행은 1개만 있다. 따라서 행이 있다면 1행만 출력하면 되므로 if로 써도 된다.
				member = new MemberDTO();
				member.setName(rs.getString(1));
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다. <<");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return member;
	}// end of login(Map<String, String> paraMap)

}
