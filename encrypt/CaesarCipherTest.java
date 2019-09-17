package encrypt;

import static org.junit.Assert.*;

import org.junit.Test;

public class CaesarCipherTest {
	
    private static final int OFFSET_MIN = 32;
    private static final int OFFSET_MAX = 126;	
	
	private String encryptDecryptHelper(String first, String second, boolean encrypt) {
		int shift = (int)(second.charAt(0) - first.charAt(0));
		String s = first;
		if (!encrypt)
			s = second;

		if (shift < 1)
			shift = OFFSET_MAX - OFFSET_MIN + shift;
		
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			int indx = c, cpos;

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

	@Test
	public void testCaesarCipher() {
		CaesarCipher cipher = new CaesarCipher();
		assertNotNull(cipher);
	}

	@Test
	public void testEncrypt() {
		CaesarCipher cipher = new CaesarCipher();
		String word = "encryptme1!";
		String encryptedWord = cipher.encrypt(word);
				
		assertEquals(encryptDecryptHelper(word, encryptedWord, true), cipher.encrypt(word));

	}

	@Test
	public void testDecrypt() {
		CaesarCipher cipher = new CaesarCipher();
		String word = "encryptme2?";
		String decryptedWord = cipher.decrypt(word);
				
		assertEquals(encryptDecryptHelper(decryptedWord, word, false), cipher.decrypt(word));
	}

}
