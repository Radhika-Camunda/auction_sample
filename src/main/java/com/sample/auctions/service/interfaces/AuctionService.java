package com.sample.auctions.service.interfaces;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;

import com.sample.auctions.dto.auction.AuctionRequest;
import com.sample.auctions.dto.auction.AuctionUpdate;
import com.sample.auctions.model.auction.Auction;

public interface AuctionService {

    Auction createAuction(AuctionRequest auctionRequest, UserDetails userDetails);

    List<Auction> getAllAuctions(Specification<Auction> spec, int pageNo, String sortBy, String sortDir);

    void deleteAuction(Long id, UserDetails userDetails);

    Auction updateAuction(Long id, AuctionUpdate auctionUpdate, UserDetails userDetails);

    Auction getAuctionById(Long id);
}

