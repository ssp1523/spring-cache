package com.example.service;

import com.example.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * book test
 * @author: sunshaoping
 * @date: Create by in 15:13 2018-11-16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    private static final String ISBN = "1234-5678";
    @Autowired
    BookService bookService;

    @Test
    public void findBook() {
        System.out.println(Thread.currentThread().getName());
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBook(ISBN);
        Book book3 = bookService.findBook(ISBN);
        assert book1 == book2;
        assert book1 == book3;
    }

    @Test
    public void findBook1() {
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBook(ISBN, true, false);
        assert book1 == book2;
    }

    @Test
    public void findBookCondition() {
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBook(ISBN, false, false);
        assert book1 != book2;
    }

    @Test
    public void findBook2() {
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBook(ISBN, false);
        assert book1 == book2;
    }

    @Test
    public void saveBook() {

    }

    @Test
    public void removeBook() {
        Book book1 = bookService.findBook(ISBN);
        bookService.removeBook(ISBN);
        Book book2 = bookService.findBook(ISBN);
        assert book1 != book2;
    }

    @Test
    public void updateBook() {
        Book book = new Book();
        book.setIsbn(ISBN);
        book.setBookName("JAVA-编程思想");
        Book book1 = bookService.updateBook(book);
        Book book2 = bookService.findBook(ISBN);
        assert book1 == book2;
        assert book2.getBookName().equals("JAVA-编程思想");
    }
}