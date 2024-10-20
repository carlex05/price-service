package com.inditex.priceservice.infrastructure.repository;

import com.inditex.priceservice.infrastructure.repository.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(
        """
        SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brandId = :brandId
        AND :applicationDate BETWEEN p.startDate AND p.endDate
        ORDER BY p.priority DESC
        """
    )
    List<PriceEntity> findTopPriorityByProductBrandAndDate(
            @Param("applicationDate") Instant applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Integer brandId);
}
