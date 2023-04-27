package br.com.zaek.utils;

public class Utils {

	// TODO Auto-generated constructor stub
	public static Double convertToDouble(String number) {
		if (number == null ) {
			return 0D;
		}
		
		String newNumber = number.replaceAll(",",".");
		if (isNumeric(newNumber)) {
			return Double.parseDouble(newNumber);
		}
		// TODO Auto-generated method stub
		return 0D;
	}

	public static boolean isNumeric(String number) {
		if (number == null ) {
			return false;
		}
		String newNumber = number.replaceAll(",",".");
		
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	

}
