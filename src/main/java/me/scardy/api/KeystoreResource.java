package me.scardy.api;

import me.scardy.business_logic.KeyStoreHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

public class KeyStoreResource {

    private String id;

    public KeyStoreResource( String id ) {
        this.id = id;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response getKeyStore( @QueryParam( "version" ) Long version ) {
        try {
            return Response.ok().entity( new KeyStoreHandler( id ).getKeyStore( new Date( version ) ).toJSON().toString() ).build();
        } catch ( Exception e ) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }


    @GET
    @Path( "/versions" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getVersions() {
        try {
            List<Date> versionsList = new KeyStoreHandler( id ).getVersions();
            JSONArray versions = new JSONArray();
            for ( Date version : versionsList ) {
                versions.put( version.getTime() );
            }
            return Response.ok().entity( versions.toString() ).build();
        } catch ( Exception e ) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }


    @PUT
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response updateKeyStore( String jsonString ) {
        try {
            JSONObject keyStore = new JSONObject( jsonString );
            String encryptedData = keyStore.optString( "encryptedData" );
            if ( "".equals( encryptedData ) ) {
                return Response.status( Response.Status.BAD_REQUEST ).build();
            } else {
                boolean success = new KeyStoreHandler( id ).updateKeyStore( encryptedData );
                if ( success ) {
                    return Response.ok().build();
                } else {
                    return Response.serverError().build();
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
