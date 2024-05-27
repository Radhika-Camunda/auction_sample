package com.sample.auctions.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.auctions.model.auction.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByBiddingAuctionId(Long biddingId);
}
