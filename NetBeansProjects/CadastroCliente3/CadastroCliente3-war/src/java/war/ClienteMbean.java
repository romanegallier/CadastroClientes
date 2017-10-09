/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import ejb.ClienteFachada;
import ejb.Clientes;
import ejb.Commentaire;
import ejb.Note;
import ejb.Service;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author romane
 */
@ManagedBean

//@Dependent
@SessionScoped
@Named(value = "clienteMbean")
public class ClienteMbean implements Serializable {

    @EJB
    private ClienteFachada clienteFachada;
    String userMail;
    String userpwd;
    Clientes clientes;
    String localisation;
    Service services;
    Note note;
    Commentaire commentaire;

    public Service getServices() {
        return services;
    }

    public void setServices(Service services) {
        this.services = services;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    /**
     * Creates a new instance of ClienteMbean
     */
    public ClienteMbean() {
        clientes= new Clientes();
        services= new Service();
    }

    public Clientes getClientes() {
        return clientes;
    }
    
    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
        System.out.println("war.ClienteMbean.setUserpwd()"+userpwd);
    }
    
    public String getUserpwd() {
        return userpwd;
    }

    
    
    public List<Clientes> getListaClientes() {
        return clienteFachada.getListaClientes();
        
    }
    
    public List<Service> getListaService(){
       return clienteFachada.getListServices();
    }

    

    public void setUserMail(String userMail) {
        this.userMail = userMail;
        System.out.println("war.ClienteMbean.setUserMail(): "+userMail);
    }
    
    public String getUserMail() {
        return userMail;
    }
    
    public String getResponse() {

        if ((userMail != null) && (userpwd!=null)&&clienteFachada.logIn(userMail,userpwd)!=null) {
            //invalidate user session
            //FacesContext context = FacesContext.getCurrentInstance();
            //HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            //session.invalidate();
            return "login sucesfull";
            
        } else {
            return "login errado";
        }
    }
    
    public void inscrire(){
        
        try {
            clienteFachada.ajouterUtilisateur(clientes);
            System.out.println("war.ClienteMbean.inscrire()"+clientes.getNome());
        } catch (Exception ex) {
            Logger.getLogger(ClienteMbean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesMessage message = new FacesMessage( "Succès de l'inscription !" );
        FacesContext.getCurrentInstance().addMessage( null, message );
    }
    
    public void inscrireService(){
        
        try {
            services= new Service();
            services.setCidade(userMail);
            services.setDescripcao(userpwd);
            services.getEndereco();
            services.setIdS(Integer.SIZE);
            clienteFachada.ajouterServices(services);
        } catch (Exception ex) {
            Logger.getLogger(ClienteMbean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesMessage message = new FacesMessage( "Succès ajout du service:"+services.toString() );
        FacesContext.getCurrentInstance().addMessage( null, message );
        //traiter le cas de problemme 
    }
    
    public void rechercher(){
        
    }
    
    public String logIn(){
        if ((userMail != null) && (userpwd!=null)) {
            clientes= clienteFachada.logIn(userMail,userpwd);
            if (clientes!= null){
                FacesContext ct = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) ct.getExternalContext().getSession(true);
                session.setAttribute("clientes", clientes);
                return "logInSucessfull";
            }
                
            else return "logIn";
        } else {
            return "logIn"; //pas top devrai aller sur une autre page ou mettre un message
        }
        
        
        
        //TODO on ne peut pas s'inscrire ou se connecter si on est deja connecté
    }
    
    public void deconection (){
            //invalidate user session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
    }
    
    public void donnerNote(double note){
        //TODO faire que quand on clique on enregistre le services.
        //TODO verifier que cet utilisateur n'a pas deja laisser de note pour ce services
        if (services==null|| services.getIdS()==null){
            FacesMessage message = new FacesMessage( "service null" );
            FacesContext.getCurrentInstance().addMessage( null, message );
            
        }
        else {
            if (clientes==null||clientes.getId()==null){
                FacesMessage message = new FacesMessage( "clentes");
                FacesContext.getCurrentInstance().addMessage( null, message );
            }
            
            else {
                clienteFachada.laisserNote(services.getIdS(), clientes.getId(), note);
            }
            
        }
        
        
        
        
        
    }
    
    public String aller_page_note(Service service){
         FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null){
            clientes = (Clientes) session.getAttribute("clientes");
            if (clientes== null){
                return logIn();
            }
            else {
            this.services=service;
            return "note";
            }
        }
        else return "login";
    }
    
    public void laisserCommentaire (String commentaire){
        //TODO faire que quand on clique on enregistre le services.
        //TODO verifier que cet utilisateur n'a pas deja laisser de note pour ce services
        clienteFachada.laisserCommentaires(services.getIdS(), clientes.getId(), commentaire);
    }
}
