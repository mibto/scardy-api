package me.scardy.setup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by MB on 20.06.2016.
 */
public class PersistenceListener implements ServletContextListener {

    @Override
    public void contextInitialized( ServletContextEvent ignored ) {
        MongoDb.initConnection();
        MongoDb.initMorphia();
    }

    @Override
    public void contextDestroyed( ServletContextEvent ignored ) {
        MongoDb.destroyMorphia();
        MongoDb.destroyConnection();
    }
}
