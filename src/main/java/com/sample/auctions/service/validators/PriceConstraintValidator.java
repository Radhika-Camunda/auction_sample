package com.sample.auctions.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.sample.auctions.service.properties.AppConstants.MAX_PRICE;

import java.math.BigDecimal;

public class PriceConstraintValidator implements ConstraintValidator<PriceConstraint, BigDecimal> {

    @Override
    public void initialize(PriceConstraint constraintAnnotation) { }

    @Override
    public boolean isValid(BigDecimal bigDecimal,
                           ConstraintValidatorContext constraintValidatorContext) {
        return bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) > 0 && bigDecimal.compareTo(BigDecimal.valueOf(
                MAX_PRICE)) < 0
                && bigDecimal.scale() <= 2;
    }

}
