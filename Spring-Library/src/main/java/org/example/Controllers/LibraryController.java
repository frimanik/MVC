package org.example.Controllers;

import jakarta.validation.Valid;
import org.example.Entities.Book;
import org.example.Exceptions.BookNotFoundException;
import org.example.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LibraryController {

    private final BookRepository bookRepository;

    @Autowired
    public LibraryController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/getBooks")
    public Page<Book> getBooks(@RequestParam int page, @RequestParam int size,@RequestParam String sort) {
        Sort sortBy = Sort.by(Sort.Direction.fromString("DESC"),sort);
        Pageable pageable = PageRequest.of(page, size,sortBy);
        return bookRepository.findAll(pageable);
    }

    @GetMapping("/getBook/{id}")
    public Book getBook(@PathVariable  Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with this id does not exist"));
    }

    @PostMapping("/add")
    public Book addBook(@Valid @RequestBody Book book){
        return bookRepository.save(book);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@Valid @RequestBody Book book, @PathVariable Long id){
        if(!bookRepository.existsById(id)){throw new BookNotFoundException("Book with this id does not exist");}
        return bookRepository.save(book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id){
        if(!bookRepository.existsById(id)){throw new BookNotFoundException("Book with this id does not exist");}
        bookRepository.deleteById(id);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
