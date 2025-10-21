package backendTesting.genericUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * This Class consists of generic methods related to java functionalities.
 * @author Bobbala Venkata Nagi
 */
public class JavaUtility {
	
	/**
	 * This Method will return current system date and time
	 * @return timestamp as String
	 */
	public String getSystemDate() {
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		return s.format(d);
	}
	
	/**
	 * This Method will return random int number
	 * @return
	 */
	public int getRandomNumber() {
		Random r = new Random();
		return r.nextInt(100);
//		return (int) Math.random();
	}
}
