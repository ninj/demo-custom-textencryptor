package com.example.demo.config;

import org.springframework.boot.context.config.*;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Demonstrates use of the {@link BindHandler} to abstract decrypting a value using a
 * {@link com.example.demo.encrypt.CustomTextEncryptor} decrypting a value using a {@link ConfigDataLocationResolver}.
 * </p>
 * <p>
 * Note that the value stored in the resource is already decrypted.
 * </p>
 */
public class SomeConfigDataResolver implements ConfigDataLocationResolver<SomeConfigDataResource> {

    private static final String PREFIX = "someResource";

    @Override
    public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
        return location.hasPrefix(PREFIX);
    }

    @Override
    public List<SomeConfigDataResource> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location) throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException {
        Binder binder = context.getBinder();
        BindHandler handler = context.getBootstrapContext().get(BindHandler.class);
        String bindValue = binder.bind("some.bind-value", Bindable.of(String.class), handler).orElse("not set");
        SomeConfigDataResource resource = new SomeConfigDataResource(bindValue);
        return Collections.singletonList(resource);
    }
}
