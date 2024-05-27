package com.sample.auctions.model.auction;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.auctions.model.AbstractEntity;
import com.sample.auctions.model.user.User;
import com.sample.auctions.service.converter.ItemCategoryConverter;
import com.sample.auctions.service.converter.ItemStatusConverter;
import com.sample.auctions.service.validators.PriceConstraint;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.sample.auctions.service.properties.AppConstants.MAX_DESCRIPTION_LENGTH;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "auction_type")
@AllArgsConstructor
public class Auction extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "auction-sequence-generator")
    @GenericGenerator(
            name = "auction-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "auction_sequence"),
                    @Parameter(name = "initial_value", value = "7"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long auctionId;

    @Column(name = "auction_type", insertable = false, updatable = false)
    private String auctionType;

    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH, message = "Description must be provided")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @PriceConstraint
    private BigDecimal startingPrice;

    @Schema(description = "Item status")
    @Convert(converter = ItemStatusConverter.class)
    private ItemStatus itemStatus;

    @Schema(description = "Item category")
    @Convert(converter = ItemCategoryConverter.class)
    private ItemCategory itemCategory;

    @Future
    private LocalDateTime auctionEndTime;

    @NotNull
    private LocalDateTime auctionStartTime;

}
