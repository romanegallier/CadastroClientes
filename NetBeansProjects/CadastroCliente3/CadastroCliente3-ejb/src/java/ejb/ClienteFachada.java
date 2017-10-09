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
        if (clientes.getCidade()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque ville");
            clientes.setCidade("gre");
        }
        if (clientes.getEndereco()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque adresse");
            clientes.setEndereco("gresse");
        }
        if (        clientes.getId()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque id");
            //Query query = em.createQuery("SELECT MAX(ID) FROM CLIENTES");
            
            Query query = em.createQuery("select max(c.id) from Clientes c");
            
            // query.setParameter("mail", userMail);
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), query effectue");
            int i =query.getFirstResult();
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), acha "+i);
            clientes.setId(i++);
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
        if (                                        clientes.getTel()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque tel");
            clientes.setTel("5");
        }
        if (                                                clientes.getUf()==null){
            System.out.println("ejb.ClienteFachada.ajouterUtilisateur(), manque uf");
            clientes.setUf("5");
        }
        TypeService typeService =  new TypeService(1);
        clientes.setTypeService(typeService);
        
        persist(clientes);
    }
    
    
    
    public void ajouterPrestateur(Clientes clientes) {
       em.persist(clientes);
    }
    
    public void ajouterServices(Service service){
        em.persist(service);
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
        Query query = em.createNamedQuery("Service.findAll");
        return query.getResultList();
    }

}
