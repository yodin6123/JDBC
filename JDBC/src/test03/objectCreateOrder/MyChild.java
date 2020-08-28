package test03.objectCreateOrder;

public class MyChild extends MyParent {
	
	String name = "이순신";  // 1. 객체 생성 시 field 생성이 제일 먼저 일어난다.
	static String address = "경기도 고양시 우리동네";
	
	// **** static 초기화 블럭 **** // 2. 두번째로 static 초기화 블럭이 실행된다. 첫 객체 생성에 딱 1번만 실행된다. 그래서 주로 객체의 환경 설정에 쓰이고, 이후 객체 생성에 일괄적으로 적용된다.
	static {
		System.out.println("### 1. 자식클래스 MyChild의 static 초기화 블럭 실행됨(딱 1번만 실행됨) ###\n");
		address = "서울시 강남구 도곡동";  // static 초기화 블럭에는 static 변수만 들어온다.
	}
	
	// 3. 부모클래스의 기본생성자가 실행
	
	// **** instance 초기화 블럭 **** // 4. instance 초기화 블럭 실행
	{
		System.out.println("### 3. 자식클래스 MyChild의 instance 초기화 블럭 실행됨 ###\n");
		name = "엄정화";
	}
	
	public MyChild( ) {  // 5. 자식클래스의 기본생성자 실행
		System.out.println("### 4. 자식클래스 MyChild의 default 생성자 MyChild() 실행됨 ###\n");
		name = "몰라요";
	}
	
	// ** 생성 순서는 소스 코드 순서와 관계없이 위와 같이 정해져 있다.

}
