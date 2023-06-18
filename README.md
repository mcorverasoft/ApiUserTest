# Welcome to User api restfull!

This api has two endpoints:

- to create users with basic information
- and the other to get the users
  (Developed on a hexagonal architecture)

## How to run

Go to the repository main folder, please enter the bellow command :
**mvn spring-boot:run**
This api rest runs on http://localhost:8080

## Swagger url
This api has UI Swagger,
please go to http://localhost:8080/swagger-ui.html
follow Swagger's indication to run the endpoints

## H2 Database UI

To access the H2 console, please go to http://localhost:8080/h2/
In the H2 login form, in **JDBC URL**, please enter "***jdbc:h2:mem:testdb***" and click on "**Connect**"

## Base Response
All this response of this api rest, has the following structure;

    { "data": {}, "date": "2023-06-18T18:09:40.388Z", "errorResponse": { "errors": [ "string" ], "type": "[VALIDATION,ERROR,etc]" }, "message": "string", "status": "100 CONTINUE", "successful": true, "transactionId": "string" }
**important**: in the data field comes the information of each endpoint,


