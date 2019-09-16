# Vert.x Example - Project Service

Before running this example you need to have a running MongoDB instance.

# How to run a MongoDB container
    podman run -it --rm -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret -e MONGO_INITDB_DATABASE=mongo_db  mongo

# How to run the application
     mvn clean compile vertx:run

# API Call examples
## Get All  projects
    curl localhost:8080/projects

## Get By Id
    curl localhost:8080/projects/1

## Get Projects by Status
    curl localhost:8080/projects/status/COMPLETED
