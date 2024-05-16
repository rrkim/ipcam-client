package com.rrkim.utility;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesUtility {

    public static byte[] decryptData(String encodedString, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, DecoderException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptionByte = Base64.getDecoder().decode(encodedString);

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec IV = new IvParameterSpec(secretKey.substring(0,16).getBytes());

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secretKeySpec, IV);

        return c.doFinal(encryptionByte);
    }
}
