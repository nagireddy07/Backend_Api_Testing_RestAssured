package encryption;

import org.testng.annotations.Test;

import backendTesting.genericUtilities.AES_EncryptionDecryption_Utility;

public class SampleEncryptionDecryption {
	String encryptedBody;
	String decryptedBody;
	String SECRET_KEY = "#*#*#*_(&%@#$!@#";
	@Test
	public void encrypt() throws Exception {
		encryptedBody = AES_EncryptionDecryption_Utility.encrypt("{\"username\":\"sample\",\"password\":\"test\"}",SECRET_KEY);
		System.out.println(encryptedBody);
		
	}
	@Test(dependsOnMethods = "encrypt")
	public void decrypt() throws Exception {
		decryptedBody = AES_EncryptionDecryption_Utility.decrypt(encryptedBody,SECRET_KEY);
		System.out.println(decryptedBody);
	}
}
