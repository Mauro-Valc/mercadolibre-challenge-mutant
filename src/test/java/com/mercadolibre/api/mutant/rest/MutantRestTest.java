package com.mercadolibre.api.mutant.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.mercadolibre.api.mutant.business.MutantBusiness;
import com.mercadolibre.api.mutant.domain.DNASequence;
import com.mercadolibre.api.mutant.model.Mutant;
import com.mercadolibre.api.mutant.repository.MutantRepository;

@SpringBootTest
@ActiveProfiles("test")
class MutantRestTest {

	private static final String[] MUTANT_DNA = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] HUMAN_DNA = { "ATGCGA", "CCGTTC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String[] DNA_INVALID_SIZE = { "ATGCGA", "CAGTGC", "TTATGT" };
	private static final String[] DNA_INVALID_CHARACTER = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA",
			"TCACTX" };
	private static final String[] DNA_INVALID_LENGTH = { "ATGCGA", "CAGTGC", "TTATGTT", "AGAAGG", "CCCCTA", "TCACTG" };
	private static final String MUTANT_PATH = "/mutant/";
	private static final String STATS_PATH = "/stats";
	private final Gson gson = new Gson();

	private MockMvc restMock;
	@Autowired
	private MutantBusiness business;
	@Autowired
	private MutantRepository mutantRepository;
	private DNASequence dnaSequence;
	private Mutant mutant;

	@BeforeEach
	public void setUp() {
		mutantRepository.deleteAll();
		MockitoAnnotations.openMocks(this);
		this.restMock = MockMvcBuilders.standaloneSetup(new MutantRest(this.business)).build();
	}

	@Test
	void mutantValidatorMutantSuccess() throws Exception {
		createDNA(MUTANT_DNA);
		this.restMock.perform(post(URI.create(MUTANT_PATH)).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(dnaSequence).getBytes())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void mutantValidatorHumanSuccess() throws Exception {
		createDNA(HUMAN_DNA);
		this.restMock.perform(post(URI.create(MUTANT_PATH)).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(dnaSequence).getBytes())).andDo(print()).andExpect(status().isForbidden());
	}

	@Test
	void mutantValidatorInvalidSize() throws Exception {
		createDNA(DNA_INVALID_SIZE);
		this.restMock.perform(post(URI.create(MUTANT_PATH)).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(dnaSequence).getBytes())).andDo(print()).andExpect(status().isForbidden());
	}

	@Test
	void mutantValidatorInvalidCharacter() throws Exception {
		createDNA(DNA_INVALID_CHARACTER);
		this.restMock.perform(post(URI.create(MUTANT_PATH)).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(dnaSequence).getBytes())).andDo(print()).andExpect(status().isForbidden());
	}

	@Test
	void mutantValidatorInvalidlenght() throws Exception {
		createDNA(DNA_INVALID_LENGTH);
		this.restMock.perform(post(URI.create(MUTANT_PATH)).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(dnaSequence).getBytes())).andDo(print()).andExpect(status().isForbidden());
	}

	@Test
	void statsTest() throws Exception {
		createStats();
		this.restMock.perform(get(URI.create(STATS_PATH)).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk());
	}

	private void createStats() {
		for (int i = 0; i < 10; i++) {
			mutant = new Mutant();
			mutant.setMutant(i == 0);
			mutantRepository.save(mutant);
		}
	}

	private void createDNA(String[] dna) {
		dnaSequence = new DNASequence();
		dnaSequence.setDna(new ArrayList<String>(Arrays.asList(dna)));
	}

}
