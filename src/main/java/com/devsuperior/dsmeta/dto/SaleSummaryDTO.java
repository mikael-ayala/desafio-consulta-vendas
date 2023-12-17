package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projection.SaleSummaryProjection;

public record SaleSummaryDTO(String sellerName, Double total) {
    public SaleSummaryDTO(SaleSummaryProjection projection) {
        this(projection.getName(), projection.getTotal());
    }
}
