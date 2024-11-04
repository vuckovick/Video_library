/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kokan
 */
@Entity
@Table(name = "videosnimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videosnimak.findAll", query = "SELECT v FROM Videosnimak v"),
    @NamedQuery(name = "Videosnimak.findByIdVideoSnimak", query = "SELECT v FROM Videosnimak v WHERE v.idVideoSnimak = :idVideoSnimak"),
    @NamedQuery(name = "Videosnimak.findByNaziv", query = "SELECT v FROM Videosnimak v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "Videosnimak.findByTrajanje", query = "SELECT v FROM Videosnimak v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "Videosnimak.findByPostavljanje", query = "SELECT v FROM Videosnimak v WHERE v.postavljanje = :postavljanje")})
public class Videosnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVideoSnimak")
    private Integer idVideoSnimak;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Postavljanje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postavljanje;
    @ManyToMany(mappedBy = "videosnimakList")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "idVlasnik", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik idVlasnik;

    public Videosnimak() {
    }

    public Videosnimak(Integer idVideoSnimak) {
        this.idVideoSnimak = idVideoSnimak;
    }

    public Videosnimak(Integer idVideoSnimak, String naziv, int trajanje, Date postavljanje) {
        this.idVideoSnimak = idVideoSnimak;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.postavljanje = postavljanje;
    }

    public Integer getIdVideoSnimak() {
        return idVideoSnimak;
    }

    public void setIdVideoSnimak(Integer idVideoSnimak) {
        this.idVideoSnimak = idVideoSnimak;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getPostavljanje() {
        return postavljanje;
    }

    public void setPostavljanje(Date postavljanje) {
        this.postavljanje = postavljanje;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Korisnik getIdVlasnik() {
        return idVlasnik;
    }

    public void setIdVlasnik(Korisnik idVlasnik) {
        this.idVlasnik = idVlasnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVideoSnimak != null ? idVideoSnimak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videosnimak)) {
            return false;
        }
        Videosnimak other = (Videosnimak) object;
        if ((this.idVideoSnimak == null && other.idVideoSnimak != null) || (this.idVideoSnimak != null && !this.idVideoSnimak.equals(other.idVideoSnimak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Videosnimak[ idVideoSnimak=" + idVideoSnimak + " ]";
    }
    
}
