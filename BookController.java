package com.example.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.Model.Book;
import com.example.Service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
        String result = bookService.addBook(book);
        if (result.equals("Book ID already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable @Positive int bookId) {
        return bookService.getBookById(bookId);
    }

    @PatchMapping("/{bookId}/availability")
    public String updateAvailability(@PathVariable @Positive int bookId,
                                     @RequestParam boolean available) {
        return bookService.updateAvailability(bookId, available);
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable @Positive int bookId) {
        return bookService.deleteBook(bookId);
    }
}