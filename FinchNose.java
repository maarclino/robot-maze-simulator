package part2;
import finchRobot.Finch;
import part2.FinchAction;

public class FinchNose extends FinchAction {
 
 public FinchNose(String action, Finch finch) {
  super(action, finch);
 }

 public void execute() {
	    String action = getAction();
	    int i1 = action.indexOf(" "); // posició del 1r espai (entre NOSE i red)
	    int i2 = action.indexOf(" ", i1+1); // posició del 2n espai (entre red i green)  
	    int i3 = action.indexOf(" ", i2+1); // posició del 3r espai (entre green i blue)

	    int red = Integer.parseInt(action.substring(i1+1, i2)); // agafes la intensitat del color vermell
	    int green = Integer.parseInt(action.substring(i2+1, i3)); // agafes la intensitat del color verd
	    int blue = Integer.parseInt(action.substring(i3+1)); // agafes la intensitat del color blau

	    getFinch().setLED(red, green, blue); // poses la combinació de colors al finch amb setLED
	}
 
 @Override
 public String toString() {
  return getAction();
 }

}
