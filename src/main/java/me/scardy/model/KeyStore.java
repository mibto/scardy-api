package me.scardy.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity( value = "keyStores", noClassnameStored = true )
public class KeyStore implements Store {


    @Id
    private String id;


    @Embedded
    private List<VersionedData> versions = new ArrayList<>();


    public KeyStore( String id ) {
        this.id = id;
        versions = new ArrayList<>();
    }

    public KeyStore() {
        //constructor for morphia
    }

    public List<Date> getVersions() {
        return versions.stream().map( VersionedData::getVersion ).collect( Collectors.toList() );
    }

    public VersionedData getVersion( Date version ) {
        return versions.stream().filter( versionedData -> version.equals( versionedData.getVersion() ) ).findAny().orElse( null );
    }

    @Override
    public void addVersion( VersionedData version ) {
        this.versions.add( version );
    }
}
