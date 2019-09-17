package vault;

import encrypt.CaesarCipher;
import validator.Validator;
import exceptions.*;

import java.util.Random;
import java.util.HashMap;
import javafx.util.Pair;
import java.util.Date;

public class PasswordVault implements Vault{
	
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMS = "0123456789";
	private static final String SPECIAL_CHARS = "!@#$%^&";
	private static final int PASSWORD_CHAR_COUNT = 15;
	private static final int MAX_FAILED_LOGIN = 3;
	private static final int MS_IN_10MIN = 600000; // 600000 milliseconds = 10 minutes
	
	private CaesarCipher encryptor;
	private HashMap<String, String> usersPasswordsMap;
	private HashMap<String, Integer> usersLoginMap;
	private HashMap<Pair<String, String>, String> sitePasswordsMap;
	private HashMap<String, Long> userLockedOutMap;
	
	/**
	 * Default constructor for PasswordVault
	 */
	public PasswordVault() {
		usersPasswordsMap = new HashMap<String, String>();
		usersLoginMap = new HashMap<String, Integer>();
		sitePasswordsMap = new HashMap<Pair<String, String>, String>();
		userLockedOutMap = new HashMap<String, Long>();
		
		encryptor = new CaesarCipher();
	}
	
	/**
	 * Generates a random password with 15 characters.
	 * The password will have a number from 8 to 10 letters, 3 to 4  numbers, and the rest special characters. 
	 */
	private String generateRandomPassword() {
		Random rand = new Random();
		char[] randomPassword = new char[PASSWORD_CHAR_COUNT];
		
		int index = 0;
		int randomIndex;
		int count = rand.nextInt(3) + 8;
		while (index < count) {
			randomIndex = rand.nextInt(CHARS.length());
			randomPassword[index] = CHARS.charAt(randomIndex);
			index++;
		}
		
		count = (rand.nextInt(2) + 3) + count;
		while (index < count) {
			randomIndex = rand.nextInt(NUMS.length());
			randomPassword[index] = NUMS.charAt(randomIndex);
			index++;
		}
		
		while (index < PASSWORD_CHAR_COUNT) {
			randomIndex = rand.nextInt(SPECIAL_CHARS.length());
			randomPassword[index] = SPECIAL_CHARS.charAt(randomIndex);
			index++;
		}
		
		String password = new String(randomPassword);
		return password;
	}
	
	/**
	 * Checks if the user exists in the vault
	 * 
	 * @param username The username to check
	 */
	private boolean userExists(String username) {
		return usersPasswordsMap.containsKey(username);
	}
	
	@Override
	public void addNewUser(String username, String password) 
			throws 	exceptions.InvalidUsernameException,
					exceptions.InvalidPasswordException, 
					exceptions.DuplicateUserException
	{
		if (Validator.isValidUsername(username)) {
			if (!userExists(username)) {
				if (Validator.isValidPassword(password)) {
					usersLoginMap.put(username, 0);
					usersPasswordsMap.put(username, encryptor.encrypt(password));
				}
			} else {
				throw new DuplicateUserException();
			}
		}
	}
	
	/**
	 * If the user is locked out, adds the current time to the map
	 * 
	 * @param username The username to add the locked out time
	 */
	private void addLockedOutTime(String username) {
		Date date = new Date();
		userLockedOutMap.put(username, date.getTime());
	}
	
	/**
	 * Checks if the user is locked out of the vault
	 * 
	 * @param username The username to check the lock status
	 */
	private boolean isLockedOut(String username) {
		return (usersLoginMap.get(username) >= MAX_FAILED_LOGIN);
	}
	
	/**
	 * Checks the password of the user with the decrypted password stored in the vault
	 * 
	 * @param username The username to check the password
	 * @param password The password to be associated with this user
	 */
	private boolean isCorrectPassword(String username, String password) {
		String decryptedPassword = encryptor.decrypt(usersPasswordsMap.get(username));
		return (password.equals(decryptedPassword));
	}
	
	/**
	 * Checks if the site associated with the user exists in the vault
	 * 
	 * @param username The username associated with the site
	 * @param sitename The sitename to check if exists
	 */
	private boolean siteExists(String username, String sitename) {
		return (sitePasswordsMap.get(userSitePair(username, sitename)) != null);
	}
	
