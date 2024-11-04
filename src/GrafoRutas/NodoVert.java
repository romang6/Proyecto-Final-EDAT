package GrafoRutas;

/**
 *
 * @author romangattas
 */
public class NodoVert {
    Object elem;
    NodoVert sigVert;
    NodoAdy primerAdy;
    
    public NodoVert(Object e){
        elem=e;
        sigVert=null;
        primerAdy=null;
    }
    
    public NodoVert(Object e, NodoVert nv){
        elem=e;
        sigVert=nv;
        primerAdy=null;
    }
    
    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public NodoVert getSigVert() {
        return sigVert;
    }

    public void setSigVert(NodoVert sigVert) {
        this.sigVert = sigVert;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }
}
