/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author romane
 */
@Embeddable
public class CommentairePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SERVICE")
    private int idService;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CLIENT")
    private int idClient;

    public CommentairePK() {
    }

    public CommentairePK(int idService, int idClient) {
        this.idService = idService;
        this.idClient = idClient;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idService;
        hash += (int) idClient;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentairePK)) {
            return false;
        }
        CommentairePK other = (CommentairePK) object;
        if (this.idService != other.idService) {
            return false;
        }
        if (this.idClient != other.idClient) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.CommentairePK[ idService=" + idService + ", idClient=" + idClient + " ]";
    }
    
}
