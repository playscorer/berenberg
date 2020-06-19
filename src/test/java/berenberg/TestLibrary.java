package berenberg;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class TestLibrary extends TestCase {

    protected void setUp() throws Exception {
	super.setUp();
    }

    public void testReadFile() {
	// ARRANGE
	Library library = new Library();

	// ACT
	try {
	    library.readFile("resource/library.csv");
	} catch (IOException e) {
	    fail(e.getMessage());
	}

	// ASSERT
	assertEquals(3, library.getNbOfCopies(7));
	assertEquals(2, library.getNbOfCopies(6));
	assertEquals(13, library.getInventory().size());
    }

    public void testReturnDate() {
	// ARRANGE
	Library library = new Library();
	Calendar calendar = Calendar.getInstance();
	calendar.set(2015, Calendar.JUNE, 27);
	Date borrowDate = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 4);
	Date expectedReturnDate = calendar.getTime();

	// ACT
	Date returnDate = library.returnDate(borrowDate);

	// ASSERT
	assertEquals(expectedReturnDate, returnDate);
    }

    public void testBorrowItem() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");
	Date borrowDate = new Date();

	// ACT
	try {
	    library.borrowItem(7, borrowDate);
	    library.borrowItem(8, borrowDate);
	} catch (LibraryException e) {
	    fail(e.getMessage());
	}

	// ASSERT
	assertEquals(2, library.getNbOfCopies(7));
	assertEquals(0, library.getNbOfCopies(8));
	assertEquals(2, library.getBorrowedItems().get(library.returnDate(borrowDate)).size());
    }

    public void testBorrowItem2() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");
	Date borrowDate = new Date();

	// ACT
	try {
	    library.borrowItem(8, borrowDate);
	    library.borrowItem(8, borrowDate);
	    fail("Exception expected");
	} catch (LibraryException e) {
	}

	// ASSERT
	assertTrue(true);
    }

    public void testBorrowItem3() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");
	Date borrowDate = new Date();

	// ACT
	try {
	    library.borrowItem(9, borrowDate);
	    fail("Exception expected");
	} catch (LibraryException e) {
	}

	// ASSERT
	assertTrue(true);
    }

    public void testReturnItem() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");

	// ACT
	try {
	    library.returnItem(3);
	    fail("Exception expected");
	} catch (LibraryException e) {
	}

	// ASSERT
	assertTrue(true);
    }

    public void testReturnItem2() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");

	// ACT
	try {
	    library.returnItem(12);
	    fail("Exception expected");
	} catch (LibraryException e) {
	}

	// ASSERT
	assertTrue(true);
    }

    public void testReturnItem3() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");

	Calendar calendar = Calendar.getInstance();
	calendar.set(2015, Calendar.JUNE, 27);
	Date borrowDate = calendar.getTime();

	try {
	    library.borrowItem(4, borrowDate);
	} catch (LibraryException e) {
	    fail(e.getMessage());
	}

	// ACT
	try {
	    library.returnItem(4);
	    library.returnItem(4);
	    fail("Exception expected");
	} catch (LibraryException e) {
	}

	// ASSERT
	assertTrue(true);
    }

    public void testReturnItem4() throws IOException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");

	Calendar calendar = Calendar.getInstance();
	calendar.set(2015, Calendar.JUNE, 27);
	Date borrowDate = calendar.getTime();

	try {
	    library.borrowItem(4, borrowDate);
	} catch (LibraryException e) {
	    fail(e.getMessage());
	}

	// ACT
	try {
	    library.returnItem(4);
	} catch (LibraryException e) {
	    fail(e.getMessage());
	}

	// ASSERT
	assertEquals(3, library.getNbOfCopies(4));
    }

    public void testOverdueItems() throws IOException, LibraryException {
	// ARRANGE
	Library library = new Library();
	library.readFile("resource/library.csv");

	Calendar calendar = Calendar.getInstance();
	calendar.set(2015, Calendar.JUNE, 27);
	Date borrowDate = calendar.getTime();
	calendar.set(2015, Calendar.JUNE, 29);
	Date borrowDate2 = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 15);
	Date borrowDate3 = calendar.getTime();

	calendar.set(2015, Calendar.JUNE, 30);
	Date currentDate = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 4);
	Date currentDate2 = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 5);
	Date currentDate3 = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 6);
	Date currentDate4 = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 7);
	Date currentDate5 = calendar.getTime();
	calendar.set(2015, Calendar.JULY, 30);
	Date currentDate6 = calendar.getTime();

	// ACT
	library.borrowItem(4, borrowDate);
	library.borrowItem(4, borrowDate);
	library.borrowItem(4, borrowDate2);
	library.borrowItem(8, borrowDate2);
	library.borrowItem(3, borrowDate3);

	// ASSERT
	assertEquals(0, library.getOverdueItems(currentDate).size());
	assertEquals(0, library.getOverdueItems(currentDate2).size());
	assertEquals(2, library.getOverdueItems(currentDate3).size());
	assertEquals(2, library.getOverdueItems(currentDate4).size());
	assertEquals(4, library.getOverdueItems(currentDate5).size());
	assertEquals(5, library.getOverdueItems(currentDate6).size());
    }

}
