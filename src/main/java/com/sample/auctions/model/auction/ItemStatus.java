package com.sample.auctions.model.auction;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item status")
public enum ItemStatus {
    NEW,
    USED
}
