package com.sample.auctions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sample.auctions.model.auction.Auction;

@Repository
public interface AuctionRepository extends PagingAndSortingRepository<Auction, Long>,
        JpaSpecificationExecutor<Auction>, JpaRepository<Auction, Long> {

}
