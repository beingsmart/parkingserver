package com.park.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.park.config.constants.MongoFields;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.jongo.Jongo;

import java.io.IOException;
import java.util.Set;

/**
 * Created by sharath on 26/1/16.
 * <p/>
 * Generic Class to initialize a mongo com.park.store given the MongoClientURI
 */
public class GenericMongoStore {

    /**
     * Mongo URL to connect.
     * Example: mongodb://userName:password@hsot:port/dbName
     */
    @JsonProperty("mongoURL")
    private String mongoURL;
    /**
     * MongoClientURI formed out of #mongoURL
     */
    private final MongoClientURI mongoURI;

    /**
     * MongoClient
     */
    private final MongoClient mongo;

    public GenericMongoStore(String mongoURL) throws IOException {
        this.mongoURL = mongoURL;
        this.mongoURI = new MongoClientURI(this.mongoURL);
        this.mongo = new MongoClient(mongoURI);
    }


    /**
     * Builds jongo object with the db specified in the config.
     *
     * @param environment Dropwizard Environment
     * @return jongo Jongo object created out of @this
     * @see Jongo
     */
    public Jongo build(Environment environment) {
        final Jongo jongo = createJongo();
        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() {
                Set<String> collectionNames = new DB(mongo, mongoURI.getDatabase()).getCollectionNames();
                String locationIndex = String.format(MongoFields.SIMPLE_KEY_VALUE, MongoFields.LOC);
                for (String spaceColl : collectionNames) {
                    jongo.getCollection(spaceColl).ensureIndex(locationIndex, "2dsphere");
                }
            }

            @Override
            public void stop() {
                mongo.close();
            }
        });
        return jongo;
    }

    public Jongo createJongo() {
        DB mongoDB = new DB(mongo, mongoURI.getDatabase());
        return new Jongo(mongoDB);
    }

    public void closeMongo() {
        mongo.close();
    }

    @Override
    public String toString() {
        return mongoURL;
    }

}
