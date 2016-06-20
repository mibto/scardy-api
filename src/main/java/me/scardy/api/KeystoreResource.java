package me.scardy.api;

import me.scardy.business_logic.KeyStoreHandler;
import me.scardy.model.KeystoreModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("keystore/{id}")
public class KeystoreResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@PathParam("id") String id) {
        return KeyStoreHandler.getKeyStore(id);
    }
}
