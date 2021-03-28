package com.example.demo.context;

import com.example.demo.encrypt.TextEncryptorFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.cloud.bootstrap.encrypt.AbstractEnvironmentDecrypt;
import org.springframework.cloud.bootstrap.encrypt.DecryptEnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.Map;

/**
 * {@link DecryptEnvironmentPostProcessor} should be disabled if you use this.
 */
public class CustomDecryptEnvironmentPostProcessor extends AbstractEnvironmentDecrypt implements EnvironmentPostProcessor {

    private final ConfigurableBootstrapContext bootstrapContext;

    public CustomDecryptEnvironmentPostProcessor(ConfigurableBootstrapContext bootstrapContext) {
        this.bootstrapContext = bootstrapContext;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> decrypted = decrypt(getTextEncryptor(environment), environment.getPropertySources());
        var propertySource = new SystemEnvironmentPropertySource("custom-decrypted", decrypted);
        environment.getPropertySources().addFirst(propertySource);
    }

    /**
     * Does not use any pre-registered {@link TextEncryptor} to ensure latest environment values are used for
     * configuration.
     *
     * @param environment to source properties for configuring the {@link TextEncryptor}.
     * @return {@link TextEncryptor} to decrypt environment values
     */
    protected TextEncryptor getTextEncryptor(ConfigurableEnvironment environment) {
        Binder binder = Binder.get(environment);
        TextEncryptorFactory factory = bootstrapContext.get(TextEncryptorFactory.class);
        return factory.create(binder);
    }

}
