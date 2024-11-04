package entiteti;

import entiteti.Korisnik;
import entiteti.Videosnimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-12T14:00:18")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Date> datum;
    public static volatile SingularAttribute<Ocena, Videosnimak> idVideosnimak;
    public static volatile SingularAttribute<Ocena, Integer> idOcena;
    public static volatile SingularAttribute<Ocena, Integer> ocena;
    public static volatile SingularAttribute<Ocena, Korisnik> idKorisnik;

}