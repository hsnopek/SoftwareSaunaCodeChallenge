package hr.hsnopek.softwaresaunacodechallenge.common.exceptions;

public class NoStartException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2556521740374907615L;

	public NoStartException(String errorMessage) {
	      super(errorMessage);
	  }
}
