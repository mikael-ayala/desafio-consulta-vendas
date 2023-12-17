package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> searchSaleSummary(String minDate, String maxDate) {
		LocalDate min;
		LocalDate max;

		if (maxDate.isBlank()) {
			max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			max = LocalDate.parse(maxDate);
		}

		if (minDate.isBlank()) {
			min = max.minusYears(1L);
		} else {
			min = LocalDate.parse(minDate);
		}

		List<SaleSummaryProjection> saleSummaryProjections =
				repository.searchSaleSummary(min, max);
		return saleSummaryProjections.stream().map(SaleSummaryDTO::new).collect(Collectors.toList());
	}
}
