package io.openshift.booster.http;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import io.openshift.booster.entity.Project;
import io.openshift.booster.entity.Status;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

public class HttpApplication extends AbstractVerticle {

    private Logger logger = Logger.getLogger(HttpApplication.class.getName());

    private MongoClient client;

    private String collection = "projects";


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        // health check
        router.get("/health").handler(rc -> rc.response().end("OK"));
        router.get("/projects").handler(this::getAllProjects);
        router.get("/projects/:projectId").handler(this::getProjectById);
        router.get("/projects/status/:theStatus").handler(this::getProjectByStatus);

        client = MongoClient.createShared(vertx, config() );

        logger.info("Mongo Client Started");

        initiateDb();

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("catalog.http.port", 8080), result -> {
                    if (result.succeeded()) {
                        System.out.println("Server Started");
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });

    }


    private void initiateDb(){

        Project project1 = new Project();
        project1.setOwnerEmailAddress("guilherme@test.com");
        project1.setOwnerFirstName("guilherme");
        project1.setOwnerLastName("camposo");
        project1.setProjectId("1");
        project1.setProjectStatus(Status.CANCELLED);

        Project project2 = new Project();
        project2.setOwnerEmailAddress("test@test.com");
        project2.setOwnerFirstName("test");
        project2.setOwnerLastName("tester");
        project2.setProjectId("2");
        project2.setProjectStatus(Status.COMPLETED);

        List<BulkOperation> operations = new ArrayList<>();
        operations.add(BulkOperation.createInsert(JsonObject.mapFrom(project1)));
        operations.add(BulkOperation.createInsert(JsonObject.mapFrom(project2)));

        logger.info("Trying to initialize DB....");

        client.bulkWrite(collection,  operations, result -> {
            if(result.failed()){
                logger.severe("initialization failed");
                logger.severe(result.cause().getMessage());
            }else{
                logger.info("Inserted Documents: " + result.result().getInsertedCount());
            }

        });
    }

    private void getAllProjects(RoutingContext rc) {

        JsonObject query = new JsonObject();

        client.find(collection, query, res -> {
            if (res.failed()){
                logger.severe(res.cause().getMessage());
                rc.response().setStatusCode(505).end();
            }else{
                logger.info("Projects Found " + res.result().size());
                rc.response()
                        .putHeader(CONTENT_TYPE.toString(), "application/json; charset=utf-8")
                        .end(getResultAsCollections(res));
            }
        });


    }

    private void getProjectById(RoutingContext rc) {

        String projectID = rc.request().getParam("projectId");

        JsonObject query = new JsonObject().put("projectId", projectID);

        client.find("projects", query, res -> {
            if (res.failed()){
                logger.severe(res.cause().getMessage());
                rc.response().setStatusCode(505).end();
            }else{
                logger.info("Projects Found " + res.result().size());

                rc.response()
                        .putHeader(CONTENT_TYPE.toString(), "application/json; charset=utf-8")
                        .end(getResultAsCollections(res));
            }
        });
    }

    private void getProjectByStatus(RoutingContext rc) {

        String status = rc.request().getParam("theStatus");

        JsonObject query = new JsonObject().put("projectStatus", status);

        client.find("projects", query, res -> {
            if (res.failed()){
                logger.severe(res.cause().getMessage());
                rc.response().setStatusCode(505).end();
            }else{
                logger.info("Projects Found " + res.result().size());
                rc.response()
                        .putHeader(CONTENT_TYPE.toString(), "application/json; charset=utf-8")
                        .end(getResultAsCollections(res));
            }
        });
    }

    private String getResultAsCollections(AsyncResult<List<JsonObject>> res) {
        StringBuilder response = new StringBuilder();
        response.append("[");

        for (int i = 0; i < res.result().size(); i++) {
            if (i > 0) {
                response.append(",");
            }
            response.append(res.result().get(i).encodePrettily());
        }
        response.append("]");
        return response.toString();
    }

}
