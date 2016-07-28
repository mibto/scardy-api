package me.scardy.api;

import me.scardy.business_logic.DataStoreHandler;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DataStoresResource {

    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createDataStore( String jsonString ) {
        try {
            JSONObject dataStoreAsJSON = new JSONObject( jsonString );
            String id = dataStoreAsJSON.optString( "id" );
            String admin = dataStoreAsJSON.optString( "admin" );
            String encryptedData = dataStoreAsJSON.optString( "encryptedData" );
            if ( "".equals( id ) || "".equals( admin ) ) {
                return Response.status( Response.Status.BAD_REQUEST ).build();
            }
            DataStoreHandler dataStoreHandler = new DataStoreHandler( id );
            if ( dataStoreHandler.createDataStore( admin ) ) {
                if ( dataStoreHandler.updateDataStore( encryptedData, admin ) ) {
                    return Response.ok().build();
                } else {
                    return Response.serverError().build();
                }
            } else {
                return Response.serverError().build();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Path( "/{id}" )
    public DataStoreResource getDataStoreResource( @PathParam( "id" ) String id ) {
        return new DataStoreResource( id );
    }
}
