/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kokan
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdkorisnik", query = "SELECT k FROM Korisnik k WHERE k.idkorisnik = :idkorisnik"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByEmail", query = "SELECT k FROM Korisnik k WHERE k.email = :email"),
    @NamedQuery(name = "Korisnik.findByGodiste", query = "SELECT k FROM Korisnik k WHERE k.godiste = :godiste"),
    @NamedQuery(name = "Korisnik.findByPol", query = "SELECT k FROM Korisnik k WHERE k.pol = :pol")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idkorisnik")
    private Integer idkorisnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ime")
    private String ime;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Godiste")
    private int godiste;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pol")
    private Character pol;
    @JoinColumn(name = "idMesta", referencedColumnName = "idMesto")
    @ManyToOne(optional = false)
    private Mesto idMesta;

    public Korisnik() {
    }

    public Korisnik(Integer idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    public Korisnik(Integer idkorisnik, String ime, String email, int godiste, Character pol) {
        this.idkorisnik = idkorisnik;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
    }

    public Integer getIdkorisnik() {
        return idkorisnik;
    }

    public void setIdkorisnik(Integer idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public Character getPol() {
        return pol;
    }

    public void setPol(Character pol) {
        this.pol = pol;
    }

    public Mesto getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(Mesto idMesta) {
        this.idMesta = idMesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idkorisnik != null ? idkorisnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idkorisnik == null && other.idkorisnik != null) || (this.idkorisnik != null && !this.idkorisnik.equals(other.idkorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ idkorisnik=" + idkorisnik + " ]";
    }
    
}
