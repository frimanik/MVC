package org.example.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="author")
public class Author {
    @Id
    long id;
    @NotNull
    String name;
    @OneToMany
    List<Book> books;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
        books = new ArrayList<>();
    }

    public Author() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addNewBook(Book book){books.add(book);}
}