	/**
	 * Returns the pair of username and sitename to be used as the key in the mapping.
	 * 
	 * @param username The username that is associated
	 * @param sitename The sitename that is associated 
	 */
	private Pair<String, String> userSitePair(String username, String sitename) {
		return new Pair<String, String>(username, sitename);
	}
	
	/**
	 * Keeps track of when the user fails to login.
	 * If the user is locked out after the failure, it will add the locked out time.
	 * 
	 * @param username The username of the user that failed to login
	 */
	private void failedLogin(String username) {
		usersLoginMap.put(username, usersLoginMap.get(username) + 1);
		if (isLockedOut(username))
			addLockedOutTime(username);
		
	}
	
	/**
	 * Return the time elapsed since the user was last locked out of the vault
	 * 
	 * @param username The username that will be checked for the time elapsed
	 */
	private long getTimeElapsed(String username) {
		Date date = new Date();
		return date.getTime() - userLockedOutMap.get(username);
	}
	
	/**
	 * Remove the locked out status of the user and reset the login tries 
	 * 
	 * @param username The username to remove the locked status of 
	 */
	private void removeLockedOut(String username) {
		usersLoginMap.put(username, 0);
		userLockedOutMap.remove(username);
	}
	
	/**
	 * Adds or updates the password for the user's given site
	 * 
	 * @param username The username associated with the site
	 * @param sitename The sitename of which to update the password
	 */
	private String addOrUpdateSite(String username, String sitename) {
		String sitePassword = generateRandomPassword();
		Pair<String, String> pair = userSitePair(username, sitename);
		sitePasswordsMap.put(pair, encryptor.encrypt(sitePassword));
		return sitePassword;
	}
	
	@Override
	public String addNewSite(String username, String password, String sitename)
			throws exceptions.DuplicateSiteException, 
			exceptions.UserNotFoundException,
			exceptions.UserLockedOutException, 
			exceptions.PasswordMismatchException, 
			exceptions.InvalidSiteException 
	{
		if (userExists(username)) {
			if (!isLockedOut(username) || getTimeElapsed(username) > MS_IN_10MIN) {
				if (isCorrectPassword(username, password)) {
					removeLockedOut(username);
					if (Validator.isValidSite(sitename)) {
						if (!siteExists(username, sitename)) {
							return addOrUpdateSite(username, sitename);
						} else {
							throw new DuplicateSiteException();
						}
					} else {
						throw new InvalidSiteException();
					}
				} else {
					failedLogin(username);
					throw new PasswordMismatchException();
				}
			} else {
				addLockedOutTime(username);
				throw new UserLockedOutException();
			}
		} else {
			throw new UserNotFoundException();
		}
	}
	
	@Override
	public String updateSitePassword(String username, String password, String sitename)
			throws 	exceptions.SiteNotFoundException, 
					exceptions.UserNotFoundException,
					exceptions.UserLockedOutException, 
					exceptions.PasswordMismatchException
	{
		if (userExists(username)) {
			if (!isLockedOut(username) || getTimeElapsed(username) > MS_IN_10MIN) {
				if (isCorrectPassword(username, password)) {
					removeLockedOut(username);
					if (siteExists(username, sitename)) {
						return addOrUpdateSite(username, sitename);
					} else {
						throw new SiteNotFoundException();
					}
				} else {
					failedLogin(username);
					throw new PasswordMismatchException();
				}
			} else {
				addLockedOutTime(username);
				throw new UserLockedOutException();
			}
		} else {
			throw new UserNotFoundException();
		}
	}
	
	@Override
	public String retrieveSitePassword(String username, String password, String sitename)
			throws 	exceptions.SiteNotFoundException, 
					exceptions.UserNotFoundException,
					exceptions.UserLockedOutException, 
					exceptions.PasswordMismatchException
	{
		if (userExists(username)) {
			if (!isLockedOut(username) || getTimeElapsed(username) > MS_IN_10MIN) {
				if (isCorrectPassword(username, password)) {
					removeLockedOut(username);
					if (siteExists(username, sitename)) {
						String encryptedSitePassword = sitePasswordsMap.get(userSitePair(username, sitename));
						return encryptor.decrypt(encryptedSitePassword);
					} else {
						throw new SiteNotFoundException();
					}
				} else {
					failedLogin(username);
					throw new PasswordMismatchException();
				}
			} else {
				addLockedOutTime(username);
				throw new UserLockedOutException();
			}

		} else {
			throw new UserNotFoundException();
		}
	}

}
