package registration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import util.EvaluatedExpression;
import util.RegistrationExpression;

public class ListItem extends JPanel {
	private String time;
	private HashMap<ListItem, String> map;
	private Subscriber sub;
	private String faultyStartNumber;
	
	public ListItem(String time, HashMap<ListItem, String> map, Subscriber sub, String faultyStartNumber) {
		this.time = time;
		this.faultyStartNumber = faultyStartNumber;
		this.map = map;
		this.sub = sub;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		JLabel timeLabel = new JLabel("Tid: " + time);
		timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 34));
		if(faultyStartNumber.length() != 0){
		timeLabel.setForeground(Color.RED);}
	
		
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
			String message = "skriv startnumret: ";
			if(faultyStartNumber.length() != 0){
				message =  "Felaktig input! Du skrev: " + faultyStartNumber + "\nskriv startnumret/intervallet: "; 
			}
			Optional<String> s = Optional.ofNullable((String)JOptionPane.showInputDialog(message));		

			if(s.isPresent()){
				EvaluatedExpression evalTuple = RegistrationExpression.eval(s.get().trim());
				if(evalTuple.errorList.isEmpty()) {
					for(String correct : evalTuple.evaluatedNbrs) {
						map.put(ListItem.this, correct+"; " + time);
						sub.update();
					}
					return;
				} 
				JOptionPane.showMessageDialog(ListItem.this.getParent(),
				    "Felaktig input.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
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
