package me.scardy.business_logic;

import me.scardy.model.KeystoreModel;
import me.scardy.setup.MongoDb;

/**
 * Created by MB on 20.06.2016.
 */
public class KeyStoreHandler {

    public static String getKeyStore(String id){
        KeystoreModel keystore = MongoDb.getDatastore().createQuery( KeystoreModel.class ).field( "keystoreId" ).equal( id ).get();
        return keystore.getName();
    }
}
