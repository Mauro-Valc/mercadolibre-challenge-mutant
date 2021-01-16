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
import com.mercadolibre.api.mutant.domain.DNAStatus;
import com.mercadolibre.api.mutant.model.Mutant;
import com.mercadolibre.api.mutant.repository.MutantRepository;

/**
 * Class with all the business logic for the validation of mutants and result of
 * the requests
 *
 */
@Service
public class MutantBusinessImpl implements MutantBusiness {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(MutantBusinessImpl.class);
	@Autowired
	private MutantRepository mutantRepository;

	/**
	 * @see MutantBusiness#mutantValidator(DNASequence)
	 */
	public ResponseEntity<String> mutantValidator(DNASequence dna) {
		LOGGER.info("Init mutantValidator with DNA: {}", dna);
		ResponseEntity<String> response = null;
		try {
			String[] array = dna.getDna().toArray(new String[0]);
			boolean mutant = isMutant(array);
			LOGGER.debug("is mutant: {}", mutant);
			this.createMutantRecord(mutant);
			if (mutant) {
				response = new ResponseEntity<>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Error in mutantValidator: {}", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
		LOGGER.info("Finish mutantValidator with response: {}", response);
		return response;
	}

	/**
	 * @see MutantBusiness#status()
	 */
	public ResponseEntity<DNAStatus> stats() {
		LOGGER.info("Init stats");
		ResponseEntity<DNAStatus> response = null;
		DNAStatus status = null;
		try {
			status = this.mutantRepository.findStatistics();
			response = new ResponseEntity<DNAStatus>(status, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("error in stats", e.getMessage());
			response = new ResponseEntity<DNAStatus>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("Finsh stats with response: {}", response);
		return response;
	}

	/**
	 * Save the result of DNA request
	 * 
	 * @param isMutant
	 */
	private void createMutantRecord(boolean isMutant) {
		Mutant mutant = new Mutant();
		mutant.setMutant(isMutant);
		this.mutantRepository.save(mutant);
	}

	/**
	 * Validate if the sent DNA sequence corresponds to that of a mutant, add lists
	 * of strings corresponding to the rows, columns and diagonals of the matrix and
	 * then search if the established sequences are found in this list
	 * 
	 * @param dnaSecuence
	 * @return true if is a mutant, else false
	 * @throws Exception
	 */
	private boolean isMutant(String[] dnaSecuence) throws Exception {
		List<String> dna = new ArrayList<String>(Arrays.asList(dnaSecuence));
		List<String> results = new ArrayList<>();
		char[][] matrixDNA = null;
		matrixDNA = createMatrixDNA(dna);
		results.addAll(dna);
		this.addAllColumns(matrixDNA, results);
		addValidDiagonals(matrixDNA, results);
		addValidContraDiagonals(matrixDNA, results);
		long mutant = results.stream().filter(a -> DNA.stream().filter(w -> a.contains(w)).count() > 0)
				.peek(a -> LOGGER.debug("Mutant sequence: {}", a)).count();
		return (mutant > 1);
	}

	/**
	 * Allows you to add all diagonals of more than 4 characters to the list of
	 * results
	 * 
	 * @param matrixDNA
	 * @param result
	 */
	private static void addValidDiagonals(char[][] matrixDNA, List<String> result) {
		int startColumn = 0;
		int startRow = 0;
		int lastRow = matrixDNA.length - 1;
		int row;
		while (startRow < matrixDNA.length || startColumn < matrixDNA[0].length) {
			String diagonal = "";
			if (startRow < matrixDNA.length) {
				row = startRow++;
			} else {
				row = lastRow;
				startColumn++;
			}
			for (int col = startColumn; col < matrixDNA[0].length && row >= 0; col++) {
				diagonal = diagonal + matrixDNA[row--][col];
			}
			if (diagonal.length() >= SEQUENCE_SIZE_MUTANT) {
				result.add(diagonal);
			}
		}
	}

	/**
	 * Allows you to add all contra diagonals of more than 4 characters to the list
	 * of results
	 * 
	 * @param matrixDNA
	 * @param result
	 */
	private static void addValidContraDiagonals(char[][] matrixDNA, List<String> result) {
		int startColumn = matrixDNA.length - 1;
		int startRow = 0;
		int lastRow = matrixDNA.length - 1;
		int row;
		while (startRow < matrixDNA.length || startColumn >= 0) {
			String contraDiagonal = "";
			if (startRow < matrixDNA.length) {
				row = startRow++;
			} else {
				row = lastRow;
				startColumn--;
			}
			for (int col = startColumn; col >= 0 && row >= 0; col--) {
				contraDiagonal = contraDiagonal + matrixDNA[row--][col];
			}
			if (contraDiagonal.length() >= SEQUENCE_SIZE_MUTANT) {
				result.add(contraDiagonal);
			}
		}
	}

	/**
	 * Allows add all columns of the matix
	 * 
	 * @param matrixDNA
	 * @param result
	 */
	private void addAllColumns(char[][] matrixDNA, List<String> result) {
		for (int i = 0; i < matrixDNA[0].length; i++) {
			result.add(this.getColumn(matrixDNA, i));
		}
	}

	/**
	 * Get a specific column
	 * 
	 * @param array
	 * @param index
	 * @return
	 */
	private String getColumn(char[][] array, int index) {
		String column = "";
		for (int i = 0; i < array[0].length; i++) {
			column = column + array[i][index];
		}
		return column;
	}

	/**
	 * Allows create Matrix char[][] from a list<>
	 * 
	 * @param dnaSequence
	 * @return
	 * @throws Exception
	 */
	private char[][] createMatrixDNA(List<String> dnaSequence) throws Exception {
		LOGGER.debug("inti createMatrixDNA 2D vectors");
		int vectorLength = dnaSequence.size();
		if (vectorLength < SEQUENCE_SIZE_MUTANT) {
			throw new Exception("The DNA size must be greater that 4");
		}
		char[][] dna = new char[vectorLength][vectorLength];

		for (int i = 0; i < vectorLength; i++) {
			String dnaRow = dnaSequence.get(i);
			validateDNA(vectorLength, dnaRow);
			dna[i] = dnaRow.toUpperCase().toCharArray();
		}
		return dna;
	}

	/**
	 * Validate that the size and letters in the DNA are as expected
	 * 
	 * @param vectorLength
	 * @param dnaRow
	 * @throws Exception
	 */
	private void validateDNA(int vectorLength, String dnaRow) throws Exception {
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
