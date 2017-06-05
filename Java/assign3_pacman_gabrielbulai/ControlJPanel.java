import becker.robots.*;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlJPanel extends JPanel implements ActionListener {

	/**
	 *@author Gabriel Bulai 
	 *this class contains code to set up the view and to define the buttons size and actions
	 */
	// variables declaration
	private RobotSE _pac;
	JButton leftButton;
	JButton upButton;
	JButton pickButton;
	JButton rightButton;
	JButton downButton;

	public ControlJPanel(RobotSE pac) {
		_pac = pac;

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints c;
		c = new GridBagConstraints();

		// leftbutton
		c.gridx = 2;
		c.gridy = 1;
		c.ipadx = 6;
		c.ipady = 6;
		leftButton = new JButton("Left");
		leftButton.addActionListener(this);
		this.add(leftButton, c);
		leftButton.setPreferredSize(new Dimension (100, 25) );
		

		// upbutton
		c.gridx = 3;
		c.gridy = 0;
		c.ipadx = 6;
		c.ipady = 6;
		upButton = new JButton("Up");
		upButton.addActionListener(this);
		this.add(upButton, c);
		upButton.setPreferredSize(new Dimension (100, 25) );

		// pickButton
		c.gridx = 3;
		c.gridy = 1;
		c.ipadx = 6;
		c.ipady = 6;
		pickButton = new JButton("Pick");
		pickButton.addActionListener(this);
		this.add(pickButton, c);
		pickButton.setPreferredSize(new Dimension (100, 25) );

		// rightbutton
		c.gridx = 4;
		c.gridy = 1;
		c.ipadx = 6;
		c.ipady = 6;
		rightButton = new JButton("Right");
		rightButton.addActionListener(this);
		this.add(rightButton, c);
		rightButton.setPreferredSize(new Dimension (100, 25) );

		// downbutton
		c.gridx = 3;
		c.gridy = 2;
		c.ipadx = 6;
		c.ipady = 6;
		downButton = new JButton("Down");
		downButton.addActionListener(this);
		this.add(downButton, c);
		downButton.setPreferredSize(new Dimension (100, 25) );
	}

	public void actionPerformed(ActionEvent e) {
		// leftbutton
		if (e.getSource() == leftButton) {

			while (!_pac.isFacingWest()) {
				_pac.turnLeft();
			}
			if (_pac.frontIsClear()) {
				_pac.move();
			}

		}

		// upbutton
		if (e.getSource() == upButton) {

			while (!_pac.isFacingNorth()) {
				_pac.turnLeft();
			}
			if (_pac.frontIsClear()) {
				_pac.move();
			}
		}

		// pickbutton
		if (e.getSource() == pickButton) {

			if (_pac.canPickThing()) {
				_pac.pickAllThings();
			}
		}

		// rightbutton
		if (e.getSource() == rightButton) {

			while (!_pac.isFacingEast()) {
				_pac.turnLeft();
			}
			if (_pac.frontIsClear()) {
				_pac.move();
			}

		}
		// down
		if (e.getSource() == downButton) {

			while (!_pac.isFacingSouth()) {
				_pac.turnLeft();
			}
			if (_pac.frontIsClear()) {
				_pac.move();
			}
		}

	}
}
