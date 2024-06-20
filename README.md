## Usage Guide

#### Build and Testing
You can build the app and run tests by running `./gradlew clean build test`

#### Manual testing with swagger-ui
You can start the app up by running `./gradlew bootRun` and navigate to `http://localhost:8080/swagger-ui/index.html`.

From there you can test the `/rentals` endpoint. Sample request:
```json
{
  "toolCode": "JAKR",
  "rentalDays": 4,
  "discountPercent": 50,
  "checkoutDate": "07/02/20"
}
````

#### Data Layer
This application uses an h2 database for demonstration purposes. 

It utilizes a single table called `Tool` and the schema for that table can be found in `src/main/resources/schema.sql`.

On start up five rows of tools data (as provided in the spec) are loaded into the h2 db. That data can be found in `src/main/resources/data.sql`. 