package com.example.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Exception.BookNotFoundException;
import com.example.Model.Book;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();

    // 1. Add a book; reject duplicate IDs.
    public String addBook(Book book) {
        boolean exists = books.stream()
                .anyMatch(b -> b.getBookId().equals(book.getBookId()));
        if (exists) {
            return "Book ID already exists";
        }
        books.add(book);
        return "Book added successfully";
    }

    // Extra: get all books.
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // 2. Search a book by ID.
    public Book getBookById(int bookId) {
        return books.stream()
                .filter(b -> b.getBookId() == bookId)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(
                        "Book not found with ID: " + bookId));
    }

    // 3. Update availability.
    public String updateAvailability(int bookId, boolean available) {
        Book book = getBookById(bookId);
        book.setAvailable(available);
        return "Book availability updated successfully";
    }

    // 4. Delete a book.
    public String deleteBook(int bookId) {
        Book book = getBookById(bookId);
        books.remove(book);
        return "Book deleted successfully";
    }
}