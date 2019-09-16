package io.openshift.booster.http;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
@org.junit.Ignore
public class HttpApplicationTest extends MongoDbTestBase {

    public static final int PORT = 8081;
    private Vertx vertx;
    private WebClient client;

    @Before
    public void before(TestContext context) {

        JsonObject config = new JsonObject();
        config.put("connection_string" , "mongodb://localhost:27018");
        config.put("db_name", "mongo_db");
        config.put("catalog.http.port", PORT);

        vertx = Vertx.vertx();
        vertx.exceptionHandler(context.exceptionHandler());
        vertx.deployVerticle(new HttpApplication(),new DeploymentOptions().setConfig(config), context.asyncAssertSuccess());
        client = WebClient.create(vertx);
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void getAllProjects(TestContext context) {
        // Send a request and get a response
        Async async = context.async();
        client.get(PORT, "localhost", "/projects")
            .send(resp -> {
                context.assertTrue(resp.succeeded());
                context.assertEquals(resp.result().statusCode(), 200);
                context.assertEquals(2 , resp.result().bodyAsJsonArray().getList().size());
                async.complete();
            });
    }


}
