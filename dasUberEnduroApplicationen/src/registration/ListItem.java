package registration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ListItem extends JPanel {
	private String time;
	private HashMap<ListItem, String> map;
	private Subscriber sub;
	
	public ListItem(String time, HashMap<ListItem, String> map, Subscriber sub) {
		this.time = time;
		this.map = map;
		this.sub = sub;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		JLabel timeLabel = new JLabel("Tid: " + time);
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new EditButtonListener());
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveButtonListener());
		this.add(timeLabel);
		this.add(editButton);
		this.add(removeButton);
	}
	
	private class EditButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Optional<String> s = Optional.ofNullable((String)JOptionPane.showInputDialog("Skriv startnummer: "));		
			if(s.isPresent()){
				if(correctInput(s.get())){
					map.put(ListItem.this, s.get()+"; " + time);
					sub.update();
				}
			}
		}
		
		private boolean correctInput(String input) {
			for (char c : input.toCharArray()) {
				if (!Character.isDigit(c)) {
					return false;
				} 
			}
			return input.length() != 0;
		}

		
	}
	
	private class RemoveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			map.remove(ListItem.this);
			sub.update();
		}
		
	}
	
	
}
