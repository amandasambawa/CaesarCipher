package validator;

import exceptions.InvalidPasswordException;
import exceptions.InvalidSiteException;
import exceptions.InvalidUsernameException;

public class Validator {
	
	private static final String LOWERCASE_PATTERN = "[a-z]{6,12}";
	private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[A-Za-z])(?=.*[!@#$%^&]).{6,15}";
	
	/**
	 * Checks if the username is valid (between 6 and 12 lowercase letters)
	 *  
	 * @param username The username to check if valid
	 */
	public static boolean isValidUsername(String username) throws exceptions.InvalidUsernameException{
		 if (username.matches(LOWERCASE_PATTERN))
			 return true;
		 else 
			 throw new InvalidUsernameException();
	}
	
	/**
	 * Checks if the sitename is valid (between 6 and 12 lowercase letters)
	 *  
	 * @param sitename The sitename to check if valid
	 */
	public static boolean isValidSite(String sitename) throws exceptions.InvalidSiteException {
		if (sitename.matches(LOWERCASE_PATTERN))
			return true;		
		else
			throw new InvalidSiteException();
	}
	
	/**
	 * Checks if the password satisfies these conditions: 
	 *  Be between 6 and 15 characters long
	 *  Contain at least one letter (can be upper or lowercase) and one digit (0-9)
	 *  Contain at least one special character from the set !@#$%^&
	 *  
	 * @param password The password to check if valid
	 */
	public static boolean isValidPassword(String password) throws exceptions.InvalidPasswordException {
		if (password.matches(PASSWORD_PATTERN))
			return true;
		else 
			throw new InvalidPasswordException();
	}
}
