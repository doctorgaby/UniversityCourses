import java.awt.event.*;

/**
 *@author Gabriel Bulai 
 *this class implements the action listener for the pause/restart
 */

public class PauseRestartListener implements ActionListener {
	MenuCity rmc;

	public PauseRestartListener(MenuCity rmc) {
		this.rmc = rmc;
	}

	public void actionPerformed(ActionEvent ae) {
		rmc.pauseRestart.doClick();
	}
}
