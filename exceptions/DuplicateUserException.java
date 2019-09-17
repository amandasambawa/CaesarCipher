package exceptions;

public class DuplicateUserException extends Exception {

	public DuplicateUserException() {
		super("Duplicate users");
	}
}
