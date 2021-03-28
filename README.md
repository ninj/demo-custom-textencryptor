# demo-custom-encryptor

Demonstration of adding a custom `TextEncryptor` into the `Bootstrap` environment.

See: https://github.com/spring-cloud/spring-cloud-commons/issues/897

Running the demo should print:
- the decrypted value of `custom.property` (`hello`).
- the decrypted value of `some.bind-value` (`world`).

## Implementation Notes

`application.properties`:
- sets `spring.cloud.decrypt-environment-post-processor.enabled` to false, preventing `DecryptEnvironmentPostProcessor`
  from trying to also decrypt values.  
- `custom.textencryptor.trim` configures how decrypt values.
- `custom.property` contains an encrypted value that will be decrypted by a `CustomDecryptEnvironmentPostProcessor`, in
  a similar fashion to a `DecryptEnvironmentPostProcessor`.
- `some.bind-value` contains an encrypted value that needs to be unencrypted so that `SomeConfigDataResolver` can make
  use of it. A `BindHandler` is used to decrypt the value, the `ConfigDataResolver` implementation has no knowledge of
  the `TextEncryptor`.
- `spring.config.import` references `someResource` to trigger `SomeConfigDataResolver`.

