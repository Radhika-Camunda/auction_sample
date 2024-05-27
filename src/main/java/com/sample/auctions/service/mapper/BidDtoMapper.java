package com.sample.auctions.service.mapper;

import java.time.LocalDateTime;

import com.sample.auctions.dto.auction.BidRequest;
import com.sample.auctions.model.auction.Bid;

public class BidDtoMapper {

    private BidDtoMapper() {
    }

    public static BidRequest mapToBidRequest(Bid bid) {
        return BidRequest.builder()
                .bidPrice(bid.getBidPrice())
                .build();
    }

    public static Bid mapToBid(BidRequest bidRequest) {

        return Bid.builder()
                .bidPrice(bidRequest.getBidPrice())
                .bidTime(LocalDateTime.now())
                .build();
    }


}
