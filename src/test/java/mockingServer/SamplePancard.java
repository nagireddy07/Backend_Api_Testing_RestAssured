package mockingServer;

class PancardValidate {
	public static void isValid(String panCard) {
		if(panCard.matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
			System.out.println("Pancard is valid");
		}else {
			System.out.println("Pancard is Invalid");
		}
	}
}

public class SamplePancard{
	public static void main(String[] args) {
		PancardValidate.isValid("ABCDE1235H");
	}
	
}