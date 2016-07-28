package me.scardy.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity( value = "dataStores", noClassnameStored = true )
public class DataStore implements Store {

    @Id
    private String id;

    private String admin;

    @Embedded
    private ArrayList<VersionedData> versions = new ArrayList<>();

    public DataStore() {
        //constructor for morphia
    }

    public DataStore( String id, String admin ) {
        this.id = id;
        this.admin = admin;
        this.versions = new ArrayList<>();
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin( String admin ) {
        this.admin = admin;
    }

    public List<Date> getVersions() {
        return versions.stream().map( VersionedData::getVersion ).collect( Collectors.toList() );
    }

    public VersionedData getVersion( Date version ) {
        return versions.stream().filter( versionedData -> version.equals( versionedData.getVersion() ) ).findAny().orElse( null );
    }

    @Override
    public void addVersion( VersionedData versions ) {
        this.versions.add( versions );
    }

}
