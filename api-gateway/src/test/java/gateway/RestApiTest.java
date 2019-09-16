package gateway;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import gateway.services.ProjectService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(Arquillian.class)
public class RestApiTest {

    private static String port = "18080";

    @Mock
    private ProjectService projectService;

    @Before public void initMocks() {
        // Init the mocks from above
        MockitoAnnotations.initMocks(this);
    }

   private String projects = "[{ \"_id\" : \"5d7ecebdb3f2b705e270bf0d\", \"projectId\" : \"1\", \"ownerFirstName\" : \"guilherme\", \"ownerLastName\" : \"camposo\", \"ownerEmailAddress\" : \"guilherme@test.com\", \"projectTitle\" : null, \"projectDescription\" : null, \"projectStatus\" : \"CANCELLED\" },{ \"_id\" : \"5d7eced8e6617d02a18815fc\", \"projectId\" : \"1\", \"ownerFirstName\" : \"guilherme\", \"ownerLastName\" : \"camposo\", \"ownerEmailAddress\" : \"guilherme@test.com\", \"projectTitle\" : null, \"projectDescription\" : null, \"projectStatus\" : \"CANCELLED\" }]";

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RestApplication.class.getPackage())
                .addAsResource("project-local.yml", "project-defaults.yml");
    }

    @Before
    public void beforeTest() throws Exception {
        RestAssured.baseURI = String.format("http://localhost:%s", port);
    }

    @Test
    @RunAsClient
    public void testGetStoreStatus() throws Exception {

        Mockito.when(projectService.getAllProjects()).thenReturn(projects);

        given()
            .get("/gateway/projects")
            .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(equalTo(projects));
    }


}
