package entiteti;

import entiteti.Korisnik;
import entiteti.Videosnimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-12T14:00:18")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Integer> idGledanje;
    public static volatile SingularAttribute<Gledanje, Date> datum;
    public static volatile SingularAttribute<Gledanje, Integer> sekundPocetka;
    public static volatile SingularAttribute<Gledanje, Videosnimak> idVideoSnimak;
    public static volatile SingularAttribute<Gledanje, Integer> sekundeGledanja;
    public static volatile SingularAttribute<Gledanje, Korisnik> idKorisnik;

}