package com.example.demo.encrypt;

import lombok.Data;

@Data
public class CustomTextEncryptorConfigurationProperties {

    public static final String DEFAULT_PREFIX = "custom.textencryptor";

    private int trim = 1;

}
