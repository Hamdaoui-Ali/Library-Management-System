package controller;

import model.Book;
import model.BookManager;
import vue.BookVue;

import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class BookController {
    private BookManager bookManager;
    private BookVue bookVue;

    public BookController() throws IOException {
        // Initialize the BookManager and BookVue
        this.bookManager = new BookManager("src\\books.csv");
        this.bookVue = new BookVue(bookManager);

        // Register button listeners
        initializeButtonListeners();
    }

    private void initializeButtonListeners() {
        // Show All Books Button
        bookVue.getShowBooksButton().addActionListener(e -> showAllBooks());

        // Add Book Button
        bookVue.getAddBookButton().addActionListener(e -> addBook());

        // Update Book Button
        bookVue.getUpdateBookButton().addActionListener(e -> updateBook());

        // Delete Book Button
        bookVue.getDeleteBookButton().addActionListener(e -> deleteBook());

        // Search Button
        bookVue.getSearchButton().addActionListener(e -> searchBooks());
    }

    private void showAllBooks() {
        try {
            List<Book> books = bookManager.getAllBooks();
            bookVue.displayBooks(books);
        } catch (Exception e) {
            showError("Error loading books: " + e.getMessage());
        }
    }

    private void addBook() {
        try {
            String[] input = bookVue.getBookFormInput();
            Book newBook = new Book(
                input[1], // title
                input[2], // author
                input[3], // genre
                Integer.parseInt(input[4]), // year
                Boolean.parseBoolean(input[5]) // isBorrowed
            );
            bookManager.addBook(newBook);
            showSuccess("Book added successfully!");
            showAllBooks();
            bookVue.clearForm();
        } catch (IOException | NumberFormatException e) {
            showError("Error adding book: " + e.getMessage());
        }
    }

    private void updateBook() {
        try {
            String[] input = bookVue.getBookFormInput();
            int id = Integer.parseInt(input[0]);
            Book updatedBook = new Book(
                id, // ID
                input[1], // title
                input[2], // author
                input[3], // genre
                Integer.parseInt(input[4]), // year
                Boolean.parseBoolean(input[5]) // isBorrowed
            );
            bookManager.modifyBook(id, updatedBook);
            showSuccess("Book updated successfully!");
            showAllBooks();
            bookVue.clearForm();
        } catch (IOException | NumberFormatException e) {
            showError("Error updating book: " + e.getMessage());
        }
    }

    private void deleteBook() {
        try {
            String[] input = bookVue.getBookFormInput();
            int id = Integer.parseInt(input[0]);
            bookManager.deleteBook(id);
            showSuccess("Book deleted successfully!");
            showAllBooks();
            bookVue.clearForm();
        } catch (IOException | NumberFormatException e) {
            showError("Error deleting book: " + e.getMessage());
        }
    }

    private void searchBooks() {
        try {
            String keyword = bookVue.getSearchText();
            List<Book> results = bookManager.searchBooks(keyword);
            bookVue.displayBooks(results);
        } catch (Exception e) {
            showError("Error searching books: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new BookController();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error initializing the application: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
