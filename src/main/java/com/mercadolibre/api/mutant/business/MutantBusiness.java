package com.mercadolibre.api.mutant.business;

import org.springframework.http.ResponseEntity;

import com.mercadolibre.api.mutant.domain.DNASequence;

public interface MutantBusiness {

	ResponseEntity<String> mutantValidator(DNASequence dna);

}
