package com.inditex.priceservice.application;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PriceFilter;
import com.inditex.priceservice.domain.repository.PriceRepository;
import com.inditex.priceservice.domain.usecase.PriceQuery;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PriceQueryUseCase implements PriceQuery {

    private final PriceRepository priceRepository;
    private final Validator validator;

    public PriceQueryUseCase(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
        var factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public Optional<Price> getPriceForProductBrandAndDate(PriceFilter filter) {
        if(filter == null || !validator.validate(filter).isEmpty())
            throw new IllegalArgumentException();
        return priceRepository.findMostPriorityPriceForProductBrandAndDate(filter);
    }

}
