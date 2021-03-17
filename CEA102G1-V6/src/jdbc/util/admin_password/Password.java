package jdbc.util.admin_password;

public class Password {
	public static String generateRandomPassword() {
		//產生包含英文大小寫及數字的陣列
		char[] allWordAndNumber = new char[62];
		int index = 0;
		for (int unicode = 48 ; unicode <= 122; unicode++) {
			if (unicode > 57 && unicode < 65 || unicode > 90 && unicode < 97) { //遇到不是數字或英文字就跳過
				continue;
			}
			allWordAndNumber[index] = (char) unicode; //將要的字元填入驗證碼表
			index++;
		}
		
		//產生10位數的亂碼
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			password.append(allWordAndNumber[(int)(Math.random() * 62)]);
		}
		
		return password.toString();
	}
	
	public static String passwordEncoder(String realPassword) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < realPassword.length(); i++) {
			char c = realPassword.charAt(i);
			sb.append((char)(c + 2));
		}
		return sb.toString();
	}
	
	public static String passwordDecoder(String fakePassword) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fakePassword.length(); i++) {
			char c = fakePassword.charAt(i);
			sb.append((char)(c - 2));
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
//		String a = Password.generateRandomPassword();
//		String b = Password.passwordEncoder(a);
//		String c = Password.passwordDecoder(b);
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
	}
	
}
