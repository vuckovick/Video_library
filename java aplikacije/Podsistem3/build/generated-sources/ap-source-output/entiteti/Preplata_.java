package entiteti;

import entiteti.Korisnik;
import entiteti.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-12T14:00:18")
@StaticMetamodel(Preplata.class)
public class Preplata_ { 

    public static volatile SingularAttribute<Preplata, Date> datum;
    public static volatile SingularAttribute<Preplata, Paket> idPaket;
    public static volatile SingularAttribute<Preplata, Integer> cena;
    public static volatile SingularAttribute<Preplata, Integer> idPreplata;
    public static volatile SingularAttribute<Preplata, Korisnik> idKorisnik;

}