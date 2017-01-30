package registration;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.BorderFactory;
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
		timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new EditButtonListener());
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveButtonListener());
		this.add(timeLabel);
		JPanel componentSeparator = new JPanel();
		componentSeparator.setMaximumSize(new Dimension(10, 10));
		this.add(componentSeparator);
		this.add(editButton);
		this.add(componentSeparator);
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
