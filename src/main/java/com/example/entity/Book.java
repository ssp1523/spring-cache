package com.example.entity;

import java.io.Serializable;

/**
 * 书籍
 * @author: sunshaoping
 * @date: Create by in 15:05 2018-11-16
 */
public class Book implements Serializable {

    private Long id;

    private String bookName;

    private String isbn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public Book clone() {
        Book book = new Book();
        book.setId(this.id);
        book.setIsbn(this.isbn);
        book.setBookName(this.bookName);
        return book;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
