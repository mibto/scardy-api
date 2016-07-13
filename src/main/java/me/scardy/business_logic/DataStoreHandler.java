package me.scardy.business_logic;

import me.scardy.model.DataStore;
import me.scardy.model.VersionedData;
import me.scardy.setup.MongoDb;
import org.mongodb.morphia.Key;

import java.util.Date;
import java.util.List;

public class DataStoreHandler {

    String id;
    DataStore dataStore;

    public DataStoreHandler( String id ) {
        this.id = id;
        dataStore = MongoDb.getDatastore().get( DataStore.class, id );
    }

    public VersionedData getDataStore( Date version ) {
        if ( dataStore != null ) {
            return dataStore.getVersion( version );
        } else {
            return null;
        }
    }


    public List<Date> getVersions() {
        return dataStore.getVersions();
    }


    public boolean createDataStore( String admin ) {
        if ( dataStore != null ) {
            return false;
        }
        Key<DataStore> dataStoreKey = MongoDb.getDatastore().insert( new DataStore( id, admin ) );
        return dataStoreKey != null;
    }

    public boolean updateDataStore( String encryptedData, String admin ) {
        if ( dataStore != null && dataStore.getAdmin().equals( admin ) ) {
            VersionedData versionedData = new VersionedData();
            versionedData.setEncryptedData( encryptedData );
            dataStore.addVersion( versionedData );
            MongoDb.getDatastore().save( dataStore );
            return true;
        } else {
            return false;
        }
    }
}