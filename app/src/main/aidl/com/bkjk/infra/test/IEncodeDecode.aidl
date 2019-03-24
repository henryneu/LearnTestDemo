// IEncodeDecode.aidl
package com.bkjk.infra.test;

// Declare any non-default types here with import statements

interface IEncodeDecode {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String encode(String password);
    String decode(String password);
}
