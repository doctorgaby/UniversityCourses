import becker.robots.City;
import becker.robots.Direction;
import becker.robots.RobotSE;

/**
 *@author Gabriel Bulai 
 *this class implements the treaded robot
 */

public class ThreadedRobot extends RobotSE implements Runnable {

	public ThreadedRobot(City arg0, int arg1, int arg2, Direction arg3) {
		super(arg0, arg1, arg2, arg3);

	}
	
	public void breakPlayer() {
		breakRobot("I'm broken");
	}
	@Override
	public void run() {

	}

}
