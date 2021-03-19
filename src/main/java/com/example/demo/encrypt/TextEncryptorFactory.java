package com.example.demo.encrypt;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public interface TextEncryptorFactory {
    TextEncryptor create(Binder binder);
}
