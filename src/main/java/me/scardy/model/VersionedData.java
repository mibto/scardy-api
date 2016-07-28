package me.scardy.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.Embedded;

import java.util.Date;

@Embedded
public class VersionedData implements Data {


    private Date version;


    private boolean hasDecryptionProblem;


    private String encryptedData;


    public VersionedData() {
        this.hasDecryptionProblem = false; //default value
        version = new Date();
    }

    @Override
    public Date getVersion() {
        return version;
    }

    public boolean getHasDecryptionProblem() {
        return hasDecryptionProblem;
    }

    public void setHasDecryptionProblem( boolean hasDecryptionProblem ) {
        this.hasDecryptionProblem = hasDecryptionProblem;
    }

    @Override
    public VersionedData setEncryptedData( String encryptedData ) {
        this.encryptedData = encryptedData;
        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put( "version", version.getTime() );
        json.put( "hasDecryptionProblem", hasDecryptionProblem );
        json.put( "encryptedData", encryptedData );
        return json;
    }
}
