package com.example.demo.context;

import com.example.demo.encrypt.CustomTextEncryptor;
import com.example.demo.encrypt.CustomTextEncryptorConfigurationProperties;
import com.example.demo.encrypt.TextEncryptorFactory;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.Bootstrapper;

import static com.example.demo.encrypt.CustomTextEncryptorConfigurationProperties.DEFAULT_PREFIX;

/**
 * Registers {@link org.springframework.security.crypto.encrypt.TextEncryptor} for use with
 * {@link CustomDecryptEnvironmentPostProcessor}
 */
public class CustomTextEncryptorBootstrapper implements Bootstrapper {
    @Override
    public void intitialize(BootstrapRegistry registry) {
       registry.register(TextEncryptorFactory.class, context -> binder -> {
           var config = binder.bind(DEFAULT_PREFIX, CustomTextEncryptorConfigurationProperties.class)
                   .orElseGet(CustomTextEncryptorConfigurationProperties::new);
           return new CustomTextEncryptor(config);
       });
    }
}
