import java.awt.event.*;

/**
 *@author Gabriel Bulai 
 *this class implements the action listener for the settings button
 */

public class SettingsListener implements ActionListener {
	MenuCity amc;
	double setting;

	public SettingsListener(MenuCity amc, double setting) {
		this.amc = amc;
		this.setting = setting;
	}

	public void actionPerformed(ActionEvent ae) {
		amc.changeSetting(setting);
	}
}
