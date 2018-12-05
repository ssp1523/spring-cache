package com.example.service;

import com.example.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBook(ISBN);
        Book book3 = bookService.findBook(ISBN);
        assert book1 == book2;
        assert book1 == book3;
    }


    @Test
    public void findBookSync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Book> book1 = executorService.submit(() -> bookService.findBookSync(ISBN));
        Future<Book> book2 = executorService.submit(() -> bookService.findBookSync(ISBN));
        assert book1.get() == book2.get();

    }


    @Test
    public void findBookCacheManager() {
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBookCacheManager(ISBN);
        assert book1 != book2;
    }

    @Test
    public void findBookCondition() {
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBookCondition(ISBN, 99);
        Book book3 = bookService.findBookCondition(ISBN, 200);
        assert book1 != book2;
        assert book3 == book1;
    }
    @Test
    public void findBookUnless() {
        Book book3 = bookService.findBookUnless(ISBN, 200);
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBookUnless(ISBN, 99);
        Book book4 = bookService.findBookUnless(ISBN, 200);
        assert book1 == book2;
        assert book3 != book1;
        assert book4 == book1;
    }

    @Test
    public void findBookCacheResolver() {
        Book book1= bookService.findBookCacheResolver(ISBN);
        Book book2 = bookService.findBookCacheResolver(ISBN);

        assert book1 != book2;
    }
    @Test
    public void findBookKeyGenerator() {
        Book book1 = bookService.findBook(ISBN);
        Book book2 = bookService.findBookKeyGenerator(ISBN);
        assert book1 != book2;
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