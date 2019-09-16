package gateway.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class ProjectService {

    private Client client = ClientBuilder.newClient();

    private static final String PROJECT_SERVICE_URL = "http://localhost:8081";

    public String getAllProjects(){
        WebTarget target = client.target(PROJECT_SERVICE_URL).path("/projects");
        return  target.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
    }

    public String getProjectsById(String id){
        WebTarget target = client.target(PROJECT_SERVICE_URL).path("/projects").path("/"+ id);
        return target.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
    }

    public String getProjectsByStatus(String status){
        WebTarget target = client.target(PROJECT_SERVICE_URL).path("/projects").path("/status").path("/"+status);
        return target.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
    }


}
