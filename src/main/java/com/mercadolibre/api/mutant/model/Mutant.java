package com.mercadolibre.api.mutant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mutant")
public class Mutant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mutant_id", nullable = false)
	private Long mutantId;
	@Column(name = "is_mutant", nullable = false)
	private boolean mutant;

	/**
	 * @return the mutantId
	 */
	public Long getMutantId() {
		return mutantId;
	}

	/**
	 * @param mutantId the mutantId to set
	 */
	public void setMutantId(Long mutantId) {
		this.mutantId = mutantId;
	}

	/**
	 * @return the mutant
	 */
	public boolean isMutant() {
		return mutant;
	}

	/**
	 * @param mutant the mutant to set
	 */
	public void setMutant(boolean mutant) {
		this.mutant = mutant;
	}

}
