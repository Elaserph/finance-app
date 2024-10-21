### [previous](../../README.md)

## Task description

### Define and implement a RESTful API that performs funds transfer between two accounts with currency exchange.

    Defined a Rest API to perform funds transfer, service called funds-transfer and another API which would act as an external API 
    for currency exchange called currency-exchange.

## Requirements

### Implementation has to be done in Java or Kotlin

    Implemented using Java 17 and SpringBoot 3.3.4.

### No security layer (authentication / authorization) needs to be provided

    No Security layer in application.

### Implementation has to be able to support being invoked concurrently by multiple users/systems

    SpringBoot Web comes with out of the box support for concurrency in APIs. For funds transfer, I have used pessimistic locking over accounts 
    to prevent concurrent access to them while they are under transfer process. This way, concurrent users using different accounts can access  
    the funds transfer API but concurrent users cannot access the same accounts thereby ensuring data consistency and transactional integrity.

### The minimal attributes to define an Account are:

An owner ID (numeric) <br>
A Currency (String) <br>
A balance (numeric) <br>

    Yes, took care, please refer the AccountEntity for more details.

### Exchange rates can be retrieved from external APIs

    Defined an external service currency-exchange with some dummy data which provides exchange rate to funds-transfer service.

### Program has to be runnable without any special software/container

    Program can be run from terminal/cmd without any special software, please follow the README.md .

### Functionality covered with tests

    Wrote Unit and Integration tests to cover all possible ways, I can think of, to use the services. 
    Covered all posibble errors/exceptions. Achieved code coverage of 94%.

### Fund transfer should fail if:

Either the debit or the credit account does not exist <br>
The exchange rate cannot be retrieved <br>
The balance of the debit account is not sufficient <br>
    
    All 3 mentioned functionalities covered.

## Additional information

### The code is expected to be of good quality and easy to maintain and Assume that the application has to be production-ready

    Used:
    SpringBoot multi-module project layout. 
    maven profiles (dev, prod, stage) for seperation of configurations.
    Sonarlint plugin to keep code quality in check.
    Integration and Unit tests to cover functionalities (total 50+ tests).
    JaCoCo Code coverage for better code coverage visibility (94% code coverage).
    OpenAPI and Swagger for API documentation and easy testing in dev profile.
    Javadocs, code comments and other docs for easy understanding.
    Followed best coding practices and externalization of common properties into a separate jar.

### As business specification is very light, use common sense in case of doubt 
    
    Thank you! I did.