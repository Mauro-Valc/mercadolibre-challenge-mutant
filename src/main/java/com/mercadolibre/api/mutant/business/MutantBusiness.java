package com.mercadolibre.api.mutant.business;

import org.springframework.http.ResponseEntity;

import com.mercadolibre.api.mutant.domain.DNASequence;
import com.mercadolibre.api.mutant.domain.DNAStatus;

public interface MutantBusiness {

	/**
	 * Allows to validate if it is a mutant through its DNA
	 * 
	 * @param dna
	 * @return status 200 if is mutan, else 403
	 */
	ResponseEntity<String> mutantValidator(DNASequence dna);

	/**
	 * allows you to check the scores of the request performed
	 * 
	 * @return
	 */
	ResponseEntity<DNAStatus> stats();

}
