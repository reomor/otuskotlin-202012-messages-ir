# Kotless notes

https://github.com/JetBrains/kotless/wiki/Getting-Started
https://site.kotless.io/pages/plugin/configuration

- AWS - IAM - create credentials (key-id, secret-access-key)
- Register DNS name, then Route 53, Certificate Manager
- ~/.aws/credentials
```
[profile kotless]
aws_access_key_id = <key-id>
aws_secret_access_key = <secret>
```
- use this profile in section `terraform`
- `version` resolves problem with not downloadable terraform
- bug on Windows - LF endings instead of CRLF
- [Missing Authentication Token](https://coderoad.ru/45619045/Обработка-Missing-Authentication-Token-после-настройки-AWS-Lambda-с-помощью)
- java runtime detects from gradle
