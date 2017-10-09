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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author romane
 */
@Entity
@Table(name = "COMMENTAIRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commentaire.findAll", query = "SELECT c FROM Commentaire c")
    , @NamedQuery(name = "Commentaire.findByIdService", query = "SELECT c FROM Commentaire c WHERE c.commentairePK.idService = :idService")
    , @NamedQuery(name = "Commentaire.findByIdClient", query = "SELECT c FROM Commentaire c WHERE c.commentairePK.idClient = :idClient")
    , @NamedQuery(name = "Commentaire.findByCommentaire", query = "SELECT c FROM Commentaire c WHERE c.commentaire = :commentaire")})
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommentairePK commentairePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "COMMENTAIRE")
    private String commentaire;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "ID_SERVICE", referencedColumnName = "ID_S", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Service service;

    public Commentaire() {
    }

    public Commentaire(CommentairePK commentairePK) {
        this.commentairePK = commentairePK;
    }

    public Commentaire(CommentairePK commentairePK, String commentaire) {
        this.commentairePK = commentairePK;
        this.commentaire = commentaire;
    }

    public Commentaire(int idService, int idClient) {
        this.commentairePK = new CommentairePK(idService, idClient);
    }

    public CommentairePK getCommentairePK() {
        return commentairePK;
    }

    public void setCommentairePK(CommentairePK commentairePK) {
        this.commentairePK = commentairePK;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
        hash += (commentairePK != null ? commentairePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commentaire)) {
            return false;
        }
        Commentaire other = (Commentaire) object;
        if ((this.commentairePK == null && other.commentairePK != null) || (this.commentairePK != null && !this.commentairePK.equals(other.commentairePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.Commentaire[ commentairePK=" + commentairePK + " ]";
    }
    
}
