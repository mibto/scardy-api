package me.scardy.model;

import org.json.JSONObject;

import java.util.Date;

public interface Data {

    Date getVersion();

    VersionedData setEncryptedData( String encryptedData );

    boolean getHasDecryptionProblem();

    void setHasDecryptionProblem( boolean hasDecryptionProblem );

    JSONObject toJSON();

}
