package part2;
import finchRobot.Finch;

public abstract class  FinchAction  {
  
 private final Finch finch; 
 private final String action;  
 
 public FinchAction(String action, Finch finch) {
  this.action = action;
  this.finch = finch;
 }

 public String getAction() {
  return action;
 }
    
 public abstract void execute();
 
 @Override
 public String toString() {
  return null;
 }

public Finch getFinch() {
	return finch;
}
}
