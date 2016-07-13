package me.scardy.model;

import java.util.Date;
import java.util.List;

public interface Store {

    void addVersion( VersionedData version );

    List<Date> getVersions();

    VersionedData getVersion( Date version );

}
