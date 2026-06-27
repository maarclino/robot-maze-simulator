/*
 * Maze.java        1.0 Feb 1, 2022
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

public class Maze {

	public static void main(String[] args) {
		Finch finch = new Finch();
		
		FinchActionList list = new FinchActionList("lab_1a", finch);
		Reader reader = new Reader();
		int opcio = -1;
		while (opcio != 0) {
			System.out.println("1. Executar una ordre");
			System.out.println("2. Importar ordres de la base de dades");
			System.out.println("3. Exportar ordres a la base de dades");
			System.out.println("4. Executar totes les ordres");
			System.out.println("5. Llistar les ordres");
			System.out.println("6. Esborrar totes les ordres");
			System.out.println("7. Esborrar la taula de la base de dades");
			System.out.println("8. Canviar el laberint");
			System.out.println("0. Sortir");
			try {
				opcio = Integer.parseInt(reader.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Opció no vàlida! Escriu un número.");
				opcio = -1;
			}
			switch (opcio) {
			case 1:
				System.out.println("Escriu l'ordre:");
				String ordre = reader.readLine();
				list.executeOrder(ordre);
				break;
			case 2:
				list.importOrders();
				break;
			case 3:
				list.exportOrders();
				break;
			case 4:
				System.out.println("Segons de pausa entre ordres:");
				String seconds = reader.readLine();
				list.execute(seconds);
				break;
			case 5:
				list.displayOrders();
				break;
			case 6:
				list.removeOrders();
				break;
			case 7:
				list.removeDataBaseTable();
				break;
			case 8:
				System.out.println("Escriu el nom del laberint:");
				String nomLaberint = reader.readLine();
				list.setNomLaberint(nomLaberint);
				break;
			case 0:
				System.out.println("Sortint...");
				break;
			default:
				System.out.println("Opció no vàlida!");
			}
		}
        finch.quit();
	}

}
