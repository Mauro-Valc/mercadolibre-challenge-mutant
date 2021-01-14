package com.mercadolibre.api.mutant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.api.mutant.model.Mutant;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Long> {

}
