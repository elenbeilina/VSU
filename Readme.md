## IT tournaments for students platform

Platform for tournaments with different technologies' logic.

> #### Important notes:
> - rating system: modernised true skill algorithm
> - target: help students with improving their programming skills
> - configuration can be found in vsu-environment repository

### Build project

```
$ mvn clean package
```

### Build and push docker image

```
$ sh build-image.sh
```

------------------------

### Configuration

Available app configuration:
- application port
- server for postgres

Can be configured via docker-compose file. Example can be found in https://github.com/elenbeilina/vsu-docker-env repository

------------------------

### Test environment

All test environment can be found in https://github.com/elenbeilina/vsu-docker-env repository

---