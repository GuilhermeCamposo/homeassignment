package io.openshift.booster.http;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;

public class MongoDbTestBase {

    private static MongodExecutable exe;

    protected MongoClient mongoClient;

    @BeforeClass
    public static void startMongo() throws Exception {

        IMongodConfig config = new MongodConfigBuilder().version(Version.Main.PRODUCTION).net(new Net(27018, Network.localhostIsIPv6())).build();
        exe = MongodStarter.getDefaultInstance().prepare(config);
        exe.start();

    }

    @AfterClass
    public static void stopMongo() {
        if (exe != null) {
            exe.stop();
        }
    }


    protected void awaitLatch(CountDownLatch latch, TestContext context) throws InterruptedException {
        context.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    protected void dropCollection(MongoClient mongoClient, String name, Async async, TestContext context) {
        mongoClient.getCollections(ar -> {
            if (ar.failed()) {
                ar.cause().printStackTrace();
                context.fail(ar.cause().getMessage());
            } else {
                AtomicInteger collCount = new AtomicInteger();
                List<String> toDrop = ar.result().stream().filter(l -> l.startsWith(name)).collect(Collectors.toList());
                int count = toDrop.size();
                if (!toDrop.isEmpty()) {
                    for (String collection : toDrop) {
                        mongoClient.dropCollection(collection, ar1 -> {
                            if (ar.failed()) {
                                context.fail(ar1.cause().getMessage());
                            } else {
                                if (collCount.incrementAndGet() == count) {
                                    async.complete();
                                }
                            }
                        });
                    }
                } else {
                    async.complete();
                }
            }
        });
    }
}
