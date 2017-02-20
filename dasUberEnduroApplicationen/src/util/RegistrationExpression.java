package util;

import java.util.LinkedList;

public class RegistrationExpression {
	//TODO: Skriv tester
	public static EvaluatedExpression eval(String input) {
		EvaluatedExpression e = new EvaluatedExpression();
		for(String text: formatInputString(input)) {
			if(isInterval(text)){
				String[] array = text.split("-");
				int start = Integer.parseInt(array[0].trim());
				int end = Integer.parseInt(array[1].trim());
				if(start>end){
					e.errorList.add(text);
				}
				for (int i = start; i <= end; i++) {
					e.evaluatedNbrs.add("" + i);
				}
			} else {
				String[] startNumbers = text.split(" ");
				for (String s : startNumbers) {
					if (correctInput(s)) {
						e.evaluatedNbrs.add(s);
					} else {
						e.errorList.add(text);
					}
				}
			}
		}		
		return e;
	}
	
	private static String[] formatInputString(String text) {
		text = text.trim();
		text = text.replaceAll("\\s*(-)+\\s*", "-");
		text = text.replaceAll(",", " ");			
		return text.split("\\s+");
	}

	private static boolean isInterval(String text) {
		return text.matches("\\d+\\s*[-]\\s*\\d+");
		
	}

	private static boolean correctInput(String input) {
		for (char c : input.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return input.length() != 0;
	}
}
