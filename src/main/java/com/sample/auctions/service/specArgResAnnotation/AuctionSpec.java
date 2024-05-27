package com.sample.auctions.service.specArgResAnnotation;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import com.sample.auctions.model.auction.Auction;


@And({
        @Spec(path = "description", params = "descr", spec = Like.class),
        @Spec(path = "itemCategory", spec = EqualIgnoreCase.class),
        @Spec(path = "startingPrice", params = {"priceFrom", "priceTo"},
                spec = Between.class),
        @Spec(path = "auctionType", spec = EqualIgnoreCase.class),
        @Spec(path = "itemStatus", spec = EqualIgnoreCase.class)
})
public interface AuctionSpec extends Specification<Auction> {

}
