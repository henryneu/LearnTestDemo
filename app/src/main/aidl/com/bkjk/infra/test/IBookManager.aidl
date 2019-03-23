// IBookManager.aidl
package com.bkjk.infra.test;

// Declare any non-default types here with import statements
import com.bkjk.infra.test.Book;
import com.bkjk.infra.test.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
