package gateway.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class FreelancerService {

    private Client client = ClientBuilder.newClient();

    private static final String FREELANCER_SERVICE_URL = "http://freelancer-service:8080";

    public String getAllFreelancers(){
        WebTarget target = client.target(FREELANCER_SERVICE_URL).path("/freelancers");
        return target.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
    }

    public String getFreelancerById(String id){
        WebTarget target = client.target(FREELANCER_SERVICE_URL).path("/freelancers").path("/"+ id);
        return target.request(MediaType.APPLICATION_JSON).get().readEntity(String.class);
    }


}
