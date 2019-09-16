# Home Assignment

## Projects

### API Gateway
Thorntail implementation that consumes project-serve and freelancer service

### Freelancer Service
Spring Boot implementation that connects to a Postgres database

### Project Service
Vert.x implementation that connects to a Mongo database

## Testing in OpenShift

### Preparing The Environment

    oc new-project home-assignment
    oc create -f template.yaml
    oc new-app --template=home-assignment

### Testing

1. Get the generated hostname

        API_URL=$(oc get routes api-gateway --template={{.spec.host}})

2. Make some API calls

        curl http://$API_URL/gateway/projects
        curl http://$API_URL/gateway/projects/1
        curl http://$API_URL/gateway/projects/status/COMPLETED
        curl http://$API_URL/gateway/freelancers
        curl http://$API_URL/gateway/freelancers/1    
