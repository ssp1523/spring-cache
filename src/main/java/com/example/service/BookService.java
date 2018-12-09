package com.example.service;

import com.example.RedisCacheable;
import com.example.entity.Book;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 书籍 Service
 * @author: sunshaoping
 * @date: Create by in 15:08 2018-11-16
 */

@Service
@CacheConfig(cacheNames = "books")
public class BookService {

    @RedisCacheable(expires = "PT1M")
    public Book findBook(String isbn) {
        System.out.println("查询,isbn=" + isbn);
        return createBook(isbn);
    }

    @Cacheable(key = "#isbn", sync = true)
    public Book findBookSync(String isbn) {
        System.out.println("查询,isbn=" + isbn);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return createBook(isbn);
    }

    private Book createBook(String isbn) {
        Book book = new Book();
        book.setBookName("儿童故事集");
        book.setId(new Random().nextLong());
        book.setIsbn(isbn);
        return book;
    }


    @Cacheable(key = "#isbn", condition = "#totalPages > 100")
    public Book findBookCondition(String isbn, int totalPages) {
        return createBook(isbn);
    }

    @Cacheable(key = "#isbn", unless = "#totalPages > 100")
    public Book findBookUnless(String isbn, int totalPages) {
        return createBook(isbn);
    }


    @CachePut(key = "#book.isbn")
    public Book updateBook(Book book) {
        Book book1 = createBook(book.getIsbn());
        book1.setBookName(book.getBookName());
        return book1;
    }

    @CacheEvict(key = "#isbn")
    public void removeBook(String isbn) {
        System.out.println("删除一本书: isbn = " + isbn);
    }
}
