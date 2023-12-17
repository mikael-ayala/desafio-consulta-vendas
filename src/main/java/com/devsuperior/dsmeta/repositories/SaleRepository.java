package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.projection.SaleSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,
            value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS total " +
                    "FROM tb_sales INNER JOIN tb_seller " +
                    "ON tb_sales.seller_id = tb_seller.id " +
                    "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY tb_seller.name")
    List<SaleSummaryProjection> searchSaleSummary(LocalDate minDate, LocalDate maxDate);
}
