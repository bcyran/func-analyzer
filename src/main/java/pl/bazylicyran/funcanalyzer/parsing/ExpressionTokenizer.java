package pl.bazylicyran.funcanalyzer.parsing;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements string tokenizer.
 * 
 * Breaks string into smaller elements: numbers, operators, functions and saves
 * it in the form of a list.
 * 
 * @author Bazyli Cyran
 */
public class ExpressionTokenizer {

	/** String to tokenize. */
	private String string;

	/** Final tokens list. */
	private List<String> tokens = new LinkedList<>();

	/**
	 * Initializes variables, calls tokenize function.
	 * 
	 * @param string String to tokenize.
	 */
	public ExpressionTokenizer(String string) {
		this.string = string;

		tokenize();
	}

	/**
	 * Returns final tokens list.
	 * 
	 * @return The tokens list.
	 */
	public List<String> getTokens() {
		return tokens;
	}

	/**
	 * Tokenizes string.
	 */
	private void tokenize() {
		for (int i = 0; i < string.length(); i++) {
			char current = string.charAt(i);
			char last = ' ';

			if (i > 0) {
				last = getLast().charAt(getLast().length() - 1);
			}

			if (string.charAt(i) == ' ') { // ignore spaces
				continue;
			} else if (i > 0 && Character.isDigit(current) && (Character.isDigit(last) || last == '.')) {
				appendToLast(current);
			} else if (i > 0 && current == '.' && Character.isDigit(last)) {
				appendToLast(current);
			} else if (i > 0 && Character.isLetter(current) && Character.isLetter(last)) {
				appendToLast(current);
			} else if (i > 0 && Character.isLetter(current) && Character.isDigit(last)) {
				addNew('*');
				addNew(current);
			} else {
				addNew(current);
			}
		}
	}

	/**
	 * Returns value of last token from token list.
	 * 
	 * @return The last token.
	 */
	private String getLast() {
		return tokens.get(tokens.size() - 1);
	}

	/**
	 * Appends given char to last token.
	 * 
	 * @param val The char to append.
	 */
	private void appendToLast(char val) {
		tokens.set(tokens.size() - 1, getLast() + val);
	}

	/**
	 * Adds char as new token to list.
	 * 
	 * @param val The char to add.
	 */
	private void addNew(char val) {
		tokens.add(String.valueOf(val));
	}

}
