/*
 * FinchMove.java        1.0 Feb 1, 2022
 *
 * Models the program.
 *
 * Copyright 2022 Rafel Botey Agusti <rbotey@escoladeltreball.org>
 *
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 */

package part2;
import finchRobot.Finch;
import part2.FinchAction;

public class FinchMove extends FinchAction {

 public FinchMove(String action, Finch finch) {
  super(action, finch); 
 }

 public void execute() {
	    String action = getAction();
	    int i1 = action.indexOf(" "); // posició del 1r espai (entre MOVE i duration)
	    int i2 = action.indexOf(" ", i1+1); // posició del 2n espai (entre duration i leftVelocity)
	    int i3 = action.indexOf(" ", i2+1); // posició del 3r espai (entre leftVelocity i rightVelocity)

	    int duration = Integer.parseInt(action.substring(i1+1, i2)); // agafes la duració del moviment del robot
	    int leftVelocity = Integer.parseInt(action.substring(i2+1, i3)); // agafes la velocitat de la roda esquerra
	    int rightVelocity = Integer.parseInt(action.substring(i3+1)); // agafes la velocitat de la roda dreta

	    getFinch().setWheelVelocities(leftVelocity, rightVelocity, duration); // determines la velocitat de rodes i el temps que es mouran posant en ordre les variables
	}
 
 @Override
 public String toString() {
     return getAction();
 }
}