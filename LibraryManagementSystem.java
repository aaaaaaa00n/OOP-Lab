import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Custom Exceptions
class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String message) {
        super(message);
    }
}

class InvalidItemException extends Exception {
    public InvalidItemException(String message) {
        super(message);
    }
}

// Abstract superclass LibraryItem
abstract class LibraryItem {
    protected String title;
    protected String itemId;
    protected boolean isBorrowed;
    
    public LibraryItem(String title, String itemId) {
        this.title = title;
        this.itemId = itemId;
        this.isBorrowed = false;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract void displayInfo();
    
    // Common methods
    public void borrowItem() throws ItemNotAvailableException {
        if (isBorrowed) {
            throw new ItemNotAvailableException("Item '" + title + "' (ID: " + itemId + ") is already borrowed!");
        }
        isBorrowed = true;
        System.out.println("Successfully borrowed: " + title);
    }
    
    public void returnItem() throws ItemNotAvailableException {
        if (!isBorrowed) {
            throw new ItemNotAvailableException("Item '" + title + "' (ID: " + itemId + ") was not borrowed!");
        }
        isBorrowed = false;
        System.out.println("Successfully returned: " + title);
    }
    
    // Getters
    public String getTitle() {
        return title;
    }
    
    public String getItemId() {
        return itemId;
    }
    
    public boolean isBorrowed() {
        return isBorrowed;
    }
}

// Book subclass
class Book extends LibraryItem {
    private String author;
    private String isbn;
    
    public Book(String title, String itemId, String author, String isbn) {
        super(title, itemId);
        this.author = author;
        this.isbn = isbn;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== BOOK ===");
        System.out.println("Title: " + title);
        System.out.println("Item ID: " + itemId);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Status: " + (isBorrowed ? "BORROWED" : "AVAILABLE"));
        System.out.println("============");
    }
}

// Magazine subclass
class Magazine extends LibraryItem {
    private int issueNumber;
    
    public Magazine(String title, String itemId, int issueNumber) {
        super(title, itemId);
        this.issueNumber = issueNumber;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== MAGAZINE ===");
        System.out.println("Title: " + title);
        System.out.println("Item ID: " + itemId);
        System.out.println("Issue Number: " + issueNumber);
        System.out.println("Status: " + (isBorrowed ? "BORROWED" : "AVAILABLE"));
        System.out.println("================");
    }
}

// DVD subclass
class DVD extends LibraryItem {
    private int duration; // in minutes
    
    public DVD(String title, String itemId, int duration) {
        super(title, itemId);
        this.duration = duration;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== DVD ===");
        System.out.println("Title: " + title);
        System.out.println("Item ID: " + itemId);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Status: " + (isBorrowed ? "BORROWED" : "AVAILABLE"));
        System.out.println("===========");
    }
}

// Main Library Management System
public class LibraryManagementSystem {
    private static ArrayList<LibraryItem> libraryItems = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    // Log error to file using try-with-resources
    private static void logError(String errorMessage) {
        try (FileWriter writer = new FileWriter("library_error_log.txt", true)) {
            writer.write(java.time.LocalDateTime.now() + " - " + errorMessage + "\n");
        } catch (IOException e) {
            System.err.println("Failed to write to error log: " + e.getMessage());
        }
    }
    
    // Initialize library with sample items
    private static void initializeLibrary() {
        libraryItems.add(new Book("The Great Gatsby", "B001", "F. Scott Fitzgerald", "978-0-7432-7356-5"));
        libraryItems.add(new Book("To Kill a Mockingbird", "B002", "Harper Lee", "978-0-06-112008-4"));
        libraryItems.add(new Book("1984", "B003", "George Orwell", "978-0-452-28423-4"));
        libraryItems.add(new Magazine("National Geographic", "M001", 245));
        libraryItems.add(new Magazine("TIME Magazine", "M002", 52));
        libraryItems.add(new DVD("The Matrix", "D001", 136));
        libraryItems.add(new DVD("Inception", "D002", 148));
    }
    
    // Find item by ID
    private static LibraryItem findItemById(String itemId) throws InvalidItemException {
        for (LibraryItem item : libraryItems) {
            if (item.getItemId().equalsIgnoreCase(itemId)) {
                return item;
            }
        }
        throw new InvalidItemException("Invalid item ID: " + itemId);
    }
    
    // Display all items
    private static void displayAllItems() {
        System.out.println("\n=== LIBRARY INVENTORY ===");
        for (LibraryItem item : libraryItems) {
            item.displayInfo();
            System.out.println();
        }
    }
    
    // Borrow item
    private static void borrowItem() {
        try {
            System.out.print("\nEnter item ID to borrow: ");
            String itemId = scanner.nextLine().trim();
            
            LibraryItem item = findItemById(itemId);
            item.borrowItem();
            System.out.println("✓ Operation completed successfully!");
            
        } catch (InvalidItemException e) {
            String errorMsg = "INVALID ITEM: " + e.getMessage();
            System.err.println("✗ Error: " + errorMsg);
            logError(errorMsg);
        } catch (ItemNotAvailableException e) {
            String errorMsg = "ITEM NOT AVAILABLE: " + e.getMessage();
            System.err.println("✗ Error: " + errorMsg);
            logError(errorMsg);
        }
    }
    
    // Return item
    private static void returnItem() {
        try {
            System.out.print("\nEnter item ID to return: ");
            String itemId = scanner.nextLine().trim();
            
            LibraryItem item = findItemById(itemId);
            item.returnItem();
            System.out.println("✓ Operation completed successfully!");
            
        } catch (InvalidItemException e) {
            String errorMsg = "INVALID ITEM: " + e.getMessage();
            System.err.println("✗ Error: " + errorMsg);
            logError(errorMsg);
        } catch (ItemNotAvailableException e) {
            String errorMsg = "ITEM NOT AVAILABLE: " + e.getMessage();
            System.err.println("✗ Error: " + errorMsg);
            logError(errorMsg);
        }
    }
    
    // Main menu
    private static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   LIBRARY MANAGEMENT SYSTEM        ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. Display all items");
        System.out.println("2. Borrow an item");
        System.out.println("3. Return an item");
        System.out.println("4. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    public static void main(String[] args) {
        // Initialize library with items
        initializeLibrary();
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║  WELCOME TO LIBRARY SYSTEM         ║");
        System.out.println("╚════════════════════════════════════╝");
        
        try {
            boolean running = true;
            
            while (running) {
                displayMenu();
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        displayAllItems();
                        break;
                    case "2":
                        borrowItem();
                        break;
                    case "3":
                        returnItem();
                        break;
                    case "4":
                        running = false;
                        System.out.println("\nThank you for using the Library Management System!");
                        break;
                    default:
                        System.out.println("✗ Invalid choice. Please try again.");
                }
            }
        } finally {
            // Always display "Session closed" in finally block
            System.out.println("\n═══════════════════════════════════");
            System.out.println("Session closed");
            System.out.println("═══════════════════════════════════");
            scanner.close();
        }
    }
}
