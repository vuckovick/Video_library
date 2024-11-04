/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kokan
 */
@Entity
@Table(name = "preplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preplata.findAll", query = "SELECT p FROM Preplata p"),
    @NamedQuery(name = "Preplata.findByIdPreplata", query = "SELECT p FROM Preplata p WHERE p.idPreplata = :idPreplata"),
    @NamedQuery(name = "Preplata.findByDatum", query = "SELECT p FROM Preplata p WHERE p.datum = :datum"),
    @NamedQuery(name = "Preplata.findByCena", query = "SELECT p FROM Preplata p WHERE p.cena = :cena")})
public class Preplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPreplata")
    private Integer idPreplata;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private int cena;
    @JoinColumn(name = "idKorisnik", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik idKorisnik;
    @JoinColumn(name = "idPaket", referencedColumnName = "idpaket")
    @ManyToOne(optional = false)
    private Paket idPaket;

    public Preplata() {
    }

    public Preplata(Integer idPreplata) {
        this.idPreplata = idPreplata;
    }

    public Preplata(Integer idPreplata, Date datum, int cena) {
        this.idPreplata = idPreplata;
        this.datum = datum;
        this.cena = cena;
    }

    public Integer getIdPreplata() {
        return idPreplata;
    }

    public void setIdPreplata(Integer idPreplata) {
        this.idPreplata = idPreplata;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Korisnik idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Paket getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(Paket idPaket) {
        this.idPaket = idPaket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreplata != null ? idPreplata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preplata)) {
            return false;
        }
        Preplata other = (Preplata) object;
        if ((this.idPreplata == null && other.idPreplata != null) || (this.idPreplata != null && !this.idPreplata.equals(other.idPreplata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Preplata[ idPreplata=" + idPreplata + " ]";
    }
    
}
