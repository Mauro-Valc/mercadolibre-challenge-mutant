package com.mercadolibre.api.mutant.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.api.mutant.business.MutantBusiness;
import com.mercadolibre.api.mutant.domain.DNASequence;
import com.mercadolibre.api.mutant.domain.DNAStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET })
@Api("Api for mutant validation")
public class MutantRest {

	private MutantBusiness business;

	public MutantRest(MutantBusiness business) {
		this.business = business;
	}

	@ApiOperation(value = "Allows validation of DNA sequences to identify mutants", httpMethod = "POST")
	@ApiParam(value = "Json object with DNA sequence", name = "dna", required = true, type = "JSON")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "DNA received is from a mutant", response = ResponseEntity.class),
			@ApiResponse(code = 403, message = "The data was incorrect or the DNA is from a human", response = ResponseEntity.class) })
	@PostMapping(value = "mutant/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> mutantValidator(@RequestBody DNASequence dna) {
		return this.business.mutantValidator(dna);
	}

	@ApiOperation(value = "Allows you to consult the history of DNA validation requests ", httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The statistics query was successful", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "The data was incorrect", response = ResponseEntity.class) })
	@GetMapping(value = "stats", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DNAStatus> stats() {
		return this.business.stats();
	}

}
