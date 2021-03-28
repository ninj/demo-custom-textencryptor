package com.example.demo.context;

import com.example.demo.encrypt.TextEncryptorFactory;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.Bootstrapper;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * Registers {@link TextEncryptor} for use with {@code org.springframework.cloud.bootstrap.TextEncryptorBindHandler} to
 * decrypt bind values during bootstrap.
 */
public class CustomTextEncryptorBindHandlerBootstrapper implements Bootstrapper, Ordered {

    public static final int ORDER = LOWEST_PRECEDENCE - 100;

    @Override
    public void intitialize(BootstrapRegistry registry) {
        registry.registerIfAbsent(TextEncryptor.class, context -> {
            var factory = context.get(TextEncryptorFactory.class);
            var binder = context.get(Binder.class);
            return factory.create(binder);
        });
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
