package com.sample.auctions.dto.auction;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.sample.auctions.model.auction.ItemCategory;
import com.sample.auctions.model.auction.ItemStatus;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BiddingRequest extends AuctionRequest {

    @NotNull
    private boolean isLimited;

    @Builder
    public BiddingRequest(String description, BigDecimal startingPrice, ItemStatus itemStatus,
                          ItemCategory itemCategory, int daysToEndTime, boolean isLimited) {
        super(description, startingPrice, itemStatus, itemCategory, daysToEndTime);
        this.isLimited = isLimited;
    }
}
