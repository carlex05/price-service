package com.inditex.priceservice;

import com.inditex.priceservice.application.PriceQueryUseCase;
import com.inditex.priceservice.domain.repository.PriceRepository;
import com.inditex.priceservice.domain.usecase.PriceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PriceServiceApplication {

	public static void main(String[] args) {
            
		SpringApplication.run(PriceServiceApplication.class, args);
	}

	@Autowired
	PriceRepository repository;

	@Bean
	PriceQuery getPriceQuery() {
		return new PriceQueryUseCase(repository);
	}

}
