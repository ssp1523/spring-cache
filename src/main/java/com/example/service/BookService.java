package com.example.service;

import com.example.entity.Book;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 书籍 Service
 * @author: sunshaoping
 * @date: Create by in 15:08 2018-11-16
 */
@CacheConfig(cacheNames = "books")
@Service
public class BookService {


    /**
     * 查询书籍
     */
    @Cacheable
    public Book findBook(String isbn) {
        return getBook(isbn);
    }

    private Book getBook(String isbn) {
        Book book = new Book();
        book.setBookName("儿童故事集");
        book.setId(1L);
        book.setIsbn(isbn);
        return book;
    }


    @Cacheable(key = "#isbn", condition = "#checkWarehouse")
    public Book findBook(String isbn, boolean checkWarehouse, boolean includeUsed) {
        return getBook(isbn);
    }


    @Cacheable(keyGenerator = "myKeyGenerator")
    public Book findBook(String isbn, boolean checkWarehouse) {
        return getBook(isbn);
    }

    public Book saveBook(Book book) {
        book.setId(2L);
        return book;
    }

    @CachePut(key = "#book.isbn")
    public Book updateBook(Book book) {
        return book.clone();
    }

    @CacheEvict(key = "#isbn")
    public void removeBook(String isbn) {
        System.out.println("删除一本书: isbn = " + isbn);
    }
}
