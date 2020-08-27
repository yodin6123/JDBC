package test02.member;

public interface InterMemberDAO {
	// 인터페이스 메소드는 public abstract가 생략되어 있는 형태이다.
	
	// 회원가입 메소드 //
	int memberRegister(MemberDTO member);
	

}
