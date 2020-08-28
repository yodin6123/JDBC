package test03.objectCreateOrder;

public class MyMain {

	public static void main(String[] args) {
		
		MyChild mc = new MyChild();
		
		System.out.println("주소 : " + MyChild.address);  // static 변수이기 때문에 인스턴스 생성할 필요 없다.
		System.out.println("나이 : " + mc.age);
		System.out.println("성명 : " + mc.name);
		// 객체 생성 시 실행 순서에 따라 필드에 마지막으로 저장된 값이 출력된다.
		
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		
		MyChild mc2 = new MyChild();

	}

}
