package me.scardy.api;

import me.scardy.business_logic.DataStoreHandler;
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
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class DataStoreResource {

    private String id;

    DataStoreResource( String id ) {
        this.id = id;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response getDataStore( @QueryParam( "version" ) String version ) {
        try {
            DateFormat dateFormat = DateFormat.getDateInstance();
            return Response.ok().entity( new DataStoreHandler( id ).getDataStore( dateFormat.parse( version ) ).toJSON().toString() ).build();
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
            List<Date> versionsList = new DataStoreHandler( id ).getVersions();
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
    public Response updateDataStore( String jsonString ) {
        try {
            JSONObject dataStoreAsJSON = new JSONObject( jsonString );
            String encryptedData = dataStoreAsJSON.optString( "encryptedData" );
            String admin = dataStoreAsJSON.optString( "admin" );
            if ( "".equals( encryptedData ) || "".equals( admin ) ) {
                return Response.status( Response.Status.BAD_REQUEST ).build();
            } else {
                boolean success = new DataStoreHandler( id ).updateDataStore( encryptedData, admin );
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
