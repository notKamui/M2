package com.td.either;

public class RequestSizeException extends Exception {
    public RequestSizeException() {
        super("Request too long");
    }
}

