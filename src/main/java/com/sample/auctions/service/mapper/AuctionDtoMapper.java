package com.sample.auctions.service.mapper;

import java.time.LocalDateTime;

import com.sample.auctions.dto.auction.AuctionRequest;
import com.sample.auctions.dto.auction.BiddingRequest;
import com.sample.auctions.dto.auction.BuyNowRequest;
import com.sample.auctions.model.auction.Auction;
import com.sample.auctions.model.auction.Bidding;
import com.sample.auctions.model.auction.BuyNow;

public class AuctionDtoMapper {

    private AuctionDtoMapper() {
    }

    public static Auction mapAuctionRequestToAuction(AuctionRequest auctionRequest) {
        if (auctionRequest instanceof BuyNowRequest) {
            BuyNowRequest buyNowRequest = (BuyNowRequest) auctionRequest;
            return BuyNow.builder()
                    .auctionEndTime(LocalDateTime.now().plusDays(buyNowRequest.getDaysToEndTime()))
                    .description(buyNowRequest.getDescription())
                    .itemCategory(buyNowRequest.getItemCategory())
                    .itemStatus(buyNowRequest.getItemStatus())
                    .startingPrice(buyNowRequest.getStartingPrice())
                    .startingPrice(buyNowRequest.getStartingPrice())
                    .isPremium(buyNowRequest.isPremium())
                    .auctionStartTime(LocalDateTime.now())
                    .build();
        } else {
            BiddingRequest biddingRequest = (BiddingRequest) auctionRequest;
            return Bidding.builder()
                    .auctionEndTime(LocalDateTime.now().plusDays(biddingRequest.getDaysToEndTime()))
                    .description(biddingRequest.getDescription())
                    .itemCategory(biddingRequest.getItemCategory())
                    .currentPrice(biddingRequest.getStartingPrice())
                    .itemStatus(biddingRequest.getItemStatus())
                    .startingPrice(biddingRequest.getStartingPrice())
                    .isLimited(biddingRequest.isLimited())
                    .auctionStartTime(LocalDateTime.now())
                    .build();
        }
    }

}
