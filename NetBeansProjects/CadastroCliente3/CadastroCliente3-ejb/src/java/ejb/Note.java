/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author romane
 */
@Entity
@Table(name = "NOTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n")
    , @NamedQuery(name = "Note.findByIdService", query = "SELECT n FROM Note n WHERE n.notePK.idService = :idService")
    , @NamedQuery(name = "Note.findByIdClient", query = "SELECT n FROM Note n WHERE n.notePK.idClient = :idClient")
    , @NamedQuery(name = "Note.findByValeur", query = "SELECT n FROM Note n WHERE n.valeur = :valeur")})
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotePK notePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALEUR")
    private double valeur;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "ID_SERVICE", referencedColumnName = "ID_S", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Service service;

    public Note() {
    }

    public Note(NotePK notePK) {
        this.notePK = notePK;
    }

    public Note(NotePK notePK, double valeur) {
        this.notePK = notePK;
        this.valeur = valeur;
    }

    public Note(int idService, int idClient) {
        this.notePK = new NotePK(idService, idClient);
    }

    public NotePK getNotePK() {
        return notePK;
    }

    public void setNotePK(NotePK notePK) {
        this.notePK = notePK;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notePK != null ? notePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.notePK == null && other.notePK != null) || (this.notePK != null && !this.notePK.equals(other.notePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.Note[ notePK=" + notePK + " ]";
    }
    
}
