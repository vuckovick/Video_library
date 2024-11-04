package entiteti;

import entiteti.Preplata;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-12T14:00:18")
@StaticMetamodel(Paket.class)
public class Paket_ { 

    public static volatile SingularAttribute<Paket, Integer> idpaket;
    public static volatile SingularAttribute<Paket, Integer> cena;
    public static volatile ListAttribute<Paket, Preplata> preplataList;

}