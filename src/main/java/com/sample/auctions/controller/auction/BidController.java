package com.sample.auctions.controller.auction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.auctions.dto.auction.BidRequest;
import com.sample.auctions.model.auction.Bid;
import com.sample.auctions.security.CurrentUser;
import com.sample.auctions.service.interfaces.BidService;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BidController {

    private final BidService bidService;

    @Operation(summary = "Create new bid")
    @PostMapping("/{auctionId}/bids")
    public ResponseEntity<Bid> createBid(@CurrentUser
                                                 UserDetails userDetails, @PathVariable Long auctionId,
                                         @RequestBody @Valid BidRequest bidRequest) {

        return new ResponseEntity<>(bidService.createBid(bidRequest, auctionId, userDetails),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Delete bid by id")
    @DeleteMapping("/{auctionId}/bids/{bidId}")
    public ResponseEntity<String> deleteBid(@CurrentUser
                                                 UserDetails userDetails, @PathVariable Long auctionId,
                                         @PathVariable Long bidId) {

        bidService.deleteBid(auctionId, bidId, userDetails);
        return ResponseEntity.ok("Bid deleted successfully");
    }

    @GetMapping("/{auctionId}/bids")
    @Operation(summary = "Get all bids for auction (only for seller)")
    public ResponseEntity<List<Bid>> getBidsForAuction(@PathVariable Long auctionId) {
        return new ResponseEntity<>(bidService.getBidsForAuction(auctionId), HttpStatus.OK);
    }

}
