package encrypt;

import java.util.Random;

public class CaesarCipher implements Encryptor {

	/**
	 * Default constructor for CaesarCipher - assigns a value to the shift variable
	 */
	public CaesarCipher() {
		shift = getShift();
	}

	@Override
	public String encrypt(String s) {
		return encryptDecrypt(s, true);
	}

	@Override
	public String decrypt(String s) {
		return encryptDecrypt(s, false);
	}
	
	/**
	 * Returns a randomly generated shift from the min and max offsets
	 */
	private static int getShift() {
		Random r = new Random();
		int low = 1;
		int high = OFFSET_MAX - OFFSET_MIN;
		return r.nextInt(high - low) + low;
	}

	/**
	 * Encrypts or decrypts the given string
	 *  
	 * @param s The string to encrypt or decrypt
	 * @param encrypt True if to encrypt, else false to decrypt
	 */
	private String encryptDecrypt(String s, boolean encrypt) throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();

		for (char c : s.toCharArray()) {
			int indx = c, cpos;
			if (!isPositionInRange(indx))
				throw new IllegalArgumentException("String to be encrypted has unrecognized character " + c);

			if (encrypt) {
				cpos = indx + shift;
				if (cpos > OFFSET_MAX)
					cpos = OFFSET_MIN + (cpos - OFFSET_MAX);
			} else {
				cpos = indx - shift;
				if (cpos < OFFSET_MIN)
					cpos = OFFSET_MAX - (OFFSET_MIN - cpos);	
			}
			sb.append((char)cpos);
		}
		return sb.toString();		
	}	
	
	/**
	 * Returns true if the index is in range of the min and max offsets
	 *  
	 * @param index The index to check if in range
	 */
	private boolean isPositionInRange(int indx) {
		return indx >= OFFSET_MIN && indx <= OFFSET_MAX;
	}

	private int shift;
    private static final int OFFSET_MIN = 32;
    private static final int OFFSET_MAX = 126;
}
