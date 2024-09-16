# idmc-java
A Java SDK for IDMC REST API.

- **Open source implementation API** - API spec implementation (Apache 2.0 license).

## Quickstart - Hello IDMC!
Let's take IDMC API for spin. In this guide, we are going to do the following:
- In one terminal, we will explore the contents of IDMC API using a CLI. 
  An example project is provided to demonstrate how to use the IDMC-JAVA SDK for various assets
  as well as provide a convenient way to explore the content of IDMC API implementation.

### Prerequisites
You have to ensure that your local environment has the following:
- Clone this repository.
- Ensure the `JAVA_HOME` environment variable your terminal is configured to point to JDK11+.
- Compile the project using `build/sbt package`

## APIs and Compatibility
- Open API specification: The IDMC Rest API is documented [here](api).

## Compiling and testing
- Install JDK 11 by whatever mechanism is appropriate for your system, and
  set that version to be the default Java version (e.g., by setting env variable
  JAVA_HOME)
- To compile all the code without running tests, run the following:
  ```sh
  build/sbt clean compile
  ```
- To compile and execute tests, run the following:
  ```sh
  build/sbt clean test
  ```
- To update the API specification, just update the `api/all.yaml` and then run the following:
  ```sh
  build/sbt generate
  ``` 
  This will regenerate the OpenAPI data models in the UC server and data models + APIs in the client SDK.
### Using more recent JDKs

The build script [checks for a lower bound on the JDK](./build.sbt#L14) but the [current SBT version](./project/build.properties)
imposes an upper bound. Please check the [JDK compatibility](https://docs.scala-lang.org/overviews/jdk-compatibility/overview.html) documentation for more information
