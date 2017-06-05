

public class Pacman {
	public static void main(String[] args) {
		
		/**
		 *@author Gabriel Bulai
		 *the class represents the main function that calls the other objects for the game to start. 
		 */

		MenuCity framedCity = new MenuCity();

		framedCity.addCity();
		framedCity.addMenu();
		framedCity.pauseRestart.doClick();
		framedCity.run();

	}

}
