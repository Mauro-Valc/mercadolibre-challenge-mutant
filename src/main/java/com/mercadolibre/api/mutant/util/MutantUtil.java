package com.mercadolibre.api.mutant.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MutantUtil {
	
	public static final List<String> DNA = Arrays.asList("AAAA", "TTTT", "CCCC", "GGGG");
	public static final Pattern NITROGENOUS_BASE_PATTERN = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);
	public static final int SEQUENCE_SIZE_MUTANT = 4;

}
