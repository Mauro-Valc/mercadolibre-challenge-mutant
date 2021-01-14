package com.mercadolibre.api.mutant.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.api.mutant.business.MutantBusiness;
import com.mercadolibre.api.mutant.domain.DNASequence;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET })
public class MutantRest {

	private MutantBusiness business;

	public MutantRest(MutantBusiness business) {
		this.business = business;
	}

	@PostMapping(value = "mutant/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> mutantValidator(@RequestBody DNASequence dna) {
		return this.business.mutantValidator(dna);
	}

}
