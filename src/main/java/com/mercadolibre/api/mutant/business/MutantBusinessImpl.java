package com.mercadolibre.api.mutant.business;

import static com.mercadolibre.api.mutant.util.MutantUtil.DNA;
import static com.mercadolibre.api.mutant.util.MutantUtil.NITROGENOUS_BASE_PATTERN;
import static com.mercadolibre.api.mutant.util.MutantUtil.SEQUENCE_SIZE_MUTANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mercadolibre.api.mutant.domain.DNASequence;
import com.mercadolibre.api.mutant.model.Mutant;
import com.mercadolibre.api.mutant.repository.MutantRepository;

@Service
public class MutantBusinessImpl implements MutantBusiness {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(MutantBusinessImpl.class);
	@Autowired
	private MutantRepository mutantRepository;

	public ResponseEntity<String> mutantValidator(DNASequence dna) {
		ResponseEntity<String> response = null;
		try {
			String[] array = dna.getDna().toArray(new String[0]);
			boolean mutant = isMutant(array);
			this.createMutantRecord(array.toString(), mutant);
			if (mutant)  {
				response = new ResponseEntity<>(HttpStatus.OK);
			}else {
				response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
		return response;
	}

	private void createMutantRecord(String dna, boolean isMutant) {
		Mutant mutant = new Mutant();
		mutant.setDna(dna);
		mutant.setMutant(isMutant);
		this.mutantRepository.save(mutant);
	}

	private boolean isMutant(String[] array) throws Exception {
		List<String> dna = new ArrayList<String>(Arrays.asList(array));
		List<String> results = new ArrayList<>();
		char[][] matrixDNA = null;
		results.addAll(dna);
		matrixDNA = loadDNAStructure(dna);
		this.addAllColumns(matrixDNA, results);
		addValidDiagonals(matrixDNA, results);
		addValidContraDiagonals(matrixDNA, results);
		long mutant = results.stream().filter(a -> DNA.stream().filter(w -> a.contains(w)).count() > 0).count();
		return (mutant > 1);
	}

	private static void addValidDiagonals(char[][] a, List<String> result) {
		int startColumn = 0;
		int startRow = 0;
		int lastRow = a.length - 1;
		int row;
		while (startRow < a.length || startColumn < a[0].length) {
			String diagonal = "";
			if (startRow < a.length) {
				row = startRow++;
			} else {
				row = lastRow;
				startColumn++;
			}
			for (int col = startColumn; col < a[0].length && row >= 0; col++) {
				diagonal = diagonal + a[row--][col];
			}
			if (diagonal.length() >= SEQUENCE_SIZE_MUTANT) {
				result.add(diagonal);
			}
		}
	}

	private static void addValidContraDiagonals(char[][] a, List<String> result) {
		int startColumn = a.length - 1;
		int startRow = 0;
		int lastRow = a.length - 1;
		int row;
		while (startRow < a.length || startColumn >= 0) {
			String contraDiagonal = "";
			if (startRow < a.length) {
				row = startRow++;
			} else {
				row = lastRow;
				startColumn--;
			}
			for (int col = startColumn; col >= 0 && row >= 0; col--) {
				contraDiagonal = contraDiagonal + a[row--][col];
			}
			if (contraDiagonal.length() >= SEQUENCE_SIZE_MUTANT) {
				result.add(contraDiagonal);
			}
		}
	}

	private void addAllColumns(char[][] matrixDNA, List<String> result) {
		for (int i = 0; i < matrixDNA[0].length; i++) {
			result.add(this.getColumn(matrixDNA, i));
		}
	}

	private String getColumn(char[][] array, int index) {
		String column = "";
		for (int i = 0; i < array[0].length; i++) {
			column = column + array[i][index];
		}
		return column;
	}

	private char[][] loadDNAStructure(List<String> dnaSequence) throws Exception {
		LOGGER.debug("Load the DNA structure into a Two-dimensional vectors.");
		int vectorLength = dnaSequence.size();
		if (vectorLength < SEQUENCE_SIZE_MUTANT) {
			throw new Exception();
		}
		char[][] dna = new char[vectorLength][vectorLength];

		for (int i = 0; i < vectorLength; i++) {
			String dnaRow = dnaSequence.get(i);
			validateDNAConsistency(vectorLength, dnaRow);
			dna[i] = dnaRow.toUpperCase().toCharArray();
		}
		return dna;
	}

	private void validateDNAConsistency(int vectorLength, String dnaRow) throws Exception {
		if (dnaRow.length() != vectorLength) {
			LOGGER.error("The length of the DNA sequences must be the same size. Expected {}, found {}: {} ",
					vectorLength, dnaRow.length(), dnaRow);
			throw new Exception("The length of the DNA sequences must be the same size.");
		} else if (!NITROGENOUS_BASE_PATTERN.matcher(dnaRow).matches()) {
			LOGGER.error("The only valid characters are A, T, C e G. Found {}", dnaRow);
			throw new Exception("The only valid characters are A, T, C e G.");
		}
	}

}
