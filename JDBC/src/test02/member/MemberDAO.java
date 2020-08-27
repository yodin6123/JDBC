package test02.member;

import java.sql.*;

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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}// end of memberRegister(MemberDTO member)

}
