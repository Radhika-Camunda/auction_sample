package com.sample.auctions.dto.auction;

import static com.sample.auctions.service.properties.AppConstants.MAX_DESCRIPTION_LENGTH;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionUpdate {

    @Schema(example = "new sample description")
    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH, message = "Description must be provided")
    private String description;
}
