package com.sample.auctions.model.auction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sample.auctions.model.user.User;
import com.sample.auctions.service.validators.PriceConstraint;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("bidding")
public class Bidding extends Auction {

    private boolean isLimited;

    @PriceConstraint
    private BigDecimal currentPrice;

    @OneToMany(mappedBy = "bidding",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    @JsonManagedReference
    private List<Bid> bids;

    @Builder
    public Bidding(Long id, BigDecimal startingPrice, String description,
                   ItemStatus itemStatus,
                   ItemCategory itemCategory, LocalDateTime auctionEndTime,
                   LocalDateTime auctionStartTime, boolean isLimited, BigDecimal currentPrice) {
        super(id, "bidding", description, new User(), startingPrice, itemStatus,
                itemCategory, auctionEndTime,
                auctionStartTime);
        this.isLimited = isLimited;
        this.currentPrice = currentPrice;
        this.bids = new ArrayList<>();
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setBidding(this);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
        bid.setBidding(null);
    }
}
