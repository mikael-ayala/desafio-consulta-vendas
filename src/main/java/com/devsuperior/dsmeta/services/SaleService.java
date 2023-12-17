package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		LocalDate max = maxDateFormatter(maxDate);
		LocalDate min = minDateFormatter(minDate, max);

		List<SaleSummaryProjection> saleSummaryProjections =
				repository.searchSaleSummary(min, max);
		return saleSummaryProjections.stream().map(SaleSummaryDTO::new).collect(Collectors.toList());
	}

	public Page<SaleReportDTO> searchSaleReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate max = maxDateFormatter(maxDate);
		LocalDate min = minDateFormatter(minDate, max);

		return repository.searchSaleReport(min, max, name, pageable);
	}

	private LocalDate maxDateFormatter(String maxDate) {
		if (maxDate.isBlank()) {
			return LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			return LocalDate.parse(maxDate);
		}
	}

	private LocalDate minDateFormatter(String minDate, LocalDate maxDate) {
		if (minDate.isBlank()) {
			return maxDate.minusYears(1L);
		} else {
			return LocalDate.parse(minDate);
		}
	}
}
