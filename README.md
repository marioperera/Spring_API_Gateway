# Spring_API_Gateway
api gateway for publishing and registering APIs and publishing your endpoints for rest apis


## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4001/`. for customization change src/resources/Application.properties "server.port".


## Build

Run `mvn build && install` to build the project on a maven setup. The build artifacts will be stored in the `/target/artifacts` directory as a jar/war file. Use the `--prod` flag for a production build.

## Running unit tests
supports both junit and krama for unit testing
Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
Run `mvn test` to execute unit test via [Junit5](https://junit.org/junit5/).
