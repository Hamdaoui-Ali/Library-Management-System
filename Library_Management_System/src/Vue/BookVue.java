package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.Book;
import model.BookManager;

public class BookVue {

    private JFrame frame;
    private JButton showBooksButton, addBookButton, updateBookButton, deleteBookButton, searchButton;
    private JTextField searchField, idField, titleField, authorField, genreField, yearField;
    private JComboBox<String> isBorrowedComboBox;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookManager bookManager;

    public BookVue(BookManager bookManager) {
        this.bookManager = bookManager;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Top Panel (Search)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBackground(new Color(47, 79, 79));

        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        styleButton(searchButton, new Color(100, 149, 237));

        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Middle Panel (Form and Buttons)
        JPanel middlePanel = new JPanel(new GridLayout(1, 2, 20, 10));
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField();
        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField();
        JLabel isBorrowedLabel = new JLabel("Is Borrowed:");
        isBorrowedComboBox = new JComboBox<>(new String[]{"False", "True"});

        formPanel.add(idLabel); formPanel.add(idField);
        formPanel.add(titleLabel); formPanel.add(titleField);
        formPanel.add(authorLabel); formPanel.add(authorField);
        formPanel.add(genreLabel); formPanel.add(genreField);
        formPanel.add(yearLabel); formPanel.add(yearField);
        formPanel.add(isBorrowedLabel); formPanel.add(isBorrowedComboBox);

        showBooksButton = new JButton("Show All Books");
        addBookButton = new JButton("Add a Book");
        updateBookButton = new JButton("Update a Book");
        deleteBookButton = new JButton("Delete a Book");

        styleButton(showBooksButton, new Color(70, 130, 180));
        styleButton(addBookButton, new Color(70, 130, 180));
        styleButton(updateBookButton, new Color(70, 130, 180));
        styleButton(deleteBookButton, new Color(70, 130, 180));

        buttonPanel.add(showBooksButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(updateBookButton);
        buttonPanel.add(deleteBookButton);

        middlePanel.add(formPanel);
        middlePanel.add(buttonPanel);

        // Bottom Panel (Table)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Genre", "Year", "Is Borrowed"}, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
    }

    // Getters for user input and components
    public String getSearchText() { return searchField.getText(); }
    public JTable getBookTable() { return bookTable; }
    public JButton getSearchButton() { return searchButton; }
    public JButton getShowBooksButton() { return showBooksButton; }
    public JButton getAddBookButton() { return addBookButton; }
    public JButton getUpdateBookButton() { return updateBookButton; }
    public JButton getDeleteBookButton() { return deleteBookButton; }

    public String[] getBookFormInput() {
        return new String[]{
            idField.getText(), titleField.getText(), authorField.getText(),
            genreField.getText(), yearField.getText(), (String) isBorrowedComboBox.getSelectedItem()
        };
    }

    public void clearForm() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        genreField.setText("");
        yearField.setText("");
        isBorrowedComboBox.setSelectedIndex(0);
    }

    public void setBookFormInput(String[] data) {
        idField.setText(data[0]);
        titleField.setText(data[1]);
        authorField.setText(data[2]);
        genreField.setText(data[3]);
        yearField.setText(data[4]);
        isBorrowedComboBox.setSelectedItem(data[5]);
    }

    public void displayBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                book.getId(), book.getTitle(), book.getAuthor(),
                book.getGenre(), book.getPublicationYear(), book.isBorrowed()
            });
        }
    }
}
