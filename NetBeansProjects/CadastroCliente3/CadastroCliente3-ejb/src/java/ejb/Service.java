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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s")
    , @NamedQuery(name = "Service.findByIdS", query = "SELECT s FROM Service s WHERE s.idS = :idS")
    , @NamedQuery(name = "Service.findByEndereco", query = "SELECT s FROM Service s WHERE s.endereco = :endereco")
    , @NamedQuery(name = "Service.findByCidade", query = "SELECT s FROM Service s WHERE s.cidade = :cidade")
    , @NamedQuery(name = "Service.findByDescripcao", query = "SELECT s FROM Service s WHERE s.descripcao = :descripcao")
    , @NamedQuery(name = "Service.findByNome", query = "SELECT s FROM Service s WHERE s.nome = :nome")
    , @NamedQuery(name = "Service.findByPrix", query = "SELECT s FROM Service s WHERE s.prix = :prix")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_S")
    private Integer idS;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ENDERECO")
    private String endereco;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CIDADE")
    private String cidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "DESCRIPCAO")
    private String descripcao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIX")
    private double prix;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Collection<Commentaire> commentaireCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Collection<Note> noteCollection;
    @JoinColumn(name = "PRESTATEUR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Clientes prestateur;
    @JoinColumn(name = "TYPE_SERVICE", referencedColumnName = "ID_TYPE_SERVICE")
    @ManyToOne(optional = false)
    private TypeService typeService;

    public Service() {
    }

    public Service(Integer idS) {
        this.idS = idS;
    }

    public Service(Integer idS, String endereco, String cidade, String descripcao, String nome, double prix) {
        this.idS = idS;
        this.endereco = endereco;
        this.cidade = cidade;
        this.descripcao = descripcao;
        this.nome = nome;
        this.prix = prix;
    }

    public Integer getIdS() {
        return idS;
    }

    public void setIdS(Integer idS) {
        this.idS = idS;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDescripcao() {
        return descripcao;
    }

    public void setDescripcao(String descripcao) {
        this.descripcao = descripcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @XmlTransient
    public Collection<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(Collection<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }

    @XmlTransient
    public Collection<Note> getNoteCollection() {
        return noteCollection;
    }

    public void setNoteCollection(Collection<Note> noteCollection) {
        this.noteCollection = noteCollection;
    }

    public Clientes getPrestateur() {
        return prestateur;
    }

    public void setPrestateur(Clientes prestateur) {
        this.prestateur = prestateur;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idS != null ? idS.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.idS == null && other.idS != null) || (this.idS != null && !this.idS.equals(other.idS))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.Service[ idS=" + idS + " ]";
    }
    
}
