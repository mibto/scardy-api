package me.scardy.api;

import me.scardy.business_logic.KeyStoreHandler;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class KeyStoresResource {

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response createKeyStore( String jsonString ) {
        try {
            JSONObject keyStore = new JSONObject( jsonString );
            String id = keyStore.optString( "id" );
            if ( "".equals( id ) ) {
                return Response.status( Response.Status.BAD_REQUEST ).build();
            }
            boolean success = new KeyStoreHandler( id ).createKeyStore();
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
    public KeyStoreResource getKeyStoreResource( @PathParam( "id" ) String id ) {
        return new KeyStoreResource( id );
    }
}
