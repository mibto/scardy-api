package me.scardy.setup;

/**
 * Created by MB on 20.06.2016.
 */

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Collections;


public final class MongoDb {
    private static MongoClient mongoClient;

    private static String databaseName;

    private static MongoCredential credentials;

    private static Morphia morphia;

    private static AdvancedDatastore aDs;


    public static void initConnection() {
        databaseName = "scardy";
        credentials = MongoCredential.createCredential( "scardy", "scardy", "changeForProduction".toCharArray() );
        mongoClient = new MongoClient( new ServerAddress( "localhost" ), Collections.singletonList( credentials ) );
    }

    public static void initMorphia() {
        if ( MongoDb.getMongoClient() == null ) {
            throw new IllegalStateException( "You must first initialize MongoDb" );
        }

        // Create Morphia Instance
        morphia = new Morphia();

        Datastore ds = morphia.createDatastore( MongoDb.getMongoClient(), databaseName );

        // Map entities to database - needed for advanced indices
        morphia.mapPackage( "me.scardy.model" );
        ds.ensureIndexes( true );

        // Make sure, maximum caps are set
        ds.ensureCaps();

        // Turn Datastore in AdvancedDatastore (more methods available)
        aDs = (AdvancedDatastore) ds;
    }

    public static void destroyConnection() {
        mongoClient.close();
        mongoClient = null;
    }

    public static void destroyMorphia() {
        aDs = null;
        morphia = null;
    }

    /* This on is needed for Morphia */
    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    /* Allow for loading of DBs other than scardy, e.g. for testing */
    public static MongoDatabase getDb( String dbName ) {
        return mongoClient.getDatabase( dbName );

    }

    public static MongoDatabase getDb() {
        return getDb( databaseName );
    }

    /**
     * Method for fetching ONE object
     *
     * @param <T>
     * @param classType Which class-type of object is supposed to be fetched
     * @param id        Unique object identifier (e.g. userId)
     * @return Object of given class-type
     */
    public static <T> T get( Class<T> classType, Object id ) {
        return aDs.get( classType, id );
    }

    /**
     * This methods allows direct access to the Morpiha Datastore instance for advanced queries
     *
     * @return Datastore object
     */
    public static AdvancedDatastore getDatastore() {
        return aDs;
    }
}
