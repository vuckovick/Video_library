package entiteti;

import entiteti.Gledanje;
import entiteti.Ocena;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-12T14:00:18")
@StaticMetamodel(Videosnimak.class)
public class Videosnimak_ { 

    public static volatile SingularAttribute<Videosnimak, Integer> idVideoSnimak;
    public static volatile ListAttribute<Videosnimak, Gledanje> gledanjeList;
    public static volatile ListAttribute<Videosnimak, Ocena> ocenaList;
    public static volatile SingularAttribute<Videosnimak, String> naziv;

}