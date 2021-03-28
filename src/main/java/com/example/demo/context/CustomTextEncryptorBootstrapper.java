package com.example.demo.context;

import com.example.demo.encrypt.CustomTextEncryptor;
import com.example.demo.encrypt.CustomTextEncryptorConfigurationProperties;
import com.example.demo.encrypt.TextEncryptorFactory;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.Bootstrapper;

import static com.example.demo.encrypt.CustomTextEncryptorConfigurationProperties.DEFAULT_PREFIX;

/**
 * Registers {@link TextEncryptorFactory} for use with {@link CustomDecryptEnvironmentPostProcessor} and
 * {@link CustomTextEncryptorBindHandlerBootstrapper}.
 */
public class CustomTextEncryptorBootstrapper implements Bootstrapper {

    public void initialize(BootstrapRegistry registry) {
        registry.registerIfAbsent(TextEncryptorFactory.class, context -> binder -> {
            var config = binder.bind(DEFAULT_PREFIX, CustomTextEncryptorConfigurationProperties.class)
                    .orElseGet(CustomTextEncryptorConfigurationProperties::new);
            return new CustomTextEncryptor(config);
        });
    }

    @Deprecated
    @Override
    public void intitialize(BootstrapRegistry registry) {
        initialize(registry);
    }

}
