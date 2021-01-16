package com.mercadolibre.api.mutant.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DNAStatus {

	@JsonProperty("count_mutant_dna")
	private Long countMutantDNA;
	@JsonProperty("count_human_dna")
	private Long countHumanDNA;
	private double ratio;

	public DNAStatus() {
	}

	public DNAStatus(Long countMutantDNA, Long countHumanDNA) {
		this.countMutantDNA = countMutantDNA;
		this.countHumanDNA = countHumanDNA;
		this.ratio = Math.round(((double) this.countMutantDNA / this.countHumanDNA) * 100.0) / 100.0;
	}

	/**
	 * @return the countMutantDNA
	 */
	public Long getCountMutantDNA() {
		return countMutantDNA;
	}

	/**
	 * @param countMutantDNA the countMutantDNA to set
	 */
	public void setCountMutantDNA(Long countMutantDNA) {
		this.countMutantDNA = countMutantDNA;
	}

	/**
	 * @return the countHumanDNA
	 */
	public Long getCountHumanDNA() {
		return countHumanDNA;
	}

	/**
	 * @param countHumanDNA the countHumanDNA to set
	 */
	public void setCountHumanDNA(Long countHumanDNA) {
		this.countHumanDNA = countHumanDNA;
	}

	/**
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
