package org.example.SpringLibrary;

import org.example.Controllers.LibraryController;
import org.example.Entities.Author;
import org.example.Entities.Book;
import org.example.Exceptions.BookNotFoundException;
import org.example.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpringLibraryApplicationTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryController libraryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooks() {

        Author author = new Author(0,"Dirk");

        Book book =  new Book(0,"Ice",author);
        Book book1 = new Book(1,"Silent snow",author);
        Book book2 = new Book(2,"Howling wind",author);
        author.addNewBook(book);
        author.addNewBook(book1);
        author.addNewBook(book2);

        List<Book>books = List.of(book,book1,book2);

        Page<Book> page = new PageImpl<>(books);
        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Book> result = libraryController.getBooks(0, 2, "title");

        assertThat(result).isEqualTo(page);
    }

    @Test
    void testGetBook_BookExists() {

        Book book = new Book(0,"Hobbit",new Author(0,"J.R.R Tolkien"));
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        Book result = libraryController.getBook(0L);

        assertThat(result).isEqualTo(book);
    }

    @Test
    void testGetBook_BookNotFound() {

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryController.getBook(1L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book with this id does not exist");
    }

    @Test
    void testAddBook() {

        Book book = new Book(0,"Hobbit",new Author(0,"J.R.R Tolkien"));
        when(bookRepository.save(any())).thenReturn(book);

        Book result = libraryController.addBook(book);

        assertThat(result).isEqualTo(book);
    }

    @Test
    void testUpdateBook() {

        Book book = new Book();
        when(bookRepository.existsById(any())).thenReturn(true);
        when(bookRepository.save(any())).thenReturn(book);

        Book result = libraryController.updateBook(book, 1L);

        assertThat(result).isEqualTo(book);
    }

    @Test
    void testDeleteBook_BookExists() {

        when(bookRepository.existsById(any())).thenReturn(true);

        libraryController.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}
