package test06.board;

import java.util.*;

import test05.singleton.dbconnection.MyDBConnection;

public class TotalController {
	
	// DAO(Data Access Object) ==> 데이터베이스에 연결하여 관련된 업무(DDL, DML, DQL)를 처리해주는 객체
	InterMemberDAO mdao = new MemberDAO();
	InterBoardDAO bdao = new BoardDAO();

	// 시작 메뉴 //
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
						if(member!=null) {  // 로그인 성공인 경우
							menu_Board(member, sc);  // 게시판 메뉴에 들어간다.
						} else {  // 로그인 실패인 경우
							System.out.println("~~~ 로그인 실패!! ~~~");
						}
					} else {
						member = null;  // 로그아웃
					}
					break;
					
				case "3":  // 프로그램 종료
					appExit();  // Connection 자원 반납
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

		int n = mdao.memberRegister(member, sc);

		if (n == 1) {
			System.out.println("\n >>> 회원가입을 축하드립니다. <<<");
		} else {
			System.out.println("\n >>> 회원가입을 취소합니다. <<<");
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

		Map<String, String> paraMap = new HashMap<String, String>(); // 각각의 변수를 따로 보내지 않고 하나의 Map으로 묶어 함께 보낸다.
		paraMap.put("userid", userid);
		paraMap.put("passwd", passwd);

		member = mdao.login(paraMap); // 최종 리턴 타입을 MemberDTO로 받아야 하기 때문에 mdao의 login() 메소드 또한 반환 타입을 MemberDTO로 해야 한다.

		if (member != null) {
			System.out.println("\n >>> 로그인 성공!! <<<\n");
		} else {
			System.out.println("\n >>> 로그인 실패!! <<<\n");
		}

		return member;
	}// end of login(Scanner sc)
	
	// 게시판 메뉴 //
	private void menu_Board(MemberDTO loginMember, Scanner sc) {
		
		String adminMenu = ("admin".equals(loginMember.getUserid())) ? "10.모든회원정보조회" : "" ;
		String menuNo = "";
		do {
			System.out.println("\n------------ 게시판메뉴["+loginMember.getName()+"님 로그인중..] ------------\n"
							 + "1.글목록보기 2.글내용보기 3.글쓰기 4.댓글쓰기\n"
							 + "5.글수정하기 6.글삭제하기 7.최근1주일간 일자별 게시글 작성건수\n"
							 + "8.이번달 일자별 게시글 작성건수 9.나가기 " + adminMenu + "\n"
							 + "------------------------------------------------");
			
			System.out.print("▷ 메뉴번호 선택 : ");
			menuNo = sc.nextLine();
			
			switch (menuNo) {
			case "1":  // 글목록보기
				
				break;
			
			case "2":  // 글내용보기
				
				break;
				
			case "3":  // 글쓰기
				
				break;
	
			case "4":  // 댓글쓰기
				
				break;
				
			case "5":  // 글수정하기
				
				break;
				
			case "6":  // 글삭제하기
				
				break;
				
			case "7":  // 최근1주일간 일자별 게시글 작성건수
				
				break;
				
			case "8":  // 이번달 일자별 게시글 작성건수
				
				break;
				
			case "9":  // 나가기
				
				break;
				
			case "10":  // 모든회원정보조회(관리자 전용 메뉴) / 메뉴에 없는 번호(일반회원)
				if("admin".equals(loginMember.getUserid())) {
					// 관리자
				} else {
					System.out.println(">> 메뉴에 없는 번호입니다. <<\n");  // 일반유저
				}
				break;

			default:
				System.out.println(">> 메뉴에 없는 번호입니다. <<\n");
				break;
			}
			
		} while (!"9".equals(menuNo));
		
	}// end of menu_Board(MemberDTO member, Scanner sc)
	
	// Connection 자원 반납 //
	private void appExit() {
		MyDBConnection.closeConnection();
	}

}
