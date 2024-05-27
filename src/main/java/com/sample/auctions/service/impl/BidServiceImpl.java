package com.sample.auctions.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.auctions.dto.auction.BidRequest;

import com.sample.auctions.exceptions.IncorrectDateException;
import com.sample.auctions.exceptions.IncorrectOperationException;
import com.sample.auctions.exceptions.IncorrectPriceException;
import com.sample.auctions.exceptions.NotFoundException;
import com.sample.auctions.exceptions.WrongAuctionOwnerException;
import com.sample.auctions.model.auction.Bid;
import com.sample.auctions.model.auction.Bidding;
import com.sample.auctions.model.user.User;
import com.sample.auctions.repository.AuctionRepository;
import com.sample.auctions.repository.BidRepository;
import com.sample.auctions.repository.UserRepository;
import com.sample.auctions.service.interfaces.BidService;
import com.sample.auctions.service.mapper.BidDtoMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
   
    private final UserRepository userRepository;

    public Bid createBid(BidRequest bidRequest, Long auctionId, UserDetails userDetails) {
        Bidding bidding = (Bidding) auctionRepository.findById(auctionId).orElseThrow(
                () -> new NotFoundException(
                        "Auction with id " + auctionId + " not found"));
        Bid bid = BidDtoMapper.mapToBid(bidRequest);

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException(
                        "User with username " + userDetails.getUsername() + " not found"));
        bid.setUser(user);

        if (bid.getBidPrice().compareTo(bidding.getCurrentPrice()) <= 0) {
            throw new IncorrectPriceException("Bid price must be greater than offer price");
        }
        if (LocalDateTime.now().isAfter(bidding.getAuctionEndTime())) {
            throw new IncorrectDateException("Auction has ended");
        }

        bidding.setCurrentPrice(bid.getBidPrice());
        bidding.addBid(bid);
        auctionRepository.save(bidding);

        List<Bid> bidsForGivenOffer = bidding.getBids();
        List<String> emailBids =
                bidsForGivenOffer.stream().map(b -> b.getUser().getEmail())
                        .collect(Collectors.toList());
      
        return bidding.getBids().get(bidding.getBids().size() - 1);
    }

    @Override
    public void deleteBid(Long auctionId, Long bidId, UserDetails userDetails) {

        Bid bidToDelete = bidRepository.findById(bidId).orElseThrow(
                () -> new NotFoundException("Bid with id " + bidId + " not found"));

        Bidding bidding = (Bidding) auctionRepository.findById(auctionId).orElseThrow(
                () -> new NotFoundException("Auction with id " + auctionId + " not found"));

        List<Bid> bidsForGivenOffer = bidding.getBids();
        Bid highestPriceBid =
                bidsForGivenOffer.stream().max(Comparator.comparing(Bid::getBidPrice)).get();

        if (!highestPriceBid.getBidId().equals(bidId)) {
            throw new IncorrectOperationException("You can only delete bid with highest price");
        }

        if (!bidToDelete.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new WrongAuctionOwnerException("You can only delete your own bid");
        }

        if (bidsForGivenOffer.size() == 1) {
            bidding.setCurrentPrice(bidding.getStartingPrice());
        } else {
            bidding.setCurrentPrice(highestPriceBid.getBidPrice());
        }

        List<String> emailBids =
                bidsForGivenOffer.stream().map(b -> b.getUser().getEmail())
                        .collect(Collectors.toList());

       
        bidding.removeBid(bidToDelete);
        auctionRepository.save(bidding);
        bidRepository.delete(bidToDelete);
    }

    @Override
    public List<Bid> getBidsForAuction(Long auctionId) {
        return bidRepository.findByBiddingAuctionId(auctionId);
    }


}
