package com.sample.auctions.model.auction;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sample.auctions.model.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("buy_now")
public class BuyNow extends Auction {

    private boolean isPremium;

    @Builder
    public BuyNow(long id, BigDecimal startingPrice, String description,
                  ItemStatus itemStatus,
                  ItemCategory itemCategory, LocalDateTime auctionEndTime,
                  LocalDateTime auctionStartTime, boolean isPremium) {
        super(id, "buy_now", description, new User(), startingPrice, itemStatus, itemCategory,
                auctionEndTime,
                auctionStartTime);
        this.isPremium = isPremium;
    }
}
