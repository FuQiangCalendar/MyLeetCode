package org.example.structure.chatgpt;

import java.security.Key;
public class Main {
    public static void main(String[] args) throws Exception {
        // 生成密钥
        Key key = ConfigEncryptor.generateKey();
        // 需要加密的明文
        String plaintext = "password=123456";
        // 加密明文
        String ciphertext = ConfigEncryptor.encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);
        // 解密密文
        String decrypted = ConfigEncryptor.decrypt(ciphertext, key);
        System.out.println("Decrypted: " + decrypted);
    }

    public void testAddOperation() {
        //byte、short、char、boolean：都以int型来保存
        byte i = 15;
        int j = 8;
        int k = i + j;
    }
}