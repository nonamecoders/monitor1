package com.alan.monitor1.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
/**
 * this class for 암호화
 *
 * @author jason.kim
 * @since 2018-12-11
 */
public final class CipherUtil {

    private static final String MODE = "AES/GCM/NoPadding";

    private static final int KEY_SIZE = 32;

    private String iv;

    private Key keySpec;

    private CipherUtil() {
        // cannot make default instance
    }

    /**
     * 32자리의 키값을 입력하여 객체를 생성한다.
     *
     * @param key 암/복호화를 위한 키값
     */
    public CipherUtil(String key) {
        if (key.length() < KEY_SIZE) {
            throw new IllegalArgumentException("key size must be longer than " + KEY_SIZE);
        }

        this.iv = key.substring(0, KEY_SIZE);
        byte[] keyBytes = new byte[KEY_SIZE];
        byte[] b = key.getBytes(StandardCharsets.UTF_8);
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }

        System.arraycopy(b, 0, keyBytes, 0, len);
        this.keySpec = new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * AES 으로 암호화 한다.
     *
     * @param str 암호화할 문자열
     * @return
     * @throws GeneralSecurityException
     */
    public String encrypt(String str) throws GeneralSecurityException {
        Cipher c = Cipher.getInstance(MODE);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv.getBytes(StandardCharsets.UTF_8));
        c.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);
        byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(encrypted));
    }

    /**
     * AES 으로 암호화된 문자열을 복호화한다.
     *
     * @param str 복호화할 문자열
     * @return
     * @throws GeneralSecurityException
     */
    public String decrypt(String str) throws GeneralSecurityException {
        Cipher c = Cipher.getInstance(MODE);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv.getBytes(StandardCharsets.UTF_8));
        c.init(Cipher.DECRYPT_MODE, keySpec, parameterSpec);
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
    }
}
