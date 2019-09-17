package exceptions;

public class PasswordMismatchException extends Exception {

	public PasswordMismatchException() {
		super("Passwords do not match");
	}
}
