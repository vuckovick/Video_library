package entiteti;

import entiteti.Videosnimak;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T21:19:59")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile ListAttribute<Kategorija, Videosnimak> videosnimakList;
    public static volatile SingularAttribute<Kategorija, Integer> idKategorija;

}