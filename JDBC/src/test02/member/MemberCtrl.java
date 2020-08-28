package test02.member;

import java.util.*;

public class MemberCtrl {
	
	InterMemberDAO mdao = new MemberDAO();

	// 시작메뉴 //
	public void menu_Start(Scanner sc) {
		
		MemberDTO member = null;
		String sChoice = "";
		do {
			String loginName = (member==null) ? "" : "[" + member.getName() + " 로그인중..]";
			String login_logout = (member==null) ? "로그인" : "로그아웃";
			
			System.out.println("\n >>> ----- 시작메뉴 "+loginName+"----- <<<\n"
					+ "1.회원가입  2."+login_logout+"  3.프로그램종료\n"
					+ "------------------------------\n");
			
			System.out.print("▷ 메뉴번호선택 : ");
			sChoice = sc.nextLine();
			
			switch (sChoice) {
				case "1":  // 회원가입
					memberRegister(sc);
					break;
				
				case "2":  // 로그인 또는 로그아웃
					if("로그인".equals(login_logout)) {
						member = login(sc);  // 로그인시도
					} else {
						member = null;  // 로그아웃
					}
					break;
					
				case "3":
					
					break;

				default:
					System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택하세요!! <<<\n");
					break;
			}// end of switch()
			
		} while(!("3".equals(sChoice)));
		
	}// end of menu_Start(Scanner sc)

	// 회원가입 //
	private void memberRegister(Scanner sc) {
		
		System.out.println("\n >>> --- 회원가입 --- <<<");
		
		System.out.print("1. 아이디 : ");
		String userid = sc.nextLine();
		
		System.out.print("2. 암호 : ");
		String passwd = sc.nextLine();
		
		System.out.print("3. 회원명 : ");
		String name = sc.nextLine();
		
		System.out.print("4. 연락처(휴대폰) : ");
		String mobile = sc.nextLine();
		
		MemberDTO member = new MemberDTO();
		member.setUserid(userid);
		member.setPasswd(passwd);
		member.setName(name);
		member.setMobile(mobile);
		
		int n = mdao.memberRegister(member);
		
		if(n==1) {
			System.out.println("\n >>> 회원가입을 축하드립니다. <<<");
		} else {
			System.out.println("\n >>> 회원가입 실패!! <<<");
		}
		
	}// end of memberRegister(Scanner sc)
	
	// 로그인 //
	private MemberDTO login(Scanner sc) {
		
		MemberDTO member = null;
		
		System.out.println("\n >>> --- 로그인 ---- <<<");
		
		System.out.print("▷ 아이디 : ");
		String userid = sc.nextLine();
		
		System.out.print("▷ 암호 : ");
		String passwd = sc.nextLine();
		
		Map<String, String> paraMap = new HashMap<String, String>();  // 각각의 변수를 따로 보내지 않고 하나의 Map으로 묶어 함께 보낸다.
		paraMap.put("userid", userid);
		paraMap.put("passwd", passwd);
		
		member = mdao.login(paraMap);  // 최종 리턴 타입을 MemberDTO로 받아야 하기 때문에 mdao의 login() 메소드 또한 반환 타입을 MemberDTO로 해야 한다.
		
		if(member!=null) {
			System.out.println("\n >>> 로그인 성공!! <<<\n");
		} else {
			System.out.println("\n >>> 로그인 실패!! <<<\n");
		}
		
		return member;
	}// end of login(Scanner sc)

}
