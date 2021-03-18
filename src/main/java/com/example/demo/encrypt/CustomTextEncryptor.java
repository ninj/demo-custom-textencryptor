package com.example.demo.encrypt;

import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * Decrypts values by removing a configurable number of character from the start and end of the value.
 */
public class CustomTextEncryptor implements TextEncryptor {

    private final CustomTextEncryptorConfigurationProperties config;

    public CustomTextEncryptor(CustomTextEncryptorConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public String encrypt(String text) {
        return null;
    }

    @Override
    public String decrypt(String encryptedText) {
        return encryptedText.substring(config.getTrim(), encryptedText.length() - config.getTrim());
    }
}
