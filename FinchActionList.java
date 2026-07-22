package part2;

import finchRobot.Finch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;

public class FinchActionList {

	//private final String fileName;
	private ArrayList<FinchAction> actions;
	private final Finch finch;
	//Base de dades
	private Connection connection;
	private String nomLaberint;

	public FinchActionList(String nomLaberint, Finch finch){
		this.nomLaberint = nomLaberint;
		this.actions = new ArrayList<FinchAction>();
		this.finch = finch;
		try {
			this.connection = DriverManager.getConnection(
	        		"jdbc:postgresql://localhost:5432/finch",
	        		"finch",
	        		"123456");
	        this.connection.setAutoCommit(true);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}
	
	public void setNomLaberint(String nomLaberint) {
	    this.nomLaberint = nomLaberint;
	}

	public ArrayList<FinchAction> getActions() {
		return actions;
	}

	public Finch getFinch() {
		return finch;
	}

	public void setActions(ArrayList<FinchAction> actions) {
		this.actions = actions;
	}

	public void addAction(FinchAction finchAction) {
		this.actions.add(finchAction);	
		}

	public void removeAction(FinchAction finchAction) {
		this.actions.remove(finchAction);

	}

	public void removeDataBaseTable() {	
		String sql = "DROP TABLE IF EXISTS " + nomLaberint;
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}

	public void importOrders() {
		String sentence = "SELECT * FROM " + nomLaberint;
		ResultSet myResultSet;
		String action;
		try (Statement myStatement = connection.createStatement()){
			myResultSet = myStatement.executeQuery(sentence);
			//Llegir el ResulSet
			while (myResultSet.next()) {
				if (!myResultSet.wasNull()){
					action = myResultSet.getString("action").trim();
					// Si és una ordre "MOVE" utilitzem la classe FinchMove per executar les ordres.
					if (action.indexOf("MOVE") == 0) {
						addAction(new FinchMove(action, finch));
					} else if (action.indexOf("NOSE") == 0) {
						addAction(new FinchNose(action, finch));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void exportOrders() {
	    try {
	        Statement st = connection.createStatement();
	        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + nomLaberint + " (" +
	        	    		 "lab_id BIGSERIAL PRIMARY KEY," + 
	        	    		 "action VARCHAR(50)" + ")" );
	        st.executeUpdate("DELETE FROM " + nomLaberint);
	        for (FinchAction action : actions) {
	            st.executeUpdate("INSERT INTO " + nomLaberint + " (action) VALUES ('" + action.toString() + "')");
	        }
	        st.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Mostra totes les accions a l'ArrayList actions
	 */
	public void displayOrders() {
		for (FinchAction a : actions) {
			System.out.println(a);
		}
	}

	public void removeOrders() {
		this.actions.clear();
	}


	public void execute(String seconds) {
		int ms = Integer.parseInt(seconds) * 1000;
		for (FinchAction action : actions) {
			action.execute();
			try {
				Thread.sleep(ms); //Pausa el programa.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Converteix Strings d'ordres separats per "\n": "MOVE 2000 100 100\nNOSE 0 0
	 * 255" A un array: ["MOVE 2000 100 100", "NOSE 0 0 255"]
	 * 
	 * @param order String amb un seguit d'ordres separat per '\n'
	 */
	public void executeOrder(String order) {
		ArrayList<String> orders = new ArrayList<String>();
		int newOrderPos = 0;
		while (newOrderPos != -1) {
			newOrderPos = order.indexOf("\n");
			if (newOrderPos != -1) {
				System.out.println(newOrderPos);
				orders.add(order.substring(0, newOrderPos));
				order = order.substring(newOrderPos +1, order.length());
				System.out.println(order);
			} else {
				orders.add(order); // Última línia (No més \n).
			}
		}
		
		for (String line : orders) {
			line = line.trim();
			// Si és una ordre "MOVE" utilitzem la classe FinchMove per executar les ordres.
			if (line.indexOf("MOVE") == 0) {
				new FinchMove(line, finch).execute();
			} else if (line.indexOf("NOSE") == 0) {
				new FinchNose(line, finch).execute();
			}
		}
	}
}
