/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kokan
 */
@Entity
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdpaket", query = "SELECT p FROM Paket p WHERE p.idpaket = :idpaket"),
    @NamedQuery(name = "Paket.findByCena", query = "SELECT p FROM Paket p WHERE p.cena = :cena")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpaket")
    private Integer idpaket;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private int cena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaket")
    private List<Preplata> preplataList;

    public Paket() {
    }

    public Paket(Integer idpaket) {
        this.idpaket = idpaket;
    }

    public Paket(Integer idpaket, int cena) {
        this.idpaket = idpaket;
        this.cena = cena;
    }

    public Integer getIdpaket() {
        return idpaket;
    }

    public void setIdpaket(Integer idpaket) {
        this.idpaket = idpaket;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @XmlTransient
    public List<Preplata> getPreplataList() {
        return preplataList;
    }

    public void setPreplataList(List<Preplata> preplataList) {
        this.preplataList = preplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpaket != null ? idpaket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idpaket == null && other.idpaket != null) || (this.idpaket != null && !this.idpaket.equals(other.idpaket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Paket[ idpaket=" + idpaket + " ]";
    }
    
}
