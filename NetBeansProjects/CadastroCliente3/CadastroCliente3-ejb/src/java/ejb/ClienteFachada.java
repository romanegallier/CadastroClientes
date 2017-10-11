/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static ejb.Service_.idS;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author romane
 */
@Stateless
@LocalBean
public class ClienteFachada {

    @PersistenceContext(unitName = "CadastroCliente3-ejbPU")
    private EntityManager em;
    
    public void persist(Object object) {
        em.persist(object);
    }
    
    
    /***CLIENT*/
    
    public List<ejb.Clientes> getListaClientes() {
        Query query = em.createNamedQuery("Clientes.findAll");
        return query.getResultList();
    }
    
    public Clientes logIn(String userMail, String userpwd) {
        Clientes clientes = null;
        Query query = em.createNamedQuery("Clientes.findByMail");
        query.setParameter("mail", userMail);
        try {
            clientes= (Clientes)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        if ( clientes.getPwd().equals(userpwd)) return clientes;
        else return null;
    }
    
    public void ajouterUtilisateur( Clientes clientes ){
        Query query = em.createNamedQuery("Clientes.idmax");
        Clientes c2= (Clientes) query.getSingleResult();
        int i= c2.getId();
        clientes.setId(i+1);
        TypeService typeService =  new TypeService(1);
        clientes.setTypeService(typeService);
        persist(clientes);
        clientes= null;
    }
    
    
    /***SERIVCE*/
    
    
    public List<Service> getListServices(){
        try {
            Query query = em.createNamedQuery("Service.findAll");
            return query.getResultList();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public List<Service> getListServices(String localisation){
        try {
            Query query = em.createNamedQuery("Service.findByCidade");
            query.setParameter("cidade",localisation);
            return query.getResultList();
        }
        catch (Exception e) {
            return null;
        }
    }

    
    public void ajouterServices(Service service){
        if (!(service.getCidade()==null|| service.getDescripcao()==null||service.getEndereco()==null||
                service.getNome()==null||service.getPrestateur()==null||service.getTypeService()==null)){
            //TODO check that the prestateur is a clientes
            Query query = em.createNamedQuery("Service.idmax");
            
            Service s= (Service) query.getSingleResult();
            int i= s.getIdS();
            System.out.println("ejb.ClienteFachada.ajouterService(), acha "+i);
            service.setIdS(i+1);
            em.persist(service);
            System.out.println("ejb.ClienteFachada.ajouterServices(): success");
        }
    }

    
    /*COMMENTAIRE*/
    public void laisserCommentaires(int id_services, int id_clientes, String commentaires){
        Commentaire commentaire = new Commentaire(id_services, id_clientes);
        commentaire.setCommentaire(commentaires);
        em.persist(commentaire);
    }
    
    
    /*NOTE*/
    public void laisserNote(Note note){
        em.persist(note);
    }
    
    public List getListNote(int id_service){
        Query query = em.createNamedQuery("Note.findByIdService");
        query.setParameter("idService", id_service);
        return query.getResultList();
    }
    
    //TODO enlver doublon
    public List<Note> getListNota(int idS) {
        try {
            Query query = em.createNamedQuery("Note.findByIdService");
            query.setParameter("idService", idS);
            return query.getResultList();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public boolean recupererNote(int id, int ids){
        if (ids<0){
            System.out.println("ejb.ClienteFachada.recupererNote(): serviice id null");
            return false;
        }
        
        if (id<0){
            System.out.println("ejb.ClienteFachada.recupererNote(): cliente");
            return false;
        }
        Query query = em.createNamedQuery("Note.findByIdService");
        query.setParameter("idService", ids);
        try {
            List <Note> l = query.getResultList();
            for (int i=0; i<l.size();i++){
                if (id==l.get(i).getClientes().getId()){
                    return true;
                }
            }
        }
        catch (NoResultException r){
            return false;
        }
        return false;
    }
    
    
    public double calculerMoyenne(int id_service){
        double m=0.0;
        List<Note> l = getListNote(id_service);
        if (l.isEmpty()){
            return 0;
        }
        else {
            for (int i=0;i<l.size();i++){
                m+=l.get(i).getValeur();
            }
            return m/l.size();
        }
    }
    
    
    
    
    
    
    

    /**TYPE SERVICE */
    
    public List<TypeService> getListaTypoServico() {
        Query query = em.createNamedQuery("TypeService.findAll");
        return query.getResultList();
    }
    
    public TypeService getTypeServiceByName(String nome_type_service){
        try {
            Query query = em.createNamedQuery("TypeService.findByNomeService");
            query.setParameter("nomeService",nome_type_service);
            return (TypeService) query.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
    }

}
