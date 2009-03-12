/*
 * Copyright (c) 2008-2009 Tomas Varaneckas
 * http://www.varaneckas.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.varaneckas.hawkscope.util;

/**
 * Modified RC4 encryption/decryption mechanism
 * 
 * @author tomasv
 * @version $Id: RC4Crypt.java 89 2006-06-09 08:58:03Z tomasv $
 */
public class RC4Crypt {
	/**
	 * Data encrypter
	 * 
	 * @param data
	 *            Input data
	 * @param code
	 *            Secret Key
	 * @return
	 */
	static public byte[] encrypt(final byte[] data, final byte[] code) {
		int[] key = new int[256];
		int[] box = new int[256];

		final int codeLength = code.length;
		final int dataLength = data.length;

		for (int i = 0; i < 256; i++) {
			key[i] = code[(i % codeLength)];
			box[i] = i;
		}

		for (int j = 0, i = 0; i < 256; i++) {
			j = (j + box[i] + key[i]) % 256;
			box[i] ^= box[j];
			box[j] ^= box[i];
			box[i] ^= box[j];
		}

		final byte[] cipher = new byte[dataLength];

		for (int a = 0, j = 0, i = 0; i < dataLength; i++) {
			a = (a + 1) % 256;
			j = (j + box[a]) % 256;

			box[a] ^= box[j];
			box[j] ^= box[a];
			box[a] ^= box[j];

			final int k = box[((box[a] + box[j]) % 256)];
			final int c = data[i] ^ k;
			cipher[i] = (byte) c;
		}
		key = null;
		box = null;
		return cipher;
	}

	/**
	 * Data decrypter
	 * 
	 * @param data
	 *            Encrypted data
	 * @param code
	 *            Secret Key
	 * @return
	 */
	static public byte[] decrypt(final byte[] data, final byte[] code) {
		return RC4Crypt.encrypt(data, code);
	}
	
}