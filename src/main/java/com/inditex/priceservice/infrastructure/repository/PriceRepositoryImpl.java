package com.inditex.priceservice.infrastructure.repository;

import com.inditex.priceservice.domain.model.Price;
import com.inditex.priceservice.domain.model.PriceFilter;
import com.inditex.priceservice.domain.repository.PriceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    public PriceRepositoryImpl(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public Optional<Price> findTopPriorityByProductBrandAndDate(PriceFilter filter) {
        var priceEntity = jpaPriceRepository.findTopPriorityByProductBrandAndDate(filter.applicationAt(), filter.productId(), filter.brandId());
        return priceEntity.stream().map(entity -> new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        )).findFirst();
    }
}
