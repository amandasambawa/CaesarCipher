package exceptions;

public class UserLockedOutException extends Exception {

	public UserLockedOutException() {
		super("User is locked out");
	}
}
