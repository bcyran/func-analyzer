package pl.bazylicyran.funcanalyzer.parsing;

/**
 * Exceptions related to mathematical expressions as in MathExpression class
 * 
 * @author Bazyli Cyran
 */
public class ExpressionException extends RuntimeException {

	private static final long serialVersionUID = -1393541031453680189L;

	/**
	 * Throws ExpressionException with message.
	 * 
	 * @param message Exception message.
	 */
	public ExpressionException(String message) {
		super(message);
	}

	/**
	 * Throws ExpressionException with message extended by token which caused
	 * the error.
	 * 
	 * @param message Exception message.
	 * @param token Faulty token.
	 */
	public ExpressionException(String message, String token) {
		super(message + " (" + token + ")");
	}

}
