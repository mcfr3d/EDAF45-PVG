package util;
import static org.junit.Assert.*;

import org.junit.Test;

public class RegistrationExpressionTest {

	private String t1 = "1";
	private String t2 = "1 2 3";
	private String t3 = "1,2 3";
	private String t4 = "2 ,, 5";
	private String t5 = " 1 2 ";
	private String t6 = "3-7";
	private String t7 = "7-3";
	private String t8 = " c- 10";
	private String t9 = "1, 3-6, 12";
	private String t10 = "1 3- 6 12";
	private String t11 = "1, 3-c10 14";
	private String t12 = "1 @Damer 3-c10, 14";
	private String t13 = "@seniorer @juniorer 1-3";

	@Test
	public void testWithOneInput() {
		String[] correctStartNbrs = new String[]{"1"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t1);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithWhitespace() {
		String[] correctStartNbrs = new String[]{"1", "2", "3"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t2);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithWhitespaceAndComma() {
		String[] correctStartNbrs = new String[]{"1", "2", "3"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t3);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}

	@Test
	public void testWithTwoCommas() {
		String[] correctStartNbrs = new String[]{"2", "5"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t4);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithTrailingWhitespace() {
		String[] correctStartNbrs = new String[]{"1", "2"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t5);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithInterval() {
		String[] correctStartNbrs = new String[]{"3", "4", "5", "6", "7"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t6);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithInvalidInterval() {
		String[] wrongStartNbrs = new String[]{"7-3"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t7);
		assertTrue(0 == evaled.evaluatedNbrs.size());
		for(int i = 0; i < wrongStartNbrs.length; i++) {
			assertEquals(wrongStartNbrs[i], evaled.errorList.get(i));
		}
	}
	
	@Test
	public void testWithInvalidIntervalWithNonDigit() {
		String[] wrongStartNbrs = new String[]{"c-10"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t8);
		assertTrue(0 == evaled.evaluatedNbrs.size());
		for(int i = 0; i < wrongStartNbrs.length; i++) {
			assertEquals(wrongStartNbrs[i], evaled.errorList.get(i));
		}
	}
	
	@Test
	public void testWithMixedInput1() {
		String[] correctStartNbrs = new String[]{"1", "3", "4", "5", "6", "12"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t9);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithMixedInput2() {
		String[] correctStartNbrs = new String[]{"1", "3", "4", "5", "6", "12"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t10);
		assertTrue(0 == evaled.errorList.size());
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	@Test
	public void testWithMixedInputWithError1() {
		String[] correctStartNbrs = new String[]{"1", "14"};
		String[] wrongStartNbrs = new String[]{"3-c10"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t11);
		for(int i = 0; i < wrongStartNbrs.length; i++) {
			assertEquals(wrongStartNbrs[i], evaled.errorList.get(i));
		}
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
	}
	
	@Test
	public void testWithMixedInputWithError2() {
		String[] correctClasses = new String[]{"Damer"};
		String[] correctStartNbrs = new String[]{"1","14"};
		String[] wrongStartNbrs = new String[]{"3-c10"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t12);
		for(int i = 0; i < wrongStartNbrs.length; i++) {
			assertEquals(wrongStartNbrs[i], evaled.errorList.get(i));
		}
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
		for(int i = 0; i < correctClasses.length; i++) {
			assertEquals(correctClasses[i], evaled.evaluatedClasses.get(i));
		}
	}
	@Test
	public void testWithMixedInput3() {
		String[] correctClasses = new String[]{"seniorer","juniorer"};
		String[] correctStartNbrs = new String[]{"1","2","3"};
		EvaluatedExpression evaled = RegistrationExpression.eval(t13);
		for(int i = 0; i < correctStartNbrs.length; i++) {
			assertEquals(correctStartNbrs[i], evaled.evaluatedNbrs.get(i));
		}
		for(int i = 0; i < correctClasses.length; i++) {
			assertEquals(correctClasses[i], evaled.evaluatedClasses.get(i));
		}
	}
	
}
