//package registration;
//
//import static org.junit.Assert.*;
//
//import java.awt.AWTException;
//import java.awt.Component;
//import java.awt.Container;
//import java.io.File;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class GuiTest {
//	
//	private Gui gui;
//	private JButton button;
//	private JTextArea textOutput;
//	private JTextField textEntry;
//	private JPanel faultyRegistrationPanel;
//	private final String file = "/test/output.txt";
//	private Component[] listItems;
//	
//	@Before
//	public void before() throws AWTException {
//	    gui = new Gui(file, new ClientConnection());
//	    button = (JButton) getComponent(gui, "button");
//	    textOutput = (JTextArea) getComponent(gui, "textOutput");
//	    textEntry = (JTextField) getComponent(gui, "textEntry");
//	    faultyRegistrationPanel = (JPanel) getComponent(gui, "faultyRegistrationPanel");
//	    listItems = faultyRegistrationPanel.getComponents();
//	}
//
//	@After
//	public void after() {
//	    File f = new File(file);
//	    if (!f.delete()) {
//	        System.out.println("Failed to remove " + file);
//	    }
//	}
//	
//	@Test
//	public void testOneRegistration() {
//		textEntry.setText("1");
//		button.doClick();
//		assertEquals("1",textOutput.getText().substring(0, 1));
//		assertEquals(11, textOutput.getText().trim().length());
//		assertEquals(0, textEntry.getText().length());
//	}
//	
//	@Test
//	public void testMultipleRegistrations() {
//		textEntry.setText("1-8");
//		button.doClick();
//		String[] lines = textOutput.getText().split("\n");
//		assertEquals(8, lines.length);
//	}
//	
//	@Test
//	public void testClassRegistration() {
//		textEntry.setText("@seniorer");
//		button.doClick();
//		assertEquals("seniorer", textOutput.getText().substring(0, 8));
//	}
//	
//	@Test
//	public void testRemoveFaultyRegistration() {
//		button.doClick();
//		ListItem li = (ListItem) getComponent(gui, "listItem0");
//		assertEquals(8, li.getText().length());
//		JButton removeButton = (JButton) getComponent(gui, "removeButton");
//		removeButton.doClick();
//		try {
//			ListItem li0 = (ListItem) getComponent(gui, "listItem0");
//			li0.getText();
//			assertFalse(true);
//		} catch (Exception e) { }
//	}
//	
//	@Test
//	public void testEmptyField() {
//		button.doClick();
//		ListItem li = (ListItem) getComponent(gui, "listItem0");
//		assertEquals(8, li.getText().length());
//		try {
//			ListItem li1 = (ListItem) getComponent(gui, "listItem1");
//			li1.getText();
//			assertFalse(true);
//		} catch (Exception e) { }
//	}
//	
//	public static Component getComponent(Component parent, String name) {
//	    if (name.equals(parent.getName())) {
//	        return parent;
//	    }
//
//	    if (parent instanceof Container) {
//	        Component[] children = ((Container) parent).getComponents();
//
//	        for (Component c : children) {
//	            Component child = getComponent(c, name);
//	            if (child != null) return child;
//	        }
//	    }
//	    return null;
//	}
//}