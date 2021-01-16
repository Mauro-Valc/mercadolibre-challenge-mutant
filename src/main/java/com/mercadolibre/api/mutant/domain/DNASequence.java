package com.mercadolibre.api.mutant.domain;

import java.util.List;

/**
 * DTO for mapping the DNA to be analyzed
 *
 */
public class DNASequence {

	private List<String> dna;

	/**
	 * @return the dna
	 */
	public List<String> getDna() {
		return dna;
	}

	/**
	 * @param dna the dna to set
	 */
	public void setDna(List<String> dna) {
		this.dna = dna;
	}

	@Override
	public String toString() {
		return "DNASequence [dna=" + dna + "]";
	}

}