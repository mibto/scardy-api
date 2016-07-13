package me.scardy.api;

import javax.ws.rs.Path;

@Path( "/" )
public class StoresResource {

    @Path( "/data-stores" )
    public DataStoresResource getDataStoresResource() {
        return new DataStoresResource();
    }

    @Path( "/key-stores" )
    public KeyStoresResource getKeyStoresResource() {
        return new KeyStoresResource();
    }
}
