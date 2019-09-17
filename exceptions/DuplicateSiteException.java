package exceptions;

public class DuplicateSiteException extends Exception {
	
	public DuplicateSiteException() {
		super("Duplicate sites");
	}
}
