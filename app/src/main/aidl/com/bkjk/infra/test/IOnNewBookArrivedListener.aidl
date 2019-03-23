// IOnNewBookArrivedListener.aidl
package com.bkjk.infra.test;

// Declare any non-default types here with import statements
import com.bkjk.infra.test.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void onNewBookArrived(in Book newBook);
}
