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
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIdGledanje", query = "SELECT g FROM Gledanje g WHERE g.idGledanje = :idGledanje"),
    @NamedQuery(name = "Gledanje.findByDatum", query = "SELECT g FROM Gledanje g WHERE g.datum = :datum"),
    @NamedQuery(name = "Gledanje.findBySekundPocetka", query = "SELECT g FROM Gledanje g WHERE g.sekundPocetka = :sekundPocetka"),
    @NamedQuery(name = "Gledanje.findBySekundeGledanja", query = "SELECT g FROM Gledanje g WHERE g.sekundeGledanja = :sekundeGledanja")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGledanje")
    private Integer idGledanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundPocetka")
    private int sekundPocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundeGledanja")
    private int sekundeGledanja;
    @JoinColumn(name = "idKorisnik", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik idKorisnik;
    @JoinColumn(name = "idVideoSnimak", referencedColumnName = "idVideoSnimak")
    @ManyToOne(optional = false)
    private Videosnimak idVideoSnimak;

    public Gledanje() {
    }

    public Gledanje(Integer idGledanje) {
        this.idGledanje = idGledanje;
    }

    public Gledanje(Integer idGledanje, Date datum, int sekundPocetka, int sekundeGledanja) {
        this.idGledanje = idGledanje;
        this.datum = datum;
        this.sekundPocetka = sekundPocetka;
        this.sekundeGledanja = sekundeGledanja;
    }

    public Integer getIdGledanje() {
        return idGledanje;
    }

    public void setIdGledanje(Integer idGledanje) {
        this.idGledanje = idGledanje;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getSekundPocetka() {
        return sekundPocetka;
    }

    public void setSekundPocetka(int sekundPocetka) {
        this.sekundPocetka = sekundPocetka;
    }

    public int getSekundeGledanja() {
        return sekundeGledanja;
    }

    public void setSekundeGledanja(int sekundeGledanja) {
        this.sekundeGledanja = sekundeGledanja;
    }

    public Korisnik getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Korisnik idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Videosnimak getIdVideoSnimak() {
        return idVideoSnimak;
    }

    public void setIdVideoSnimak(Videosnimak idVideoSnimak) {
        this.idVideoSnimak = idVideoSnimak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGledanje != null ? idGledanje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.idGledanje == null && other.idGledanje != null) || (this.idGledanje != null && !this.idGledanje.equals(other.idGledanje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Gledanje[ idGledanje=" + idGledanje + " ]";
    }
    
}
