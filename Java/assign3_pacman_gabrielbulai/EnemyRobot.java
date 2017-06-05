import dit948.Random;
import becker.robots.City;
import becker.robots.Direction;
import becker.robots.RobotSE;

/**
 *@author Gabriel Bulai 
 *this class defines the behaviour of the enemy robot
 */

public class EnemyRobot extends RobotSE implements Runnable {

	public EnemyRobot(City arg0, int arg1, int arg2, Direction arg3) {
		super(arg0, arg1, arg2, arg3);

	}

	public void breakEnemy() {
		breakRobot("Enemy Down");
	}

	//commands for enemy robot to move randomly if front is clear
	public void move() {
        if (frontIsClear())
            super.move();
    					}
	
	public void randomMove() {
    	int nrTurns = Random.randomInt(4);
        double speed = this.getSpeed();
        if (nrTurns > 0)
                setSpeed(nrTurns*speed);
            for (int i = 0; i < nrTurns; i++)
                turnLeft();
            	setSpeed(speed);
            	move();
        						}

	@Override
	public void run() {

		randomMove();
	}

}
