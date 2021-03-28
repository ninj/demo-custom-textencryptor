package com.example.demo.config;

import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * Exposes the already-decrypted bound value via a {@link PropertySource}.
 * </p>
 */
public class SomeConfigDataLoader implements ConfigDataLoader<SomeConfigDataResource> {

    @Override
    public ConfigData load(ConfigDataLoaderContext context, SomeConfigDataResource resource) throws IOException, ConfigDataResourceNotFoundException {
        Map<String, Object> map = Collections.singletonMap("some.bind-value", resource.getBindValue());
        PropertySource<?> source = new SystemEnvironmentPropertySource("custom-bind", map);
        return new ConfigData(Collections.singletonList(source));
    }

}
