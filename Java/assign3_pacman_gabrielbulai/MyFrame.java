import becker.robots.*;

import javax.swing.*;

import dit948.Random;

import java.awt.*;

/**
 *@author Gabriel Bulai
 * this class initiate the city structure and places the pacman and thread enemy robot
 */

public class MyFrame {
	//variables declaration
	protected JFrame frame;
	RobotUIComponents ui;
	JPanel view;
	City myCity;
	JButton pauseRestart;
	ThreadedRobot pac;
	EnemyRobot enemy;
	double speed = 0.2;

	//set up the frame
	
	public MyFrame() {
		frame = new JFrame("Assignment03: Pacman Game");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addPanel();

	}

	public void addPanel() {
		view = new JPanel(new BorderLayout());
		frame.setContentPane(view);
		frame.pack();
	}

	public void addCity() {
		// create world
		City.showFrame(false); // prevent City from showing its own frame
		int size = 11;

		myCity = new City(size, size);
		initCity(size);

		// Places a robot in the center
		pac = new ThreadedRobot(myCity, size / 2, size / 2, Direction.NORTH);
		pac.setColor(Color.BLACK);

		// ENEMY create a new robot
		enemy = new EnemyRobot(myCity, size / 3, size / 3, Direction.NORTH);
		enemy.setColor(Color.RED);
		enemy.setSpeed(speed);

		ui = new RobotUIComponents(myCity, 0, 0, size, size);
		frame.setJMenuBar(ui.getMenuBar());
		CityView sp = ui.getCityView();
		view.add(sp, BorderLayout.CENTER);
		view.add(new ControlJPanel(pac), BorderLayout.SOUTH);
		pauseRestart = ui.getStartStopButton();

		frame.pack();

	}

	public void initCity(int size) {
		for (int i = 0; i < size + 1; i++) {
			new Wall(myCity, 0, i, Direction.NORTH);
			new Wall(myCity, size - 1, i, Direction.SOUTH);
			new Wall(myCity, i, 0, Direction.WEST);
			new Wall(myCity, i, size - 1, Direction.EAST);
		}

		// Place the prize at a random spot in the city
		// prize can't be at the same spot as pac

		int randomStreet = Random.randomInt(size);
		int randomAvenue = Random.randomInt(size);
		RedIcon icona = new RedIcon(0.5);

		Flasher prize = new Flasher(myCity, randomStreet, randomAvenue);
		prize.setIcon(icona);

	}

}