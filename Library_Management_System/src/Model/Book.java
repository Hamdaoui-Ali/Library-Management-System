package model;

import java.io.IOException;
import java.util.List;

import util.CSVHandler;

public class Book {
    //private static int nextId = 1; // Static variable to track the next available ID
    private int id;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private boolean isBorrowed;

    // Constructor with ID
    public Book(int id, String title, String author, String genre, int publicationYear, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isBorrowed = isBorrowed;
    }

    // Constructor without ID
    public Book(String title, String author, String genre, int publicationYear, boolean isBorrowed) {
        this.id = (getMaxIdFromCSV() + 1); // Increment the static nextId for new books
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isBorrowed = isBorrowed;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getPublicationYear() { return publicationYear; }
    public boolean isBorrowed() { return isBorrowed; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public void setBorrowed(boolean isBorrowed) { this.isBorrowed = isBorrowed; }

    // Set nextId dynamically based on CSV content
//    public static void setNextId(int nextIdValue) {
//        nextId = nextIdValue;
//    }
    
    // Method to get the maximum ID from the CSV file
    private int getMaxIdFromCSV() {
        int maxId = 0;
        try {
            List<String[]> data = CSVHandler.readCSV("src\\books.csv");
            for (String[] details : data) {
                int id = Integer.parseInt(details[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    // Method to update book details
    public void updateDetails(Book updatedBook) {
        this.title = updatedBook.getTitle();
        this.author = updatedBook.getAuthor();
        this.genre = updatedBook.getGenre();
        this.publicationYear = updatedBook.getPublicationYear();
        this.isBorrowed = updatedBook.isBorrowed();
    }

    // Method to check if any attribute matches the keyword
    public boolean matchesKeyword(String keyword) {
        return String.valueOf(id).contains(keyword) ||
               title.toLowerCase().contains(keyword.toLowerCase()) ||
               author.toLowerCase().contains(keyword.toLowerCase()) ||
               genre.toLowerCase().contains(keyword.toLowerCase()) ||
               String.valueOf(publicationYear).contains(keyword) ||
               String.valueOf(isBorrowed).toLowerCase().contains(keyword.toLowerCase());
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Book{" +
                "ID=" + id +
                ", Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", Genre='" + genre + '\'' +
                ", Publication Year=" + publicationYear +
                ", Is Borrowed=" + isBorrowed +
                '}';
    }
}
