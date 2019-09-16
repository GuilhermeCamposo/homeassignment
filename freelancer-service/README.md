# Freelancer Service - Spring Boot

## How to run postgres
    podman run -it --rm -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -e POSTGRES_DB=freelancer -p 5432:5432 postgres

## How to Run the Application
    mvn clean spring-boot:run    


## Test Scenarios

### Get All Freelancers
    curl localhost:8082/freelancers

### Get Freelancer By Id
    curl localhost:8082/freelancers/1
