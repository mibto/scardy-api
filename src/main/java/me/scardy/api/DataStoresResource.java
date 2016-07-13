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
import java.net.URI;

class DataStoresResource {

    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createDataStore( String jsonString ) {
        try {
            JSONObject dataStore = new JSONObject( jsonString );
            String id = dataStore.optString( "id" );
            String admin = dataStore.optString( "admin" );
            if ( "".equals( id ) || "".equals( admin ) ) {
                return Response.status( Response.Status.BAD_REQUEST ).build();
            }
            boolean success = new DataStoreHandler( id ).createDataStore( admin );
            if ( success ) {
                return Response.created( URI.create( id ) ).build();
            } else {
                return Response.serverError().build();
            }
        } catch ( Exception e ) {
            return Response.serverError().build();
        }
    }

    @Path( "/{id}" )
    public DataStoreResource getDataStoreResource( @PathParam( "id" ) String id ) {
        return new DataStoreResource( id );
    }
}
