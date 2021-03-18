# demo-custom-encryptor

Demonstration of adding a custom `TextEncryptor` into the `Bootstrap` environment.

See: https://github.com/spring-cloud/spring-cloud-commons/issues/897

Unfortunately at the moment this only implements a custom `TextEncryptor` for decrypting environment values.
(`TextEncryptorBindHandler` demo will have to come later.) 

Running the demo should print the decrypted value (`hello`) of `custom.setting` when run.

## Implementation Notes

`application.properties`:
- `custom.textencryptor.trim` configures how decrypt values.
- `custom.setting` contains an encrypted value.

