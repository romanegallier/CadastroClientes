/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static ejb.Service_.idS;
import java.util.List;
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
    

     // Metodo que retorna a lista de clientes armazenada na tabela Clientes
    public List<ejb.Clientes> getListaClientes() {
        Query query = em.createNamedQuery("Clientes.findAll");
        return query.getResultList();
    }
    
    public List<TypeService> getListaTypoServico() {
        Query query = em.createNamedQuery("TypeService.findAll");
        return query.getResultList();
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
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
        //on sait pas si c'est un prestateur ou un utilisateur 
        else return null;
    }
    
        // Recherche d'un utilisateur à partir de son adresse email pour le passage de parametre 
    /*public Utilisateur trouver( String email ) throws DAOException {
        Utilisateur utilisateur = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );
        try {
            utilisateur = (Utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return utilisateur;
    }*/
    
            // Enregistrement d'un nouvel utilisateur
    public void ajouterUtilisateur( Clientes clientes ){
        //TODO faire des verifications
        if (clientes.getCidade()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque ville");
            return;//TODO exeption
        }
        if (clientes.getEndereco()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque adresse");
            return;//TODO exeption
        }
        if (clientes.getId()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque id");
            Query query = em.createNamedQuery("Clientes.idmax");
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), query effectue");
            Clientes c2= (Clientes) query.getSingleResult();
            int i= c2.getId();
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), acha "+i);
            clientes.setId(i+1);
        }
        if (clientes.getMail()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque mail");
            clientes.setMail("@gmail");
        }
        if (                        clientes.getNome()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque nome");
            clientes.setNome("patate");
        }
        if (                                clientes.getPwd()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque pwd");
            clientes.setPwd("patate");
        }
        if (clientes.getTel()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque tel");
            return ;
        }
        if (clientes.getUf()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque uf");
            return;
        }
        TypeService typeService =  new TypeService(1);
        clientes.setTypeService(typeService);
        
        persist(clientes);
        clientes= null; //TODO enlever
    }
    public void ajouterPrestateur(Clientes clientes) {
       em.persist(clientes);
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
    
    public void laisserCommentaires(int id_services, int id_clientes, String commentaires){
        Commentaire commentaire = new Commentaire(id_services, id_clientes);
        commentaire.setCommentaire(commentaires);
        em.persist(commentaire);
    }
    
    public void laisserNote(int id_services, int id_clientes,Double notes){
        Note note = new Note(id_services, id_clientes);
        note.setValeur(notes);
        em.persist(note);
    }
    
    public List getListNote(int id_service){
        Query query = em.createNamedQuery("Note.findByIdService");
        query.setParameter("idS", id_service);
        return query.getResultList();
    }
    
    
    public double calculerMoyenne(int id_service){
        double m=0.0;
        List<Note> l = getListNote(id_service);
        for (int i=0;i<l.size();i++){
            m+=l.get(i).getValeur();
        }
        return m/l.size();
    }
    
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
