# API Gateway - Thorntail 

## Before Running it
This app relies on the freelancer-service and project-service. You need to have those apps running so this app can work.

## How to Run this App
    mvn clean thorntail:run
    
## Testing

### Get All Projects 
    curl localhost:8080/gateway/projects
    
### Get Project by Id
    curl localhost:8080/gateway/projects/1
    
### Get Project by Status
    curl localhost:8080/gateway/projects/status/COMPLETED
    
### Get All Freelancers 
    curl localhost:8080/gateway/freelancers
    
### Get Freelancer by Id
    curl localhost:8080/gateway/freelancers/1    