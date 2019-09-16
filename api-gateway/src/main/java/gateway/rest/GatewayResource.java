package gateway.rest;

import gateway.services.FreelancerService;
import gateway.services.ProjectService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@ApplicationScoped
@Path("/gateway")
public class GatewayResource {

    @Inject
    private ProjectService projectService;

    @Inject
    private FreelancerService freelancerService;

    @GET
    @Path("/projects")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllProjects() {
        return projectService.getAllProjects();
    }

    @GET
    @Path("/projects/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProjectsById(@PathParam("projectId") String id) {
        return projectService.getProjectsById(id);
    }

    @GET
    @Path("/projects/status/{theStatus}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProjectsByStatus(@PathParam("theStatus") String status) {
        return projectService.getProjectsByStatus(status);
    }

    @GET
    @Path("/freelancers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllFreelancers() {
        return freelancerService.getAllFreelancers();
    }

    @GET
    @Path("/freelancers/{freelancerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreelancersById(@PathParam("freelancerId") String id) {
        return freelancerService.getFreelancerById(id);
    }

}
