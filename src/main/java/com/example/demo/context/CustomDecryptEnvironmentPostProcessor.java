package com.example.demo.context;

import com.example.demo.encrypt.CustomTextEncryptor;
import com.example.demo.encrypt.CustomTextEncryptorConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cloud.bootstrap.encrypt.DecryptEnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.Properties;

import static com.example.demo.encrypt.CustomTextEncryptorConfigurationProperties.DEFAULT_PREFIX;

/**
 * Must run before {@link DecryptEnvironmentPostProcessor}. Injects a temporary property source to prevent
 * {@link DecryptEnvironmentPostProcessor} from executing. The temporary property source is removed with
 * {@link RemoveWorkaround}.
 */
public class CustomDecryptEnvironmentPostProcessor extends DecryptEnvironmentPostProcessor {

    public static final String TEMP_PROPERTY_SOURCE_NAME = "blockDecryptEnvironmentPostProcessor";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        super.postProcessEnvironment(environment, application);
        injectPropertySourceToStopDecryptEnvironmentPostProcessor(environment);
    }

    private void injectPropertySourceToStopDecryptEnvironmentPostProcessor(ConfigurableEnvironment environment) {
        Properties p = new Properties();
        p.setProperty("spring.config.use-legacy-processing", "true");
        PropertySource<?> propertySource = new PropertiesPropertySource(TEMP_PROPERTY_SOURCE_NAME, p);
        environment.getPropertySources().addFirst(propertySource);
    }

    /**
     * Does not use any pre-registered {@link TextEncryptor} to ensure latest environment values are used for
     * configuration.
     *
     * @param environment to source properties for configuring the {@link TextEncryptor}.
     * @return {@link TextEncryptor} to decrypt environment values
     */
    @Override
    protected TextEncryptor getTextEncryptor(ConfigurableEnvironment environment) {
        Binder binder = Binder.get(environment);
        var config = binder.bind(DEFAULT_PREFIX, CustomTextEncryptorConfigurationProperties.class)
                .orElseGet(CustomTextEncryptorConfigurationProperties::new);
        return new CustomTextEncryptor(config);
    }

    @Override
    public int getOrder() {
        return super.getOrder() - 100;
    }

    static class RemoveWorkaround implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            applicationContext.getEnvironment().getPropertySources().remove(TEMP_PROPERTY_SOURCE_NAME);
        }
    }
}
