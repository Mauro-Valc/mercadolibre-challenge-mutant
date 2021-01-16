package com.mercadolibre.api.mutant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mercadolibre.api.mutant.domain.DNAStatus;
import com.mercadolibre.api.mutant.model.Mutant;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Long> {

	@Query(value = "select new com.mercadolibre.api.mutant.domain.DNAStatus(count(case when m.mutant = true then 1 else null end), count(case when m.mutant = false then 1 else null end)) from Mutant m")
	DNAStatus findStatistics();

}
