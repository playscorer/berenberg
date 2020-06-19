package berenberg;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import berenberg.Item.Type;

public class Library {

    // Map containing the description of the items per itemId
    private Map<Integer, Item> items;

    // Map containing the quantity of items per itemId
    private Map<Integer, Integer> inventory;

    // Map containing a list of items for a specific return date
    private SortedMap<Date, List<Item>> borrowedItems;

    public Library() {
	items = new HashMap<>();
	inventory = new HashMap<>();
	borrowedItems = new TreeMap<>();
    }

    public void readFile(String filename) throws IOException {
	Path path = Paths.get(filename);

	try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		String[] splitted = line.split(",");
		if (splitted.length != 3) {
		    continue;
		}

		int itemId = Integer.parseInt(splitted[0]);
		Type itemType = Item.Type.valueOf(splitted[1]);
		String title = splitted[2];
		Item item = new Item(itemId, itemType, title);

		items.put(item.getId(), item);
		Integer qty = inventory.get(item.getId());
		if (qty == null) {
		    inventory.put(itemId, 1);
		} else {
		    inventory.put(itemId, ++qty);
		}
	    }
	}
    }

    /**
     * Return the number of copies of an item in the inventory
     */
    public int getNbOfCopies(int itemId) {
	if (!inventory.containsKey(itemId)) {
	    return 0;
	}
	return inventory.get(itemId);
    }

    /**
     * Lists all the items of the inventory
     */
    public List<Item> getInventory() {
	List<Item> inventoryList = new ArrayList<>();
	for (Entry<Integer, Integer> entry : inventory.entrySet()) {
	    int nbOfItems = entry.getValue();
	    for (int i = 0; i < nbOfItems; i++) {
		inventoryList.add(items.get(entry.getKey()));
	    }
	}
	return inventoryList;
    }

    /**
     * Borrows an item at the specified date
     */
    public void borrowItem(int itemId, Date borrowDate) throws LibraryException {
	if (inventory.containsKey(itemId)) {
	    int qty = inventory.get(itemId);
	    if (qty > 0) {
		inventory.put(itemId, --qty);

		Date returnDate = returnDate(borrowDate);
		List<Item> itemsBorrowedDate = borrowedItems.get(returnDate);
		if (itemsBorrowedDate == null) {
		    itemsBorrowedDate = new ArrayList<>();
		    borrowedItems.put(returnDate, itemsBorrowedDate);
		}
		itemsBorrowedDate.add(items.get(itemId));

	    } else {
		throw new LibraryException("Item unavailable : " + itemId);
	    }
	} else {
	    throw new LibraryException("Item does not exist : " + itemId);
	}
    }

    /**
     * Computes the return date (7 days after the borrow date)
     */
    public Date returnDate(Date borrowDate) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(borrowDate);
	calendar.add(Calendar.DAY_OF_MONTH, 7);
	return calendar.getTime();
    }

    /**
     * Returns an item to the library
     */
    public void returnItem(int itemId) throws LibraryException {
	if (inventory.containsKey(itemId)) {
	    for (Entry<Date, List<Item>> entry : borrowedItems.entrySet()) {
		List<Item> itemsByDate = entry.getValue();
		Item item = items.get(itemId);
		if (itemsByDate.contains(item)) {
		    itemsByDate.remove(item);
		    int qty = inventory.get(itemId);
		    inventory.put(itemId, ++qty);
		    return;
		}
	    }

	    throw new LibraryException("Item cannot be returned because it has not been borrowed : " + itemId);

	} else {
	    throw new LibraryException("Item does not exist : " + itemId);
	}
    }

    /**
     * Lists all the items overdue for the current date
     */
    public List<Item> getOverdueItems(Date currentDate) {
	List<Item> overdueItemsList = new ArrayList<>();
	SortedMap<Date, List<Item>> overdueItemsMap = borrowedItems.headMap(currentDate);
	for (Entry<Date, List<Item>> entry : overdueItemsMap.entrySet()) {
	    overdueItemsList.addAll(entry.getValue());
	}
	return overdueItemsList;
    }

    public SortedMap<Date, List<Item>> getBorrowedItems() {
	return borrowedItems;
    }
}
