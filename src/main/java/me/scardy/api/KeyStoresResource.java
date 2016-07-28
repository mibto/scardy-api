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

public class KeyStoresResource {

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response createKeyStore( String jsonString ) {
        try {
            JSONObject keyStoreAsJSON = new JSONObject( jsonString );
            String id = keyStoreAsJSON.getString( "id" );
            String encryptedData = keyStoreAsJSON.getString( "encryptedData" );
            if ( "".equals( id ) ) {
                return Response.status( Response.Status.BAD_REQUEST ).build();
            }
            KeyStoreHandler keyStoreHandler = new KeyStoreHandler( id );
            if ( keyStoreHandler.createKeyStore() ) {
                if ( keyStoreHandler.updateKeyStore( encryptedData ) ) {
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
    public KeyStoreResource getKeyStoreResource( @PathParam( "id" ) String id ) {
        return new KeyStoreResource( id );
    }
}
