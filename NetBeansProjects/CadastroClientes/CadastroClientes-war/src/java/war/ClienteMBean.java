/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import ejb.ClienteFechada;
import ejb.Clientes;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author romane
 */
@Named(value = "clienteMBean")
@RequestScoped
public class ClienteMBean {

    @EJB
    private ClienteFechada clienteFechada;

    /**
     * Creates a new instance of ClienteMBean
     */
    public ClienteMBean() {
    }
    
    public List<Clientes> getListaClientes() {
        List<Clientes> l =this.clienteFechada.getListaClientes();
        return l;
    }
    
}
