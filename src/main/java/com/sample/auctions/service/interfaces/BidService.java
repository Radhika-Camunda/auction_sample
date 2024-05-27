package com.sample.auctions.service.interfaces;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

import com.sample.auctions.dto.auction.BidRequest;
import com.sample.auctions.model.auction.Bid;

public interface BidService {

    Bid createBid(BidRequest bidRequest, Long auctionId, UserDetails userDetails);

    void deleteBid(Long auctionId, Long bidId, UserDetails userDetails);

    List<Bid> getBidsForAuction(Long auctionId);
}
