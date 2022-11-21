package fr.uge.jee.hibernate.core;

import java.io.Serializable;

public interface IdEntity<Id extends Serializable> {

    Id getId();

    void setId(Id id);
}
