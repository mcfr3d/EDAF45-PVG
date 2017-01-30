package resultMerge;

import javax.swing.JFrame;

public class MaratonRaceGui extends RaceGui {

	MaratonRaceGui(JFrame frame) {
		super(frame,"Maraton race");
		

		this.addNameFileButton();
		this.addStartFileButton();
		this.addFinishFileButton();
		this.addExportButton();
		
	}
	
}
