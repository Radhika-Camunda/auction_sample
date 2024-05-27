package com.sample.auctions.exceptions;

public class DuplicatedLoginException extends RuntimeException {

    public DuplicatedLoginException(String message) {
        super(message);
    }

}
