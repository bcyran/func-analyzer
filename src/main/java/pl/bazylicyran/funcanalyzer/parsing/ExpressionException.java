package pl.bazylicyran.funcanalyzer.parsing;

/**
 * Exceptions related to mathematical expressions as in MathExpression class
 * 
 * @author Bazyli Cyran
 */
public class ExpressionException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = -7593048227322563214L;

	/** Token which caused the exception. */
	private String currentToken;

	/** Token directly before the one which caused exception. */
	private String lastToken;

	/**
	 * Throws ExpressionException with message.
	 * 
	 * @param message Exception message.
	 */
	public ExpressionException(String message) {
		super(message);
	}

	/**
	 * Throws ExpressionException with given message and saves current token.
	 * 
	 * @param message Exception message.
	 * @param currentToken Faulty token.
	 */
	public ExpressionException(String message, String currentToken) {
		super(message);

		this.currentToken = currentToken;
	}

	/**
	 * Throws ExpressionException with given message and saves current and last
	 * token.
	 * 
	 * @param message Exception message.
	 * @param currentToken Faulty token.
	 * @param lastToken Last token.
	 */
	public ExpressionException(String message, String currentToken, String lastToken) {
		super(message);

		this.currentToken = currentToken;
		this.lastToken = lastToken;
	}

	/**
	 * Returns token which caused exception.
	 * 
	 * @return Faulty token.
	 */
	public String getToken() {
		return currentToken;
	}

	/**
	 * Returns token before the one which caused the exception.
	 * 
	 * @return Token before the faulty token.
	 */
	public String getLastToken() {
		return lastToken;
	}

}
