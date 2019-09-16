package io.openshift.booster;

import io.openshift.booster.http.HttpApplication;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class MainApplication extends AbstractVerticle {

  @Override
  public void start(final Future<Void> startFuture) {

    ConfigRetriever retriever = ConfigRetriever.create(vertx);

    retriever.getConfig(ar -> {
      if (ar.failed()) {
        System.out.println(ar.cause().getMessage());
      } else {
        JsonObject config = ar.result();
        deployVerticle(config, startFuture);
      }
    });

  }

  private void deployVerticle(JsonObject config, Future<Void> startFuture){
    Future<String> apiVerticleFuture = Future.future();

    vertx.deployVerticle(new HttpApplication(), new DeploymentOptions().setConfig(config), apiVerticleFuture.completer());

    apiVerticleFuture.setHandler(ar -> {
      if (ar.succeeded()) {
        System.out.println("Verticle deployed successfully.");
        startFuture.complete();
      } else {
        System.out.println("WARNINIG: Verticles NOT deployed successfully.");
        startFuture.fail(ar.cause());
      }
    });
  }


}
