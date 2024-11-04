package HashClientes;

import ClasesEspeciales.Cliente;
import ClasesEspeciales.ClaveCliente;

/**
 *
 * @author romangattas
 */
public class NodoHash {
    private ClaveCliente clave;
    private Cliente cliente;
    private NodoHash enlace;
    
    //constructor
    
    public NodoHash(ClaveCliente cl, Cliente cli, NodoHash en){
        clave = cl;
        cliente = cli;
        enlace = en;
    }
    
    //setters
    
    public void setCliente(Cliente cli){
        cliente = cli;
    }
    
    public void setEnlace(NodoHash en){
        enlace = en;
    }
    
    //getters
    
    public ClaveCliente getClave(){
        return clave;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    
    public NodoHash getEnlace(){
        return enlace;
    }
   
}
