package vault;

/*
  In addition the class that implements the Vault interface must include 
  two constructors:
	 public Vault();
	 public Vault(Encryptor e);
*/

public interface Vault {
	
	/**
	 * Add a new user to the vault (with no site passwords). For example, a 
	 * username and password is supplied, and the system does no verification 
	 * or checking except that the username and password must be in correct 
	 * formats, and the username cannot already be in the vault.
	 * 
	 * @param username The username to be added
	 * @param password The password to be associated with this user
	 * @throws InvalidUsernameException The supplied username is invalid 
	 * @throws InvalidPasswordException The supplied password is invalid 
	 * @throws DuplicateUserException   The username is already in the vault 
	 */
	void addNewUser(String username, String password) 
			throws 	exceptions.InvalidUsernameException,
					exceptions.InvalidPasswordException, 
					exceptions.DuplicateUserException;
	
	/**
	 * Adds a new site to the vault for the user, generates, stores, and 
	 * returns a password for that site. For example, if a user is creating 
	 * an account at the site "amazon" for the first time, he/she calls the 
	 * vault with the site name, and the site makes up a password for the 
	 * user/site, returns the (plain text password to the user, and stores 
	 * the (encrypted) password -- all stored passwords in the vault.
	 * 
	 * @param username The username requesting the new site password
	 * @param password Password for the username
	 * @param sitename Name of the site for which the user is requesting a password
	 * @return 		   A new (plaintext) password for the requested site
	 * @throws DuplicateSiteException    There is already a site stored for this
	 *                                   user
	 * @throws UserNotFoundException     There is no such user in the vault
	 * @throws UserLockedOutException    The user has been locked out due to too
	 *                                   many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match the
	 *                                   user's vault password
	 * @throws InvalidSiteException      The site name supplied is invalid
	 */
	String addNewSite(String username, String password, String sitename)
			throws 	exceptions.DuplicateSiteException, 
					exceptions.UserNotFoundException,
					exceptions.UserLockedOutException, 
					exceptions.PasswordMismatchException, 
					exceptions.InvalidSiteException;
	
	/**
	 * Generate, store, and return an updated password for a site associated 
	 * with the user. For example, the user wants to change his/her password on 
	 * "amazon," and the system generates a new password, stores an encrypted 
	 * version for the user, and returns the plaintext version.
	 * 
	 * @param username The username requesting the new site password
	 * @param password Password for the username
	 * @param sitename Name of the site for which the user is requesting a password
	 * @return An updated (plaintext) password for the requested site
	 * @throws SiteNotFoundException     The user has no password associated with
	 *                                   this site
	 * @throws UserNotFoundException     There is no such user in the vault
	 * @throws UserLockedOutException    The user has been locked out due to too
	 *                                   many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match the
	 *                                   user's vault password
	 */
	String updateSitePassword(String username, String password, String sitename)
			throws 	exceptions.SiteNotFoundException, 
					exceptions.UserNotFoundException,
					exceptions.UserLockedOutException, 
					exceptions.PasswordMismatchException;
	
	/**
	 * Retrieve the (plaintext) password for the user for the requested site. 
	 * For example, the user supplies the name of a site, and if she has a 
	 * stored password for the site, it is returned in plain text.
	 * 
	 * @param username The username requesting the site password
	 * @param password Password for the username
	 * @param sitename Name of the site for which the user is requesting a password
	 * @return The (plaintext) password for the requested site
	 * @throws SiteNotFoundException     The user has no password associated with
	 *                                   this site
	 * @throws UserNotFoundException     There is no such user in the vault
	 * @throws UserLockedOutException    The user has been locked out due to too
	 *                                   many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match the
	 *                                   user's vault password
	 */
	String retrieveSitePassword(String username, String password, String sitename)
			throws 	exceptions.SiteNotFoundException, 
					exceptions.UserNotFoundException,
					exceptions.UserLockedOutException, 
					exceptions.PasswordMismatchException;

}
