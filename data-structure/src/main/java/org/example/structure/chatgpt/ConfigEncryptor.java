package org.example.structure.chatgpt;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
public class ConfigEncryptor {
    
    // 对称加密算法
    private static final String ALGORITHM = "AES";
    // 密钥长度
    private static final int KEY_SIZE = 128;
    // 随机数生成器
    private static final SecureRandom RANDOM = new SecureRandom();
    /**
     * 生成密钥
     */
    public static Key generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE, RANDOM);
        return keyGenerator.generateKey();
    }
    /**
     * 加密明文
     */
    public static String encrypt(String plaintext, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(ciphertext);
    }
    /**
     * 解密密文
     */
    public static String decrypt(String ciphertext, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(plaintext, StandardCharsets.UTF_8);
    }
}