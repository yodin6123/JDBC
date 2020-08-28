package test04.singletonPattern;

public class NoSingletonNumber {
	
	private int cnt = 0;  // 인스턴스 변수
	
	// 기본 생성자가 생략되어 있다.
	
	public int getNextNumber() {  // 인스턴스 메소드
		return ++cnt;  // 인스턴스 변수
	}

}
