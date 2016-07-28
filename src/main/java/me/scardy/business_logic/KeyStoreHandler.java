package me.scardy.business_logic;

import me.scardy.model.KeyStore;
import me.scardy.model.VersionedData;
import me.scardy.setup.MongoDb;
import org.mongodb.morphia.Key;

import java.util.Date;
import java.util.List;


public class KeyStoreHandler {

    String id;
    KeyStore keyStore;

    public KeyStoreHandler( String id ) {
        this.id = id;
        keyStore = MongoDb.getDatastore().get( KeyStore.class, id );
    }

    public VersionedData getKeyStore( Date version ) {
        if ( keyStore != null ) {
            return keyStore.getVersion( version );
        } else {
            return null;
        }
    }


    public List<Date> getVersions() {
        return keyStore.getVersions();
    }


    public boolean createKeyStore() {
        if ( keyStore != null ) {
            return false;
        }
        Key<KeyStore> keyStoreKey = MongoDb.getDatastore().insert( new KeyStore( id ) );
        keyStore = MongoDb.getDatastore().get( KeyStore.class, id );
        return keyStoreKey != null;
    }

    public boolean updateKeyStore( String encryptedData ) {
        if ( keyStore != null ) {
            keyStore.addVersion( new VersionedData().setEncryptedData( encryptedData ) );
            MongoDb.getDatastore().save( keyStore );
            return true;
        } else {
            return false;
        }
    }
}