package com.sample.auctions.model.auction;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item category")
public enum ItemCategory {
    BOOK,
    CLOTHES,
    ELECTRONICS,
    FURNITURE,
    SPORT,
    OTHER
}
