package hr.hsnopek.softwaresaunacodechallenge.common.exceptions;

public class EndOfMapReachedException extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = 5118940172502725109L;

	public EndOfMapReachedException(String errorMessage) {
	      super(errorMessage);
	  }
}

