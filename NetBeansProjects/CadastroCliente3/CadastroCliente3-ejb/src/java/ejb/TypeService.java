/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author romane
 */
@Entity
@Table(name = "TYPE_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeService.findAll", query = "SELECT t FROM TypeService t")
    , @NamedQuery(name = "TypeService.findByIdTypeService", query = "SELECT t FROM TypeService t WHERE t.idTypeService = :idTypeService")
    , @NamedQuery(name = "TypeService.findByNomeService", query = "SELECT t FROM TypeService t WHERE t.nomeService = :nomeService")})
public class TypeService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TYPE_SERVICE")
    private Integer idTypeService;
    @Size(max = 30)
    @Column(name = "NOME_SERVICE")
    private String nomeService;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "type_service")
    //private Collection<Clientes> clientesCollection;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "type_service")
    //private Collection<Service> serviceCollection;

    public TypeService() {
    }

    public TypeService(Integer idTypeService) {
        this.idTypeService = idTypeService;
    }

    public Integer getIdTypeService() {
        return idTypeService;
    }

    public void setIdTypeService(Integer idTypeService) {
        this.idTypeService = idTypeService;
    }

    public String getNomeService() {
        return nomeService;
    }

    public void setNomeService(String nomeService) {
        this.nomeService = nomeService;
    }

//    @XmlTransient
//    public Collection<Clientes> getClientesCollection() {
//        return clientesCollection;
//    }

//    public void setClientesCollection(Collection<Clientes> clientesCollection) {
//        this.clientesCollection = clientesCollection;
//    }

//    @XmlTransient
//    public Collection<Service> getServiceCollection() {
//        return serviceCollection;
//    }

//    public void setServiceCollection(Collection<Service> serviceCollection) {
//        this.serviceCollection = serviceCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeService != null ? idTypeService.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeService)) {
            return false;
        }
        TypeService other = (TypeService) object;
        if ((this.idTypeService == null && other.idTypeService != null) || (this.idTypeService != null && !this.idTypeService.equals(other.idTypeService))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.TypeService[ idTypeService=" + idTypeService + " ]";
    }
    
}
