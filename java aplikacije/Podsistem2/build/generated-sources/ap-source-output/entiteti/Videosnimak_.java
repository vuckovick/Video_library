package entiteti;

import entiteti.Kategorija;
import entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T21:19:59")
@StaticMetamodel(Videosnimak.class)
public class Videosnimak_ { 

    public static volatile ListAttribute<Videosnimak, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Videosnimak, Integer> idVideoSnimak;
    public static volatile SingularAttribute<Videosnimak, Integer> trajanje;
    public static volatile SingularAttribute<Videosnimak, String> naziv;
    public static volatile SingularAttribute<Videosnimak, Date> postavljanje;
    public static volatile SingularAttribute<Videosnimak, Korisnik> idVlasnik;

}