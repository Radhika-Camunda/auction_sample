package com.sample.auctions.dto.auction;

import java.math.BigDecimal;

import com.sample.auctions.service.validators.PriceConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest {

    @PriceConstraint
    private BigDecimal bidPrice;

}
