package mockingServer;

import org.mockito.Mockito;

class PANcard {
	public String isValid(String panCard) {
		if(panCard.matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
			return "Pancard is valid";
		}else {
			
			return "Pancard is Invalid";
		}
	}
	public static PANcard getMockitoObj() {
		PANcard mockObj = Mockito.mock(PANcard.class);
						Mockito.when(mockObj.isValid("ABCDE1234A")).thenReturn("Is Valid");
						Mockito.when(mockObj.isValid("ABCDE1234B")).thenReturn("Is Valid");
						Mockito.when(mockObj.isValid("ABCDE1234C")).thenReturn("Is InValid");
		return mockObj;
	}
}

public class PancardMockito{
	public static void main(String[] args) {
		PANcard obj = PANcard.getMockitoObj();
		System.out.println(obj.isValid("ABCDE1234D"));
	}
	
}