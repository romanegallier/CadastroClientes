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
import ejb.TypeService;
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
    String [] typosServicos;
    boolean log = false;
    String nome_type_service;

    public Service getServices() {
        return services;
    }

 

    public void setNome_type_service(String nome_type_service) {
        System.out.println("war.ClienteMbean.setNome_type_service(): je modifie la valeur");
        this.nome_type_service = nome_type_service;
    }
    
    public String getNome_type_service() {
        return nome_type_service;
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
        if (clientes== null){
            clientes= new Clientes();
        }
        return clientes;
    }
    
    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }
    
    public String getUserpwd() {
        return userpwd;
    }

    
    
    public List<Clientes> getListaClientes() {
        return clienteFachada.getListaClientes(); 
    }
    
    public String[]  getTyposServicos() {
        List<TypeService> l = clienteFachada.getListaTypoServico(); 
        typosServicos = new String[l.size()];
        for (int i =0; i<l.size();i++){
            typosServicos[i]=l.get(i).getNomeService();
        }
        return typosServicos;
    }
    
    public List<Service> getListaService(){
       if (localisation== null || localisation.isEmpty())
            return clienteFachada.getListServices();
       else {
           return clienteFachada.getListServices(localisation);
       }
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
    
    public String getUserMail() {
        return userMail;
    }
    
    
    
    public String inscrire(){
        
        try {
            clienteFachada.ajouterUtilisateur(clientes);
            System.out.println("war.ClienteMbean.inscrire()"+clientes.getNome());
        } catch (Exception ex) {
            Logger.getLogger(ClienteMbean.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage message = new FacesMessage( "Inscription failed !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
            clientes=null;
            return "inscription";
        }
        FacesMessage message = new FacesMessage( "Inscription sucess !" );
        FacesContext.getCurrentInstance().addMessage( null, message );
        clientes=null;
        return "index";
    }
    
    public String inscrireService(){
        if (services!=null){
            try {
                services.setPrestateur(clientes);
                System.out.println("war.ClienteMbean.inscrireService(): ajout prestateur");
                TypeService t = clienteFachada.getTypeServiceByName(nome_type_service);
                services.setTypeService(t);
                clienteFachada.ajouterServices(services);
                FacesMessage message = new FacesMessage( "Succès ajout du service:"+services.toString() );
                FacesContext.getCurrentInstance().addMessage( null, message );
                return "page_conecte";
            }
            catch(Exception e){
                FacesMessage message = new FacesMessage( "Succès ajout du service:"+services.toString() );
                FacesContext.getCurrentInstance().addMessage( null, message );
                return "services";
            }
        }
        return "services";
    }
    
    public void rechercher(){
        
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
    
    
           
    public String logIn(){
        log= false;

        if ((getUserMail()!= null) && (getUserpwd()!=null)) {
            clientes=clienteFachada.logIn(getUserMail(),getUserpwd());
            if (clientes!=null){
                FacesContext ct = FacesContext.getCurrentInstance();
                //HttpSession session = (HttpSession) ct.getExternalContext().getSession(true);
                //session.setAttribute("clientes", clientes);
                ct.addMessage(null, new FacesMessage("Logado com Sucesso !"));
                log=true;
                return "page_conecte";
            }
        }

        if (log != true){
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, new FacesMessage("Usuário ou Senha inválidos"));
            return "logIn";
        }
        return "page_conecte";
    }
    
    public String deconection (){
        log= false;
        clientes= null;
        return "index";
    }
    
    
    public String afficherClient(){
        return "Bienvenue "+clientes.getNome();
    }
    
    
    
}
