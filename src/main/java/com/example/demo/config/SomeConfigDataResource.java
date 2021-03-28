package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.config.ConfigDataResource;


@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SomeConfigDataResource extends ConfigDataResource {
    private final String bindValue;
}
