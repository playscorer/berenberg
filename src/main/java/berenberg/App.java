package berenberg;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Main Class
 */
public class App {
    public static void main(String[] args) throws IOException, LibraryException {
	Library library = new Library();
	library.readFile("resource/library.csv");

	printInventory(library);

	Calendar calendar = Calendar.getInstance();
	calendar.set(2015, Calendar.SEPTEMBER, 5);
	Date borrowDate = calendar.getTime();

	printBorrowedItem(library, 4, borrowDate);

	printInventory(library);

	calendar.set(2015, Calendar.SEPTEMBER, 12);
	borrowDate = calendar.getTime();

	printBorrowedItem(library, 8, borrowDate);

	printInventory(library);

	printOverdueItems(library, borrowDate);

	calendar.set(2015, Calendar.SEPTEMBER, 15);
	Date currentDate = calendar.getTime();

	printOverdueItems(library, currentDate);

	calendar.set(2015, Calendar.SEPTEMBER, 20);
	currentDate = calendar.getTime();

	printOverdueItems(library, currentDate);

	printReturnItem(library, 4);

	printOverdueItems(library, currentDate);
    }

    private static void printReturnItem(Library library, int itemId) throws LibraryException {
	System.out.println("ItemId returned : " + itemId);
	library.returnItem(itemId);
	System.out.println();
    }

    private static void printOverdueItems(Library library, Date currentDate) {
	System.out.println("Overdue items on : " + currentDate);
	List<Item> overdueItems = library.getOverdueItems(currentDate);
	for (Item item : overdueItems) {
	    System.out.println(item);
	}
	System.out.println();
    }

    private static void printBorrowedItem(Library library, int itemId, Date borrowDate) throws LibraryException {
	library.borrowItem(itemId, borrowDate);
	System.out.println("Borrowed itemId : " + itemId + " on " + borrowDate);
	System.out.println();
    }

    private static void printInventory(Library library) {
	System.out.println("Library inventory :");
	List<Item> inventory = library.getInventory();
	for (Item item : inventory) {
	    System.out.println(item);
	}
	System.out.println();
    }
}
