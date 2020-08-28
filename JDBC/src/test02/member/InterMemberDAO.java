package test02.member;

import java.util.Map;

public interface InterMemberDAO {
	// 인터페이스 메소드는 public abstract가 생략되어 있는 형태이다.
	
	// 회원가입 메소드 //
	int memberRegister(MemberDTO member);
	
	// 로그인처리 메소드 //
	MemberDTO login(Map<String, String> paraMap);

}
