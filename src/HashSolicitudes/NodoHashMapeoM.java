package HashSolicitudes;

import ClasesEspeciales.ClaveSolicitud;
import ListaSolicitudes.Lista;

/**
 *
 * @author romangattas
 */
public class NodoHashMapeoM {
    private ClaveSolicitud clave;
    private Lista pedidos;
    private NodoHashMapeoM enlace;
    
    // Constructor
    public NodoHashMapeoM(ClaveSolicitud cl, NodoHashMapeoM en) {
        clave = cl;
        enlace = en;
        pedidos = new Lista();
    }
    
    // getters
    
    public ClaveSolicitud getClave() {
        return clave;
    }
    
    public Lista getPedidos() {
        return pedidos;
    }
    
    public NodoHashMapeoM getEnlace() {
        return enlace;
    }
    
    // setters
    
    public void setPedidos(Lista ped) {
        pedidos = ped;
    }
    
    public void setEnlace(NodoHashMapeoM en) {
        enlace = en;
    }
      
    
}
