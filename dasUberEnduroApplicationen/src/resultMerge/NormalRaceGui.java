package resultMerge;

import javax.swing.JFrame;

public class NormalRaceGui extends RaceGui {

	NormalRaceGui(JFrame frame) {
		super(frame, "Normal race");
		
		this.addNameFileButton();
		this.addStartFileButton();
		this.addFinishFileButton();
		this.addExportButton();
		
	}

}
