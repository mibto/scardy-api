package me.scardy.model;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity("keystores")
public class KeystoreModel {

    @Id
    private ObjectId id;

    @Indexed
    private String keystoreId;

    private String name;

    private String description;

    public String getName(){
        return name;
    }

}
