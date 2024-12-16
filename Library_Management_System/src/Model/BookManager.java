package model;

import util.CSVHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookManager {
    private String filePath;  // Dynamically set file path
    private List<Book> books = new ArrayList<>();

    // Constructor accepts the file path
    public BookManager(String filePath) throws IOException {
        this.filePath = filePath;
        loadBooks();
    }

    // Load books from CSV file
    public void loadBooks() throws IOException {
        books.clear();
        List<String[]> data = CSVHandler.readCSV(filePath);
        for (String[] row : data) {
            books.add(new Book(
                    Integer.parseInt(row[0]), 
                    row[1], row[2], row[3],
                    Integer.parseInt(row[4]), 
                    Boolean.parseBoolean(row[5])
            ));
        }
    }

    // Get all books
    public List<Book> getAllBooks() {
        return books;
    }

    // Add a new book and save to CSV
    public void addBook(Book book) throws IOException {
        books.add(book);
        saveBooks();
    }

    // Find a book by its ID
    public Optional<Book> findBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }

    // Modify an existing book
    public boolean modifyBook(int id, Book updatedBook) throws IOException {
        Optional<Book> existingBook = findBookById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.updateDetails(updatedBook);  // Update details
            saveBooks();
            return true;
        }
        return false;
    }

    // Delete a book by ID
    public boolean deleteBook(int id) throws IOException {
        boolean isRemoved = books.removeIf(book -> book.getId() == id);
        if (isRemoved) {
            saveBooks();
        }
        return isRemoved;
    }

    // Search for books by keyword
    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.matchesKeyword(keyword)) {
                result.add(book);
            }
        }
        return result;
    }

    // Save all books to CSV file
    private void saveBooks() throws IOException {
        List<String[]> data = new ArrayList<>();
        for (Book book : books) {
            data.add(new String[]{
                    String.valueOf(book.getId()), 
                    book.getTitle(), 
                    book.getAuthor(), 
                    book.getGenre(), 
                    String.valueOf(book.getPublicationYear()), 
                    String.valueOf(book.isBorrowed())
            });
        }
        CSVHandler.writeCSV(filePath, data);
    }
}
