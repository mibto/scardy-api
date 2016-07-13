package me.scardy.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.Embedded;

import java.util.Base64;
import java.util.Date;

@Embedded
public class VersionedData implements Data {


    private Date version;


    private boolean hasDecryptionProblem;


    private byte[] encryptedData;


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
        this.encryptedData = Base64.getDecoder().decode( encryptedData );
        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.append( "version", getVersion().getTime() );
        json.append( "hasDecryptionProblem", getHasDecryptionProblem() );
        json.append( "encryptedData", Base64.getEncoder().encode( encryptedData ) );
        return json;
    }
}
