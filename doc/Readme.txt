Library Homework Assignment

1. Design

There are five classes :
- Library that represents the library and contains all operations
- Item that represents an item
- LibraryException that describes a functional exception
- App the main class
- TestLibrary containing the unitary tests

The Library class contains 3 structures :
- a Map containing the description of the items per itemId
- a Map containing the quantity of items per itemId
- a Map containing a list of items that should be returned no later than the specific date (Key)

The call to the method headMap of SortedMap is used to return a subMap containing all the overdue items.


2. Assumptions

A specific item as one unique id. It cannot exist another different item with the same id.


3. Instructions

-> to execute the tests use the TestLibrary
-> to execute the program, run the App class