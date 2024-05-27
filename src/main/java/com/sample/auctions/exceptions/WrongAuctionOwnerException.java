package com.sample.auctions.exceptions;

public class WrongAuctionOwnerException extends RuntimeException {

    public WrongAuctionOwnerException(String message) {
        super(message);
    }

}
