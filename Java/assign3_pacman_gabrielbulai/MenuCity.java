import javax.swing.*;

import java.awt.event.WindowEvent;

import becker.robots.*;

/**
 *@author Gabriel Bulai 
 *this class defines the menu with the difficulty options, restart if prize has been picked up
 *and quit the game otherwise.
 */

public class MenuCity extends MyFrame {

	public MenuCity() {
		super();
	}

	public JMenu makeActionsMenu() {

		// first create the menu items..
		JMenuItem pauseRestart = new JMenuItem("Pause/Restart");

		pauseRestart.addActionListener(new PauseRestartListener(this));

		// then add them to the menu
		JMenu actions = new JMenu("Actions");
		actions.add(pauseRestart);
		return actions;
	}

	public void quit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void restart() {
		frame.remove(view);

		addPanel();
		addCity();
		addMenu();
		pauseRestart.doClick();
	}

	public void changeSetting(double speed) {
		this.speed = speed;
		restart();
	}
//creation of the menu
	public JMenu makeSettingsMenu() {
		// create the menu items
		JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
		JRadioButtonMenuItem medium = new JRadioButtonMenuItem("Medium");
		JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
// set up level difficulty
		if (this.speed == 0.4)
			easy.setSelected(true); // easy is the default
		else if (this.speed == 1)
			medium.setSelected(true);
		else if (this.speed == 2)
			hard.setSelected(true);

		// add listeners
		easy.addActionListener(new SettingsListener(this, 0.4));
		medium.addActionListener(new SettingsListener(this, 1));
		hard.addActionListener(new SettingsListener(this, 2));

		// new step: put buttons in a group, so that we know only
		// one of them can be selected at a time
		ButtonGroup settingsGroup = new ButtonGroup();
		settingsGroup.add(easy);
		settingsGroup.add(medium);
		settingsGroup.add(hard);

		// add the items to the menu
		JMenu settings = new JMenu("Settings");
		settings.add(easy);
		settings.add(medium);
		settings.add(hard);
		return settings;
	}

	public void addMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu actions = makeActionsMenu();
		menuBar.add(actions);

		// Once again for the settings menu

		JMenu settings = makeSettingsMenu();
		// add the menu to the menu bar
		menuBar.add(settings);

		// finally, add the menu bar to the frame

		frame.setJMenuBar(menuBar);

		frame.pack();

	}

	public void run() {

		while (true) {

			// if EnemyRobot is at the same intersection as the threadedRobot
			if ((pac.getIntersection() == enemy.getIntersection())) {
				
				try {
					pac.breakPlayer();
				} catch (RobotException e) {
				int choice = JOptionPane
						.showConfirmDialog(null, "You Lose! Restart Game?", "Game Over",
								JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION)
					restart();
				else
					quit();
				}
			}

			// if you picked up the prize
			if (pac.countThingsInBackpack() == 1) {

				try {
					enemy.breakEnemy();
				} catch (RobotException e) {
					int choice = JOptionPane.showConfirmDialog(null,
							"You Win! Restart Game?", "Game Over",
							JOptionPane.YES_NO_OPTION);

					if (choice == JOptionPane.YES_OPTION)

						restart();
					else
						quit();
				}

			}

			new Thread(pac).run();

			new Thread(enemy).run();
		}

	}

}
