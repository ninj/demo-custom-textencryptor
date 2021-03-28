package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {

    /**
     * This configuration should be set externally, but we will inline for convenience in this demo.
     */
    static {
        System.setProperty("custom.textencryptor.trim", "2");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @SuppressWarnings("FieldMayBeFinal")
    @Value("${custom.property}")
    private String customProperty = "decrypted property expected here";

    @SuppressWarnings("FieldMayBeFinal")
    @Value("${some.bind-value}")
    private String someBindValue = "decrypted bind value expected here";

    @PostConstruct
    public void init() {
        System.out.println("some.bind-value = " + someBindValue);
        System.out.println("custom.property = " + customProperty);

    }
}
