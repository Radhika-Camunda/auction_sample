package com.sample.auctions.dto.auction;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.sample.auctions.model.auction.ItemCategory;
import com.sample.auctions.model.auction.ItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyNowRequest extends AuctionRequest {

    @NotNull
    private boolean isPremium;

    @Builder
    public BuyNowRequest(String description, BigDecimal startingPrice, ItemStatus itemStatus,
                         ItemCategory itemCategory, int daysToEndTime, boolean isPremium) {
        super(description, startingPrice, itemStatus, itemCategory, daysToEndTime);
        this.isPremium = isPremium;
    }
}
